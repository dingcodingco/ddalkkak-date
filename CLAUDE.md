# CLAUDE.md - 딸깍데이트 프로젝트

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Structure

```
ddalkkak-date/
├── frontend/          # Next.js 14 웹 애플리케이션
├── backend/           # Spring Boot API 서버 (예정)
├── infrastructure/    # Terraform IaC (예정)
├── prd.md            # Product Requirements Document
└── CLAUDE.md         # 이 파일
```

## Git 브랜치 전략

### 브랜치 규칙

**메인 브랜치:**
- `main`: 프로덕션 배포 브랜치 (보호됨)
- `develop`: 개발 통합 브랜치

**작업 브랜치 네이밍:**
```
feature/SCRUM-{번호}-{간단한-설명}
bugfix/SCRUM-{번호}-{간단한-설명}
hotfix/SCRUM-{번호}-{간단한-설명}
```

**예시:**
```
feature/SCRUM-42-region-selection-map
bugfix/SCRUM-43-login-validation
hotfix/SCRUM-44-api-timeout
```

## JIRA 티켓 작업 프로세스

### 필수 워크플로우

모든 JIRA 티켓 작업 시 다음 순서를 **반드시** 따라야 합니다:

#### 1. 티켓 시작 및 브랜치 생성
```bash
# JIRA MCP를 사용하여 티켓 조회
mcp__atlassian__getJiraIssue(issueIdOrKey: "SCRUM-XX")

# 티켓 상태를 "진행 중"으로 변경
mcp__atlassian__transitionJiraIssue(issueIdOrKey: "SCRUM-XX", transition: "진행 중")

# GitHub CLI를 사용하여 작업 브랜치 생성
gh repo set-default  # 최초 1회만 실행 (리포지토리 선택)
gh pr create --draft --base develop --head feature/SCRUM-XX-brief-description --title "[SCRUM-XX] 작업 제목" --body "Draft PR"

# 또는 로컬에서 브랜치 생성
git checkout develop
git pull origin develop
git checkout -b feature/SCRUM-XX-brief-description

# 예시:
# git checkout -b feature/SCRUM-42-region-selection-map
```

#### 2. Definition of Done 확인
- 티켓 description의 "Definition of Done" 섹션을 **반드시** 확인
- 모든 체크리스트 항목을 작업 목록으로 추적

#### 3. 구현 작업
- 코드 작성
- 로컬 테스트 수행

#### 4. 통합 테스트 (필수)

**프론트엔드 변경사항:**
```bash
# Playwright MCP를 사용한 브라우저 자동화 테스트
# 1. 개발 서버 실행 확인
npm run dev

# 2. Playwright로 실제 브라우저 테스트
mcp__ide__executeCode("
# 브라우저에서 페이지 로드 테스트
# UI 인터랙션 테스트
# 예상 동작 검증
")

# 3. 빌드 성공 확인
npm run build

# 4. Lint & Format 검증
npm run lint
npm run format
```

**백엔드 변경사항:**
```bash
# API 호출 테스트 (실제 HTTP 요청)
curl -X POST http://localhost:8080/api/v1/endpoint \
  -H "Content-Type: application/json" \
  -d '{"key": "value"}'

# 또는 Postman/Insomnia로 API 테스트
# - 성공 케이스 테스트
# - 에러 케이스 테스트
# - 경계값 테스트
```

#### 5. Definition of Done 검증

모든 체크리스트 항목이 완료되었는지 확인:

**프론트엔드 예시:**
- [ ] 기능이 정상적으로 동작하는가?
- [ ] `npm run build`가 에러 없이 성공하는가?
- [ ] `npm run lint`가 통과하는가?
- [ ] 브라우저에서 실제 테스트를 수행했는가?
- [ ] 모바일 반응형이 정상 동작하는가?
- [ ] 접근성(a11y) 요구사항을 충족하는가?
- [ ] README 또는 문서가 업데이트되었는가?

**백엔드 예시:**
- [ ] API가 정상적으로 응답하는가?
- [ ] 단위 테스트가 작성되었는가?
- [ ] 통합 테스트가 통과하는가?
- [ ] API 문서(Swagger)가 업데이트되었는가?
- [ ] 에러 핸들링이 적절한가?
- [ ] 데이터베이스 마이그레이션이 필요한 경우 작성되었는가?

#### 6. 커밋 및 푸시

```bash
# 변경사항 커밋 (git 명령어 사용)
git add .
git commit -m "feat(component): 작업 내용 요약

- 상세 변경사항 1
- 상세 변경사항 2
- 상세 변경사항 3

JIRA: SCRUM-XX"

# GitHub CLI를 사용하여 푸시 및 PR 업데이트
git push origin feature/SCRUM-XX-brief-description

# Draft PR을 Ready for Review로 변경 (테스트 완료 후)
gh pr ready [PR번호]
```

#### 7. JIRA 업데이트 (진행 상황)

```bash
# 1. 작업 진행 코멘트 작성
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  commentBody: "## 진행 상황\n\n- 구현 완료 항목\n- 테스트 완료 여부\n- 브랜치: feature/SCRUM-XX-brief-description"
)

# 2. 티켓 상태를 "검토 중"으로 변경 (아직 완료 아님)
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  transition: "검토 중"
)
```

#### 8. 최종 검증 및 머지 (완료 확인 후)

```bash
# Definition of Done 모든 항목 재확인
# - [ ] 모든 체크리스트 완료
# - [ ] 빌드/린트 통과
# - [ ] 통합 테스트 완료
# - [ ] 문서 업데이트 완료

# GitHub CLI를 사용하여 PR 머지
gh pr merge [PR번호] --squash --delete-branch

# 또는 머지 옵션 선택
# --merge: 일반 머지 (모든 커밋 유지)
# --squash: 스쿼시 머지 (하나의 커밋으로 합침, 권장)
# --rebase: 리베이스 머지

# 수동으로 머지하는 경우
git checkout develop
git pull origin develop
git merge --no-ff feature/SCRUM-XX-brief-description
git push origin develop
git branch -d feature/SCRUM-XX-brief-description
```

#### 9. JIRA 티켓 완료 처리

```bash
# 최종 완료 코멘트 작성
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  commentBody: "## ✅ 완료

### 구현 내역
- 항목1
- 항목2

### 테스트 결과
- 통합 테스트: ✅ PASS
- 빌드: ✅ SUCCESS
- 린트: ✅ PASS

### 브랜치 정보
- 작업 브랜치: feature/SCRUM-XX-brief-description
- 머지됨: develop
- 커밋: [커밋 해시]"
)

# 티켓 상태를 "완료"로 변경
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  transition: "완료"
)
```

### 통합 테스트 체크리스트

#### 프론트엔드 (Playwright MCP 사용)

```typescript
// 필수 테스트 시나리오
1. 페이지 로드 테스트
   - URL 접속 성공
   - 기본 UI 요소 표시 확인
   - 로딩 상태 확인

2. 사용자 인터랙션 테스트
   - 버튼 클릭 동작
   - 폼 입력 및 제출
   - 네비게이션 이동

3. 데이터 표시 테스트
   - API 응답 데이터 렌더링
   - 에러 메시지 표시
   - 로딩 인디케이터

4. 반응형 테스트
   - 모바일 뷰포트 (375px)
   - 태블릿 뷰포트 (768px)
   - 데스크톱 뷰포트 (1024px+)

5. 접근성 테스트
   - 키보드 네비게이션
   - 스크린 리더 호환성
   - ARIA 레이블
```

#### 백엔드 (실제 API 호출)

```bash
# 필수 테스트 시나리오
1. 성공 케이스
   curl -X GET http://localhost:8080/api/v1/health
   # 예상: 200 OK

2. 인증 테스트
   curl -X GET http://localhost:8080/api/v1/protected \
     -H "Authorization: Bearer <token>"
   # 예상: 200 OK (유효한 토큰) 또는 401 Unauthorized

3. 유효성 검증 테스트
   curl -X POST http://localhost:8080/api/v1/resource \
     -H "Content-Type: application/json" \
     -d '{"invalid": "data"}'
   # 예상: 400 Bad Request + 에러 메시지

4. 에러 핸들링 테스트
   curl -X GET http://localhost:8080/api/v1/nonexistent
   # 예상: 404 Not Found

5. 성능 테스트 (선택)
   - 응답 시간 < 500ms (일반 API)
   - 응답 시간 < 8s (AI 코스 생성 API)
```

## 금지 사항

### ❌ 절대 하지 말아야 할 것

1. **통합 테스트 없이 티켓 완료 처리**
   - 프론트엔드: Playwright 테스트 없이 완료 금지
   - 백엔드: 실제 API 호출 테스트 없이 완료 금지

2. **Definition of Done 무시**
   - 체크리스트를 확인하지 않고 완료 금지
   - 일부 항목만 완료하고 티켓 종료 금지

3. **빌드 실패 상태로 커밋**
   - `npm run build` 실패 시 커밋 금지
   - `npm run lint` 실패 시 커밋 금지

4. **문서화 누락**
   - README 업데이트 없이 기능 추가 금지
   - API 변경 시 Swagger 문서 미업데이트 금지

5. **코멘트 없이 상태 변경**
   - JIRA 티켓 상태 변경 시 항상 작업 내역 코멘트 필수

6. **브랜치 규칙 위반**
   - `main` 브랜치에 직접 커밋 금지
   - 작업 브랜치 없이 직접 `develop`에 커밋 금지
   - Definition of Done 미완료 상태로 머지 금지
   - 머지 전 빌드/테스트 검증 누락 금지

## 프로젝트별 가이드

### Frontend (Next.js)
- 상세 가이드: [`frontend/CLAUDE.md`](./frontend/CLAUDE.md) 참조
- Tech Stack: Next.js 14, TypeScript, Tailwind CSS
- 배포: Vercel

### Backend (Spring Boot) - 예정
- Tech Stack: Spring Boot 3.2, Java 17, PostgreSQL
- 배포: AWS ECS Fargate

### Infrastructure (Terraform) - 예정
- IaC: Terraform
- Cloud: AWS (ECS, RDS, ElastiCache, ALB)

## 개발 환경 변수

### Frontend
```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api/v1
NEXT_PUBLIC_KAKAO_MAP_API_KEY=your_key_here
NEXT_PUBLIC_KAKAO_LOGIN_KEY=your_key_here
```

### Backend (예정)
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ddalkkak
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password
REDIS_HOST=localhost
REDIS_PORT=6379
CLAUDE_API_KEY=your_anthropic_api_key
```

## Git 커밋 메시지 컨벤션

```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 변경
style: 코드 포맷팅 (기능 변경 없음)
refactor: 코드 리팩토링
test: 테스트 추가/수정
chore: 빌드 설정, 패키지 매니저 설정
```

예시:
```
feat(frontend): 지역 선택 인터랙티브 맵 UI 구현

- SVG 기반 서울 지도 컴포넌트 추가
- 지역 hover/click 인터랙션 구현
- API 연동 및 데이터 바인딩
- 반응형 디자인 적용

JIRA: SCRUM-42
```

## 참고 문서

- **PRD**: `prd.md` - 전체 제품 요구사항 및 기술 스펙
- **Frontend Guide**: `frontend/CLAUDE.md` - Next.js 프로젝트 상세 가이드
- **API Documentation**: (예정) Swagger UI
- **Design System**: (예정) Figma 링크

## 문의 및 지원

- JIRA Board: https://ddalkkak-date.atlassian.net
- GitHub: (예정) Organization repository
- Slack: (예정) 팀 커뮤니케이션 채널
