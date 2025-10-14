# JIRA 티켓 커밋 및 진행 상황 업데이트

Git 커밋 컨벤션을 준수하여 커밋하고, JIRA 티켓에 진행 상황을 자동으로 업데이트합니다.

## 사용법

```bash
/jira-commit SCRUM-XX "커밋 메시지 요약"
```

**예시:**
```bash
/jira-commit SCRUM-42 "지역 선택 인터랙티브 맵 UI 구현"
```

## 실행 단계

### 1. 변경사항 확인

```bash
# 현재 브랜치 확인
git branch --show-current

# 변경된 파일 목록
git status --short

# 변경 내용 요약
git diff --stat
```

### 2. Git 커밋 (컨벤션 준수)

**커밋 메시지 컨벤션:**
```
{type}({scope}): {subject}

- {상세 변경사항 1}
- {상세 변경사항 2}
- {상세 변경사항 3}

JIRA: {TICKET_ID}
```

**Type:**
- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `docs`: 문서 변경
- `style`: 코드 포맷팅 (기능 변경 없음)
- `refactor`: 코드 리팩토링
- `test`: 테스트 추가/수정
- `chore`: 빌드 설정, 패키지 매니저 설정

**Scope:**
- `frontend`: 프론트엔드 변경
- `backend`: 백엔드 변경
- `infra`: 인프라 변경
- `docs`: 문서 변경
- `component`: 특정 컴포넌트 변경

**커밋 실행:**
```bash
git add .

git commit -m "feat(frontend): 지역 선택 인터랙티브 맵 UI 구현

- SVG 기반 서울 지도 컴포넌트 추가
- 지역 hover/click 인터랙션 구현
- API 연동 및 데이터 바인딩
- 반응형 디자인 적용

JIRA: SCRUM-42"
```

### 3. Git Push

```bash
# 현재 브랜치를 origin으로 푸시
git push origin $(git branch --show-current)

# 또는 명시적으로
git push origin feature/SCRUM-42-region-selection-map
```

### 4. GitHub PR 상태 업데이트

```bash
# Draft PR을 Ready for Review로 변경
gh pr ready

# 또는 PR 번호로 지정
gh pr ready 123

# PR에 코멘트 추가 (선택)
gh pr comment --body "✅ 통합 테스트 완료. 리뷰 요청합니다."
```

### 5. JIRA 진행 상황 코멘트

**코멘트 템플릿:**
```markdown
## 📝 진행 상황 업데이트

### 구현 완료 항목
- {완료 항목 1}
- {완료 항목 2}
- {완료 항목 3}

### 테스트 결과
- 통합 테스트: ✅ PASS
- 빌드: ✅ SUCCESS
- Lint: ✅ PASS

### 브랜치 정보
- 작업 브랜치: `feature/SCRUM-XX-description`
- 최신 커밋: `{commit_hash}`
- GitHub PR: #{pr_number}

### 다음 단계
- 코드 리뷰 대기
- 리뷰 반영 후 머지 예정
```

**JIRA 코멘트 작성:**
```javascript
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-42",
  commentBody: `## 📝 진행 상황 업데이트

### 구현 완료 항목
- SVG 기반 서울 지도 컴포넌트 구현
- 지역 hover/click 인터랙션 구현
- API 연동 완료
- 반응형 디자인 적용

### 테스트 결과
- 통합 테스트: ✅ PASS
- 빌드: ✅ SUCCESS
- Lint: ✅ PASS

### 브랜치 정보
- 작업 브랜치: \`feature/SCRUM-42-region-selection-map\`
- 최신 커밋: \`a1b2c3d\`
- GitHub PR: #123

### 다음 단계
- 코드 리뷰 대기
- 리뷰 반영 후 머지 예정`
)
```

### 6. JIRA 상태 변경

```javascript
// 티켓 상태를 "검토 중"으로 변경
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-42",
  transition: "검토 중"
)
```

## 변경사항 요약 자동 생성

**Git diff를 분석하여 자동으로 변경사항 요약:**

```bash
# 추가된 파일
git diff --name-status develop...HEAD | grep "^A"

# 수정된 파일
git diff --name-status develop...HEAD | grep "^M"

# 삭제된 파일
git diff --name-status develop...HEAD | grep "^D"

# 변경 라인 수
git diff --stat develop...HEAD
```

**예시 출력:**
```
📊 변경사항 요약:
- 추가된 파일: 5개
  - src/components/RegionMap.tsx
  - src/components/RegionMap.module.css
  - src/types/region.ts
  - src/api/region.ts
  - src/hooks/useRegionSelection.ts

- 수정된 파일: 2개
  - src/pages/index.tsx
  - package.json

- 총 변경: +523 -12 (535 lines)
```

## 커밋 메시지 검증

**커밋 메시지가 컨벤션을 준수하는지 검증:**

```javascript
const commitMessage = `feat(frontend): 지역 선택 인터랙티브 맵 UI 구현

- SVG 기반 서울 지도 컴포넌트 추가
- 지역 hover/click 인터랙션 구현

JIRA: SCRUM-42`;

// 검증 규칙
const validTypes = ['feat', 'fix', 'docs', 'style', 'refactor', 'test', 'chore'];
const hasJiraRef = commitMessage.includes('JIRA:');
const hasType = validTypes.some(type => commitMessage.startsWith(type));

if (!hasType) {
  console.error("❌ 커밋 메시지에 올바른 type이 없습니다.");
}

if (!hasJiraRef) {
  console.error("❌ 커밋 메시지에 JIRA 참조가 없습니다.");
}
```

## 출력 예시

```
✅ 변경사항 확인 완료
   - 브랜치: feature/SCRUM-42-region-selection-map
   - 변경 파일: 7개 (추가 5, 수정 2)
   - 변경 라인: +523 -12

✅ Git 커밋 생성
   - Type: feat(frontend)
   - Subject: 지역 선택 인터랙티브 맵 UI 구현
   - Commit: a1b2c3d4

✅ Git Push 완료
   - Remote: origin/feature/SCRUM-42-region-selection-map

✅ GitHub PR 업데이트
   - Status: Draft → Ready for Review
   - PR #123: https://github.com/org/repo/pull/123

✅ JIRA 코멘트 작성 완료
   - 진행 상황 업데이트됨

✅ JIRA 상태 변경
   - 진행 중 → 검토 중

🎯 다음 단계:
   1. 코드 리뷰 대기
   2. 리뷰 반영 (필요시)
   3. /jira-complete SCRUM-42 123 실행
```

## 프롬프트 템플릿

티켓 번호 `{ticket_id}`와 커밋 메시지 요약 `{message}`을 받아서 다음을 수행하세요:

1. 현재 브랜치 및 변경사항 확인
2. Git diff 분석하여 변경사항 자동 요약
3. 커밋 메시지 컨벤션 준수 확인
4. Git 커밋 생성 (type 자동 판단, JIRA 참조 포함)
5. Git push 실행
6. GitHub CLI로 PR을 Ready for Review로 변경
7. JIRA 진행 상황 코멘트 자동 생성 및 작성
8. JIRA 상태를 "검토 중"으로 변경
9. 작업 완료 및 다음 단계 안내

변경사항을 분석하여 적절한 commit type (feat/fix/refactor 등)을 자동으로 판단하세요.
