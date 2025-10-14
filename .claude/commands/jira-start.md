# JIRA 티켓 시작

JIRA 티켓 작업을 시작합니다. 티켓 조회, 상태 변경, 브랜치 생성, Definition of Done 추적을 자동화합니다.

## 사용법

```bash
/jira-start SCRUM-XX "간단한-설명"
```

**예시:**
```bash
/jira-start SCRUM-42 "region-selection-map"
```

## 실행 단계

### 1. JIRA 티켓 조회 및 상태 변경

**작업:**
- JIRA MCP를 사용하여 티켓 정보 조회
- 티켓 상태를 "진행 중"으로 변경
- Definition of Done 섹션 파싱

**실행:**
```javascript
// 1. 티켓 조회
mcp__atlassian__getJiraIssue(issueIdOrKey: "$TICKET_ID")

// 2. 상태 "진행 중"으로 변경
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "$TICKET_ID",
  transition: "진행 중"
)
```

### 2. Git 브랜치 생성

**브랜치 네이밍 규칙:**
- feature: `feature/SCRUM-XX-description`
- bugfix: `bugfix/SCRUM-XX-description`
- hotfix: `hotfix/SCRUM-XX-description`

**실행:**
```bash
# main 브랜치 최신화
git checkout main
git pull origin main

# 작업 브랜치 생성
git checkout -b feature/$TICKET_ID-$DESCRIPTION
```

### 3. Draft PR 생성 (GitHub CLI)

**실행:**
```bash
gh pr create \
  --draft \
  --base main \
  --head feature/$TICKET_ID-$DESCRIPTION \
  --title "[$TICKET_ID] $TITLE" \
  --body "## 관련 JIRA 티켓
$JIRA_URL

## 작업 내용
- 작업 내용 요약

## Definition of Done
$DOD_CHECKLIST"
```

### 4. Definition of Done → TodoWrite 변환

**Definition of Done 체크리스트를 TodoWrite로 자동 생성:**

프론트엔드 기본 체크리스트:
- [ ] 기능이 정상적으로 동작하는가?
- [ ] `npm run build`가 에러 없이 성공하는가?
- [ ] `npm run lint`가 통과하는가?
- [ ] 브라우저에서 실제 테스트를 수행했는가?
- [ ] 모바일 반응형이 정상 동작하는가?
- [ ] 접근성(a11y) 요구사항을 충족하는가?
- [ ] README 또는 문서가 업데이트되었는가?

백엔드 기본 체크리스트:
- [ ] API가 정상적으로 응답하는가?
- [ ] 단위 테스트가 작성되었는가?
- [ ] 통합 테스트가 통과하는가?
- [ ] API 문서(Swagger)가 업데이트되었는가?
- [ ] 에러 핸들링이 적절한가?
- [ ] 데이터베이스 마이그레이션이 필요한 경우 작성되었는가?

## 출력 예시

```
✅ JIRA 티켓 조회 완료: SCRUM-42
   제목: 지역 선택 인터랙티브 맵 UI 구현
   담당자: hyunjoon
   우선순위: High

✅ 티켓 상태 변경: 할 일 → 진행 중

✅ 브랜치 생성: feature/SCRUM-42-region-selection-map

✅ Draft PR 생성: #123
   URL: https://github.com/org/repo/pull/123

📋 Definition of Done (7개 항목)
   - 기능 동작 확인
   - 빌드 성공
   - 린트 통과
   - 브라우저 테스트
   - 반응형 확인
   - 접근성 확인
   - 문서 업데이트

🎯 다음 단계:
   1. 코드 구현
   2. /jira-test frontend 실행
```

## 프롬프트 템플릿

티켓 번호 `{ticket_id}`와 설명 `{description}`을 받아서 다음을 수행하세요:

1. JIRA MCP로 티켓 `{ticket_id}` 조회
2. 티켓 상태를 "진행 중"으로 변경
3. Git 브랜치 생성: `feature/{ticket_id}-{description}`
4. GitHub CLI로 Draft PR 생성 (base: main)
5. Definition of Done을 파싱하여 TodoWrite 생성
6. 작업 시작 안내 메시지 출력

티켓의 프론트엔드/백엔드 여부를 자동 판단하여 적절한 Definition of Done 체크리스트를 생성하세요.
