# JIRA 티켓 생성 (표준 템플릿)

기존 SCRUM 티켓 구조를 따라 일관된 형식의 JIRA 티켓을 자동으로 생성합니다.

## 사용법

```bash
/jira-create "티켓 제목" --type [frontend|backend|fullstack|infra] --parent SCRUM-XX
```

**예시:**
```bash
# Frontend 작업
/jira-create "카카오 로그인 UI 구현" --type frontend --parent SCRUM-5

# Backend 작업
/jira-create "Redis 캐싱 레이어 구현" --type backend --parent SCRUM-3

# Fullstack 작업 (Subtask 없이 독립 Task)
/jira-create "사용자 피드백 기능 구현" --type fullstack

# Infrastructure 작업
/jira-create "CloudWatch 알람 설정" --type infra --parent SCRUM-8
```

## 실행 프로세스

### 1. 사용자 입력 수집

**필수 입력:**
- 티켓 제목 (summary)
- 작업 타입 (frontend/backend/fullstack/infra)
- (선택) 부모 티켓 번호 (Subtask로 생성 시)

**대화형 프롬프트로 추가 정보 수집:**

```
📋 JIRA 티켓 생성을 시작합니다.

1️⃣ 티켓 제목: {user_input}
2️⃣ 작업 타입: {type}
3️⃣ 부모 티켓: {parent_ticket}

📝 Task 설명을 입력해주세요 (간단한 한 문장):
>

🔧 구현 내용을 입력해주세요 (핵심 구현 항목, 줄바꿈으로 구분):
>

📚 사용할 기술 스택을 입력해주세요 (콤마로 구분):
>

✅ Definition of Done 항목을 입력해주세요 (체크리스트 항목, 줄바꿈으로 구분):
>
```

### 2. 티켓 구조 자동 생성

#### Standard Template (SCRUM-11 기반)

**Description 구조:**
```markdown
## Task 설명

{사용자가 입력한 설명}

## 구현 내용

{사용자가 입력한 구현 항목들을 자동으로 bullet points로 변환}

## 기술 스택

{사용자가 입력한 기술 스택을 자동으로 bullet points로 변환}

## Definition of Done

{사용자가 입력한 DoD 항목들을 체크박스 리스트로 변환}
* [ ] {항목 1}
* [ ] {항목 2}
* [ ] {항목 3}
```

#### Type별 기본 템플릿

**Frontend:**
```markdown
## Task 설명

{user_description}

## 구현 내용

* {implementation_item_1}
* {implementation_item_2}
* {implementation_item_3}

## 기술 스택

* Next.js 14, TypeScript
* {additional_stack}

## Definition of Done

* [ ] 컴포넌트 구현 완료
* [ ] 반응형 디자인 적용 (모바일/데스크톱)
* [ ] 접근성 (a11y) 검증 완료
* [ ] Playwright 통합 테스트 완료
* [ ] npm run build 성공
* [ ] npm run lint 통과
* [ ] README 또는 문서 업데이트
* {custom_dod_items}
```

**Backend:**
```markdown
## Task 설명

{user_description}

## 구현 내용

* {implementation_item_1}
* {implementation_item_2}
* {implementation_item_3}

## 기술 스택

* Spring Boot 3.2, Java 17
* {additional_stack}

## Definition of Done

* [ ] API 엔드포인트 구현 완료
* [ ] 단위 테스트 작성 (Coverage ≥ 80%)
* [ ] 통합 테스트 작성 및 통과
* [ ] API 문서(Swagger) 업데이트
* [ ] 에러 핸들링 구현
* [ ] 로깅 및 모니터링 적용
* [ ] Database 마이그레이션 (필요 시)
* {custom_dod_items}
```

**Fullstack:**
```markdown
## Task 설명

{user_description}

## 구현 내용

### Frontend
* {frontend_item_1}
* {frontend_item_2}

### Backend
* {backend_item_1}
* {backend_item_2}

## 기술 스택

* Frontend: Next.js 14, TypeScript
* Backend: Spring Boot 3.2, Java 17
* {additional_stack}

## Definition of Done

### Frontend
* [ ] UI 컴포넌트 구현 완료
* [ ] API 연동 완료
* [ ] Playwright 테스트 완료
* [ ] npm run build/lint 통과

### Backend
* [ ] API 구현 완료
* [ ] 단위/통합 테스트 완료
* [ ] API 문서 업데이트

### Integration
* [ ] End-to-End 통합 테스트 완료
* [ ] 성능 테스트 완료
* {custom_dod_items}
```

**Infrastructure:**
```markdown
## Task 설명

{user_description}

## 구현 내용

* {implementation_item_1}
* {implementation_item_2}
* {implementation_item_3}

## 기술 스택

* Terraform, AWS
* {additional_stack}

## Definition of Done

* [ ] Infrastructure as Code 작성 완료
* [ ] terraform plan 성공
* [ ] terraform apply 성공
* [ ] 리소스 생성 확인
* [ ] 모니터링 및 알림 설정
* [ ] 문서화 완료 (아키텍처 다이어그램)
* {custom_dod_items}
```

### 3. Atlassian MCP로 티켓 생성

**Parent 티켓 있는 경우 (Subtask):**
```javascript
// 1. 부모 티켓 조회
mcp__atlassian__getJiraIssue(
  cloudId: "https://ddalkkak-date.atlassian.net",
  issueIdOrKey: "{parent_ticket}"
)

// 2. Subtask 생성
mcp__atlassian__createJiraIssue(
  cloudId: "https://ddalkkak-date.atlassian.net",
  projectKey: "SCRUM",
  issueTypeName: "Subtask",
  parent: "{parent_ticket}",
  summary: "[{type_prefix}] {title}",
  description: "{generated_description}"
)
```

**독립 Task인 경우:**
```javascript
mcp__atlassian__createJiraIssue(
  cloudId: "https://ddalkkak-date.atlassian.net",
  projectKey: "SCRUM",
  issueTypeName: "Task",
  summary: "{title}",
  description: "{generated_description}"
)
```

### 4. 작업 브랜치 정보 생성

티켓 생성 후 브랜치 네이밍 가이드 제공:

```
✅ JIRA 티켓 생성 완료!

📋 티켓 정보:
   - 티켓 번호: SCRUM-41
   - 제목: [FE] 카카오 로그인 UI 구현
   - URL: https://ddalkkak-date.atlassian.net/browse/SCRUM-41

🌿 작업 브랜치 생성:
   git checkout develop
   git pull origin develop
   git checkout -b feature/SCRUM-41-kakao-login-ui

📝 다음 단계:
   1. 티켓 상태를 "진행 중"으로 변경
      mcp__atlassian__transitionJiraIssue(issueIdOrKey: "SCRUM-41", transition: "진행 중")

   2. 작업 시작

   3. 커밋 및 진행 상황 업데이트
      /jira-commit SCRUM-41 "커밋 메시지"

   4. 완료 후 머지
      /jira-complete SCRUM-41 {pr_number}
```

## 고급 옵션

### Priority 설정

```bash
/jira-create "긴급 보안 패치" --type backend --priority high
```

**Priority 매핑:**
- `high`: Highest
- `medium`: Medium (기본값)
- `low`: Low

### Labels 추가

```bash
/jira-create "성능 최적화" --type backend --labels performance,optimization
```

### 담당자 지정

```bash
/jira-create "API 문서화" --type backend --assignee academey@gmail.com
```

### Story Points 추가 (Scrum/Agile)

```bash
/jira-create "대시보드 구현" --type frontend --story-points 5
```

## 유효성 검증

티켓 생성 전 다음을 자동 검증:

```javascript
// 1. 필수 필드 검증
if (!title || title.trim() === "") {
  throw new Error("❌ 티켓 제목은 필수입니다.");
}

if (!type || !['frontend', 'backend', 'fullstack', 'infra'].includes(type)) {
  throw new Error("❌ 올바른 작업 타입을 선택해주세요.");
}

// 2. 부모 티켓 존재 확인 (Subtask인 경우)
if (parent) {
  const parentIssue = await getJiraIssue(parent);
  if (!parentIssue) {
    throw new Error(`❌ 부모 티켓 ${parent}을 찾을 수 없습니다.`);
  }
}

// 3. Definition of Done 최소 항목 확인
if (dodItems.length < 3) {
  console.warn("⚠️ Definition of Done 항목이 3개 미만입니다. 최소 3개 이상 권장합니다.");
}

// 4. 티켓 제목 길이 확인
if (title.length > 100) {
  throw new Error("❌ 티켓 제목은 100자를 초과할 수 없습니다.");
}
```

## 출력 예시

```
🎫 JIRA 티켓 생성 중...

📋 티켓 정보:
   - 제목: [FE] 카카오 로그인 UI 구현
   - 타입: Subtask (부모: SCRUM-5)
   - 우선순위: Medium
   - 담당자: 미지정

✅ Description 생성 완료
   - Task 설명: ✅
   - 구현 내용: 3개 항목
   - 기술 스택: 2개 항목
   - Definition of Done: 7개 체크리스트

✅ JIRA 티켓 생성 완료!
   - 티켓 번호: SCRUM-41
   - URL: https://ddalkkak-date.atlassian.net/browse/SCRUM-41

🌿 권장 브랜치명:
   feature/SCRUM-41-kakao-login-ui

📝 다음 단계:
   1. 티켓 상태 변경: 진행 중
   2. 작업 브랜치 생성
   3. 개발 시작
   4. /jira-commit SCRUM-41 "메시지"

✨ Tip: CLAUDE.md의 워크플로우를 참조하세요!
```

## 템플릿 커스터마이징

프로젝트별 기본 템플릿을 `.claude/jira-templates/` 디렉토리에 저장:

```
.claude/jira-templates/
├── frontend.md         # Frontend 기본 템플릿
├── backend.md          # Backend 기본 템플릿
├── fullstack.md        # Fullstack 기본 템플릿
└── infra.md            # Infrastructure 기본 템플릿
```

커맨드 실행 시 해당 템플릿을 자동으로 로드하여 사용합니다.

## 에러 처리

```javascript
try {
  // JIRA 티켓 생성
  const issue = await createJiraIssue(params);
  console.log(`✅ 티켓 생성 성공: ${issue.key}`);
} catch (error) {
  if (error.message.includes("authentication")) {
    console.error("❌ JIRA 인증 실패. Atlassian MCP 연결을 확인하세요.");
  } else if (error.message.includes("permission")) {
    console.error("❌ 티켓 생성 권한이 없습니다.");
  } else if (error.message.includes("parent")) {
    console.error(`❌ 부모 티켓 ${parent}을 찾을 수 없습니다.`);
  } else {
    console.error(`❌ 티켓 생성 실패: ${error.message}`);
  }

  // 롤백 또는 재시도 안내
  console.log("\n🔄 수동으로 티켓을 생성하려면:");
  console.log("   https://ddalkkak-date.atlassian.net/jira/software/projects/SCRUM/issues");
}
```

## 프롬프트 템플릿

티켓 제목 `{title}`, 작업 타입 `{type}`, 부모 티켓 `{parent}`를 받아서 다음을 수행하세요:

1. 사용자에게 대화형 프롬프트로 추가 정보 수집
   - Task 설명 (간단한 한 문장)
   - 구현 내용 (핵심 항목)
   - 기술 스택
   - Definition of Done 항목

2. Type별 기본 템플릿 선택 및 사용자 입력 병합
   - Frontend: 반응형/접근성/테스트 중심
   - Backend: API/테스트/문서화 중심
   - Fullstack: Frontend + Backend 통합
   - Infrastructure: IaC/모니터링/문서화 중심

3. 유효성 검증
   - 필수 필드 확인
   - 부모 티켓 존재 확인 (Subtask인 경우)
   - DoD 최소 항목 확인

4. Atlassian MCP로 티켓 생성
   - Subtask 또는 독립 Task 자동 판단
   - 생성된 티켓 번호 및 URL 반환

5. 작업 브랜치 생성 가이드 제공
   - 브랜치명: feature/SCRUM-XX-brief-description
   - 다음 단계 안내

6. (선택) 티켓을 자동으로 "진행 중" 상태로 변경할지 물어보기

모든 출력은 한국어로 작성하고, SCRUM-11 티켓의 구조와 형식을 정확히 따르세요.
