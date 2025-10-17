# GitHub Secrets 설정 가이드

GitHub Actions CI/CD 파이프라인을 위한 Repository Secrets 설정 방법입니다.

## 📋 필수 Secrets 목록

### AWS 인증 (OIDC - 권장)

| Secret Name | Description | Example Value |
|------------|-------------|---------------|
| `AWS_ROLE_ARN` | GitHub Actions용 IAM Role ARN | `arn:aws:iam::123456789012:role/GitHubActionsRole` |

**참고**: AWS OIDC 연동 설정은 [AWS_OIDC_SETUP.md](./AWS_OIDC_SETUP.md)를 참조하세요.

---

### AWS 인증 (IAM 사용자 - 대안)

OIDC 설정이 어려운 경우에만 사용하세요.

| Secret Name | Description | Example Value |
|------------|-------------|---------------|
| `AWS_ACCESS_KEY_ID` | AWS IAM 사용자 Access Key | `AKIAIOSFODNN7EXAMPLE` |
| `AWS_SECRET_ACCESS_KEY` | AWS IAM 사용자 Secret Key | `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY` |

**보안 경고**: IAM 사용자 방식은 키 유출 위험이 있으므로 OIDC 방식을 권장합니다.

---

### Frontend 환경 변수

| Secret Name | Description | Required | Example Value |
|------------|-------------|----------|---------------|
| `NEXT_PUBLIC_API_BASE_URL` | Backend API 엔드포인트 | ✅ | `https://api.date-click.com/api/v1` |
| `NEXT_PUBLIC_KAKAO_MAP_API_KEY` | Kakao Map JavaScript API Key | ✅ | `abc123def456...` |
| `NEXT_PUBLIC_KAKAO_LOGIN_KEY` | Kakao Login REST API Key | ✅ | `xyz789ghi012...` |

---

### Backend 환경 변수 (선택)

Backend 환경 변수는 ECS Task Definition에서 설정하는 것을 권장하지만, 빌드 시 필요한 경우 추가할 수 있습니다.

| Secret Name | Description | Required | Example Value |
|------------|-------------|----------|---------------|
| `CLAUDE_API_KEY` | Anthropic Claude API Key | ⚠️ | `sk-ant-api03-...` |
| `SPRING_DATASOURCE_PASSWORD` | RDS 데이터베이스 비밀번호 | ⚠️ | `securepassword123` |

**참고**: 프로덕션 환경 변수는 ECS Task Definition의 Secrets Manager 연동을 사용하는 것이 더 안전합니다.

---

## 🔧 GitHub Secrets 설정 방법

### 1. GitHub Repository로 이동

```
https://github.com/dingcodingco/ddalkkak-date
```

### 2. Settings → Secrets and variables → Actions

1. Repository 페이지에서 **Settings** 탭 클릭
2. 왼쪽 메뉴에서 **Secrets and variables** → **Actions** 선택
3. **New repository secret** 버튼 클릭

### 3. Secret 추가

각 Secret을 다음 형식으로 추가:

```
Name: AWS_ROLE_ARN
Secret: arn:aws:iam::123456789012:role/GitHubActionsRole
```

**Add secret** 버튼을 클릭하여 저장합니다.

### 4. 추가된 Secrets 확인

모든 Secrets가 다음과 같이 표시되어야 합니다:

```
✅ AWS_ROLE_ARN
✅ NEXT_PUBLIC_API_BASE_URL
✅ NEXT_PUBLIC_KAKAO_MAP_API_KEY
✅ NEXT_PUBLIC_KAKAO_LOGIN_KEY
```

---

## 🔍 Secrets 사용 예시

### GitHub Actions Workflow에서 참조

```yaml
# Frontend 빌드 시 환경 변수로 주입
- name: Build Next.js application
  working-directory: ./frontend
  run: npm run build
  env:
    NEXT_PUBLIC_API_BASE_URL: ${{ secrets.NEXT_PUBLIC_API_BASE_URL }}
    NEXT_PUBLIC_KAKAO_MAP_API_KEY: ${{ secrets.NEXT_PUBLIC_KAKAO_MAP_API_KEY }}
    NEXT_PUBLIC_KAKAO_LOGIN_KEY: ${{ secrets.NEXT_PUBLIC_KAKAO_LOGIN_KEY }}

# AWS 인증 (OIDC)
- name: Configure AWS credentials
  uses: aws-actions/configure-aws-credentials@v4
  with:
    role-to-assume: ${{ secrets.AWS_ROLE_ARN }}
    aws-region: ap-northeast-2
```

---

## 🛡️ 보안 Best Practices

### ✅ DO (권장사항)

1. **OIDC 인증 사용**: IAM 사용자 대신 OIDC 방식 사용
2. **최소 권한 원칙**: IAM Role에 필요한 최소 권한만 부여
3. **정기적인 키 로테이션**: API 키를 주기적으로 갱신
4. **환경별 분리**: 프로덕션/스테이징 환경에 다른 Secrets 사용
5. **Secrets 암호화**: GitHub Secrets는 자동으로 암호화되지만, AWS Secrets Manager 사용 고려

### ❌ DON'T (금지사항)

1. **코드에 직접 하드코딩**: API 키를 소스 코드에 직접 작성 금지
2. **Public 저장소에 노출**: `.env` 파일을 Git에 커밋하지 않기
3. **과도한 권한 부여**: Admin 권한이 있는 IAM 사용자 사용 금지
4. **공유 계정 사용**: 개인 AWS 계정 자격 증명 사용 금지

---

## 🧪 Secrets 테스트 방법

### 로컬에서 테스트

```bash
# Frontend 환경 변수 테스트
cd frontend
echo "NEXT_PUBLIC_API_BASE_URL=https://api.date-click.com/api/v1" > .env.local
echo "NEXT_PUBLIC_KAKAO_MAP_API_KEY=your_key_here" >> .env.local
echo "NEXT_PUBLIC_KAKAO_LOGIN_KEY=your_key_here" >> .env.local
npm run build
```

### GitHub Actions에서 테스트

1. 워크플로우를 수동으로 실행 (workflow_dispatch)
2. Actions 탭에서 실행 결과 확인
3. Secrets가 올바르게 주입되었는지 로그 확인 (값은 `***`로 마스킹됨)

---

## 🆘 문제 해결

### Secret이 인식되지 않는 경우

```yaml
# ❌ 잘못된 사용
env:
  API_KEY: secrets.API_KEY

# ✅ 올바른 사용
env:
  API_KEY: ${{ secrets.API_KEY }}
```

### OIDC 인증 실패

```
Error: Could not assume role with OIDC
```

**해결 방법**:
1. IAM Role의 Trust Policy 확인
2. GitHub Actions의 `id-token: write` 권한 확인
3. AWS_ROLE_ARN이 올바른지 확인

자세한 내용은 [AWS_OIDC_SETUP.md](./AWS_OIDC_SETUP.md)를 참조하세요.

### 환경 변수가 빌드에 반영되지 않는 경우

Next.js의 `NEXT_PUBLIC_*` 환경 변수는 **빌드 타임**에 주입됩니다.

```bash
# 빌드 시 환경 변수 확인
npm run build -- --debug
```

---

## 📚 추가 문서

- [AWS OIDC 연동 설정](./AWS_OIDC_SETUP.md)
- [배포 프로세스 가이드](./DEPLOYMENT.md)
- [GitHub Actions 공식 문서](https://docs.github.com/en/actions/security-guides/encrypted-secrets)

---

## ✅ Checklist

배포 전에 다음 항목을 확인하세요:

- [ ] AWS_ROLE_ARN 또는 AWS 자격 증명 설정 완료
- [ ] NEXT_PUBLIC_API_BASE_URL 설정 완료
- [ ] NEXT_PUBLIC_KAKAO_MAP_API_KEY 설정 완료
- [ ] NEXT_PUBLIC_KAKAO_LOGIN_KEY 설정 완료
- [ ] GitHub Actions workflow 파일에서 Secrets 참조 확인
- [ ] 로컬 빌드 테스트 성공
- [ ] GitHub Actions 워크플로우 수동 실행 테스트 성공
