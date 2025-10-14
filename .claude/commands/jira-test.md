# JIRA 티켓 통합 테스트

통합 테스트를 자동으로 실행하고 Definition of Done을 검증합니다.

## 사용법

```bash
/jira-test [frontend|backend]
```

**예시:**
```bash
/jira-test frontend
/jira-test backend
```

## 프론트엔드 테스트 (Playwright MCP)

### 1. 개발 서버 확인

```bash
# 개발 서버가 실행 중인지 확인
ps aux | grep "npm run dev"

# 실행 중이 아니면 경고
echo "⚠️  개발 서버가 실행되지 않았습니다. 다른 터미널에서 'npm run dev'를 실행하세요."
```

### 2. Playwright 브라우저 테스트

**필수 테스트 시나리오:**

```javascript
// 1. 페이지 로드 테스트
mcp__playwright__browser_navigate(url: "http://localhost:3000")
mcp__playwright__browser_snapshot()
// - URL 접속 성공
// - 기본 UI 요소 표시 확인
// - 로딩 상태 확인

// 2. 사용자 인터랙션 테스트
mcp__playwright__browser_click(element: "버튼", ref: "...")
// - 버튼 클릭 동작
// - 폼 입력 및 제출
// - 네비게이션 이동

// 3. 데이터 표시 테스트
// - API 응답 데이터 렌더링
// - 에러 메시지 표시
// - 로딩 인디케이터

// 4. 반응형 테스트
mcp__playwright__browser_resize(width: 375, height: 667)  // 모바일
mcp__playwright__browser_snapshot()

mcp__playwright__browser_resize(width: 768, height: 1024) // 태블릿
mcp__playwright__browser_snapshot()

mcp__playwright__browser_resize(width: 1920, height: 1080) // 데스크톱
mcp__playwright__browser_snapshot()

// 5. 접근성 테스트
// - 키보드 네비게이션
// - 스크린 리더 호환성
// - ARIA 레이블
```

### 3. 빌드 검증

```bash
cd frontend

# 빌드 실행
npm run build

# 빌드 성공 여부 확인
if [ $? -eq 0 ]; then
  echo "✅ 빌드 성공"
else
  echo "❌ 빌드 실패"
  exit 1
fi
```

### 4. Lint & Format 검증

```bash
# Lint 검증
npm run lint

if [ $? -eq 0 ]; then
  echo "✅ Lint 통과"
else
  echo "❌ Lint 실패"
  exit 1
fi

# Format 검증 (선택)
npm run format
```

## 백엔드 테스트 (실제 API 호출)

### 1. Health Check

```bash
curl -X GET http://localhost:8080/api/v1/health \
  -H "Accept: application/json" \
  -w "\nHTTP Status: %{http_code}\n"

# 예상: 200 OK
```

### 2. 인증 테스트

```bash
# 유효한 토큰
curl -X GET http://localhost:8080/api/v1/protected \
  -H "Authorization: Bearer valid_token" \
  -H "Accept: application/json" \
  -w "\nHTTP Status: %{http_code}\n"

# 예상: 200 OK

# 무효한 토큰
curl -X GET http://localhost:8080/api/v1/protected \
  -H "Authorization: Bearer invalid_token" \
  -H "Accept: application/json" \
  -w "\nHTTP Status: %{http_code}\n"

# 예상: 401 Unauthorized
```

### 3. 유효성 검증 테스트

```bash
# 잘못된 요청
curl -X POST http://localhost:8080/api/v1/resource \
  -H "Content-Type: application/json" \
  -d '{"invalid": "data"}' \
  -w "\nHTTP Status: %{http_code}\n"

# 예상: 400 Bad Request + 에러 메시지
```

### 4. 에러 핸들링 테스트

```bash
curl -X GET http://localhost:8080/api/v1/nonexistent \
  -H "Accept: application/json" \
  -w "\nHTTP Status: %{http_code}\n"

# 예상: 404 Not Found
```

### 5. 단위 테스트

```bash
# Gradle (Spring Boot)
./gradlew test

# Maven
mvn test

# 테스트 결과 확인
if [ $? -eq 0 ]; then
  echo "✅ 단위 테스트 통과"
else
  echo "❌ 단위 테스트 실패"
  exit 1
fi
```

### 6. 통합 테스트

```bash
# Gradle
./gradlew integrationTest

# Maven
mvn verify

# 결과 확인
if [ $? -eq 0 ]; then
  echo "✅ 통합 테스트 통과"
else
  echo "❌ 통합 테스트 실패"
  exit 1
fi
```

## Definition of Done 검증

### 프론트엔드 체크리스트

```markdown
- [x] 기능이 정상적으로 동작하는가? → Playwright 테스트 통과
- [x] `npm run build`가 에러 없이 성공하는가? → 빌드 성공
- [x] `npm run lint`가 통과하는가? → Lint 통과
- [x] 브라우저에서 실제 테스트를 수행했는가? → Playwright 시나리오 완료
- [x] 모바일 반응형이 정상 동작하는가? → 반응형 테스트 완료
- [x] 접근성(a11y) 요구사항을 충족하는가? → 접근성 테스트 완료
- [ ] README 또는 문서가 업데이트되었는가? → 수동 확인 필요
```

### 백엔드 체크리스트

```markdown
- [x] API가 정상적으로 응답하는가? → curl 테스트 통과
- [x] 단위 테스트가 작성되었는가? → 단위 테스트 통과
- [x] 통합 테스트가 통과하는가? → 통합 테스트 통과
- [ ] API 문서(Swagger)가 업데이트되었는가? → 수동 확인 필요
- [x] 에러 핸들링이 적절한가? → 에러 핸들링 테스트 통과
- [ ] 데이터베이스 마이그레이션이 필요한 경우 작성되었는가? → 수동 확인 필요
```

## 테스트 결과 리포트

```markdown
# 통합 테스트 결과

## 실행 시간
- 시작: 2025-01-14 10:00:00
- 종료: 2025-01-14 10:05:23
- 소요: 5분 23초

## 프론트엔드 테스트
- ✅ 페이지 로드: PASS
- ✅ 사용자 인터랙션: PASS
- ✅ 데이터 표시: PASS
- ✅ 반응형 (모바일/태블릿/데스크톱): PASS
- ✅ 접근성: PASS
- ✅ 빌드: SUCCESS
- ✅ Lint: PASS

## Definition of Done
- 자동 검증: 6/7 (85.7%)
- 수동 확인 필요: 1개 (문서 업데이트)

## 다음 단계
✅ 모든 자동 테스트 통과
⏭️  문서 업데이트 후 /jira-commit 실행
```

## 프롬프트 템플릿

`{type}` (frontend 또는 backend)을 받아서 다음을 수행하세요:

1. 현재 작업 중인 티켓 번호 확인 (git 브랜치명에서 추출)
2. `{type}`에 맞는 통합 테스트 실행:
   - **frontend**: Playwright 브라우저 테스트 + 빌드 + Lint
   - **backend**: curl API 테스트 + 단위/통합 테스트
3. Definition of Done 체크리스트 업데이트
4. 테스트 결과 리포트 생성 (마크다운 형식)
5. 모든 테스트 통과 시: "✅ 통합 테스트 완료. /jira-commit 실행 가능"
6. 테스트 실패 시: 실패 항목 및 에러 로그 출력

TodoWrite를 사용하여 실시간으로 테스트 진행 상황을 추적하세요.
