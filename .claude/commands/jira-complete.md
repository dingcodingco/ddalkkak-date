# JIRA 티켓 완료 처리

최종 검증 후 PR을 머지하고 JIRA 티켓을 완료 처리합니다.

## 사용법

```bash
/jira-complete SCRUM-XX [PR번호]
```

**예시:**
```bash
/jira-complete SCRUM-42 123
/jira-complete SCRUM-42  # PR 번호 자동 추출
```

## 실행 단계

### 1. Definition of Done 최종 확인

**자동 검증 항목:**
- [x] 통합 테스트 통과 (이전 단계에서 확인)
- [x] 빌드 성공
- [x] Lint 통과
- [x] Git 커밋 완료
- [x] GitHub PR Ready for Review

**수동 확인 필요 항목:**
```
❓ 다음 항목들을 수동으로 확인했는지 최종 체크:

프론트엔드:
- [ ] 모바일 반응형이 정상 동작하는가?
- [ ] 접근성(a11y) 요구사항을 충족하는가?
- [ ] README 또는 문서가 업데이트되었는가?

백엔드:
- [ ] API 문서(Swagger)가 업데이트되었는가?
- [ ] 데이터베이스 마이그레이션이 작성되었는가? (필요 시)

📝 미완료 항목이 있다면 지금 완료하세요. 계속 진행하시겠습니까? (y/N)
```

### 2. GitHub PR 정보 조회

```bash
# PR 정보 조회
gh pr view $PR_NUMBER --json number,title,state,mergeable,reviews

# 또는 현재 브랜치의 PR 자동 조회
gh pr view --json number,title,state,mergeable,reviews
```

**머지 가능 여부 확인:**
- PR 상태: Open
- Mergeable: MERGEABLE
- Reviews: Approved (선택)
- CI/CD: Passed (GitHub Actions)

### 3. GitHub PR 머지

**권장 머지 방식: Squash Merge**

```bash
# Squash 머지 (권장)
gh pr merge $PR_NUMBER --squash --delete-branch

# 또는 머지 옵션 선택
gh pr merge $PR_NUMBER --squash --delete-branch --body "✅ JIRA SCRUM-42 완료"
```

**머지 옵션:**
- `--merge`: 일반 머지 (모든 커밋 유지)
- `--squash`: 스쿼시 머지 (하나의 커밋으로 합침, **권장**)
- `--rebase`: 리베이스 머지

**브랜치 정리:**
```bash
# 로컬 브랜치 삭제
git checkout develop
git branch -D feature/SCRUM-42-region-selection-map

# 원격 브랜치 삭제 (--delete-branch로 자동 처리됨)
```

### 4. JIRA 최종 완료 코멘트

**코멘트 템플릿:**
```markdown
## ✅ 완료

### 구현 내역
- {구현 항목 1}
- {구현 항목 2}
- {구현 항목 3}

### 테스트 결과
- 통합 테스트: ✅ PASS
- 빌드: ✅ SUCCESS
- Lint: ✅ PASS
- E2E 테스트: ✅ PASS (프론트엔드)
- 단위 테스트: ✅ PASS (백엔드)
- 통합 테스트: ✅ PASS (백엔드)

### 브랜치 정보
- 작업 브랜치: `feature/SCRUM-XX-description`
- 머지됨: `develop`
- 커밋: {commit_hash}
- GitHub PR: #{pr_number} (Squash Merged)

### 배포 정보
- 배포 환경: Development
- 배포 시간: {timestamp}
- 배포 URL: {deploy_url}

### 완료 시각
{completion_timestamp}
```

**JIRA 코멘트 작성:**
```javascript
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-42",
  commentBody: `## ✅ 완료

### 구현 내역
- SVG 기반 서울 지도 컴포넌트 구현
- 지역 hover/click 인터랙션 구현
- API 연동 완료
- 반응형 디자인 적용
- 접근성 개선 (키보드 네비게이션, ARIA 레이블)

### 테스트 결과
- 통합 테스트: ✅ PASS
- 빌드: ✅ SUCCESS
- Lint: ✅ PASS
- Playwright E2E: ✅ PASS (5개 시나리오)
- 반응형 테스트: ✅ PASS (모바일/태블릿/데스크톱)
- 접근성 테스트: ✅ PASS (WCAG 2.1 AA)

### 브랜치 정보
- 작업 브랜치: \`feature/SCRUM-42-region-selection-map\`
- 머지됨: \`develop\`
- 커밋: \`a1b2c3d4\`
- GitHub PR: #123 (Squash Merged)

### 배포 정보
- 배포 환경: Development (Vercel)
- 배포 시간: 2025-01-14 10:30:00 KST
- 배포 URL: https://ddalkkak-date-dev.vercel.app

### 완료 시각
2025-01-14 10:30:15 KST`
)
```

### 5. JIRA 상태 변경

```javascript
// 티켓 상태를 "완료"로 변경
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-42",
  transition: "완료"
)
```

### 6. 배포 확인 (선택)

**프론트엔드 (Vercel):**
```bash
# Vercel CLI로 배포 상태 확인
vercel ls

# 최신 배포 URL 가져오기
vercel inspect --json | jq -r '.url'
```

**백엔드 (AWS ECS):**
```bash
# ECS 서비스 배포 상태 확인
aws ecs describe-services \
  --cluster ddalkkak-cluster \
  --services ddalkkak-backend \
  --query 'services[0].deployments[0]'
```

## 로컬 환경 정리

```bash
# develop 브랜치로 전환
git checkout develop

# 최신 변경사항 pull
git pull origin develop

# 완료된 작업 브랜치 삭제
git branch -D feature/SCRUM-42-region-selection-map

# 원격 브랜치 동기화
git fetch --prune
```

## 출력 예시

```
📋 Definition of Done 최종 확인...
   ✅ 통합 테스트: PASS
   ✅ 빌드: SUCCESS
   ✅ Lint: PASS
   ✅ 문서 업데이트: 완료
   ✅ 반응형: 검증됨
   ✅ 접근성: WCAG 2.1 AA 준수

🔍 GitHub PR 정보 조회...
   - PR #123: [SCRUM-42] 지역 선택 인터랙티브 맵 UI 구현
   - State: OPEN
   - Mergeable: MERGEABLE
   - Reviews: 1 Approved

✅ GitHub PR 머지 완료
   - 머지 방식: Squash Merge
   - 브랜치 삭제: ✅

✅ JIRA 완료 코멘트 작성
   - 구현 내역: 5개 항목
   - 테스트 결과: 모두 통과
   - 브랜치/배포 정보: 포함

✅ JIRA 상태 변경
   - 검토 중 → 완료

🚀 배포 확인
   - 환경: Development (Vercel)
   - URL: https://ddalkkak-date-dev.vercel.app
   - Status: Ready

🧹 로컬 환경 정리
   - develop 브랜치로 전환
   - 작업 브랜치 삭제

🎉 SCRUM-42 완료!
   - 작업 시간: 2일
   - 커밋: 5개 → 1개 (Squash)
   - 변경: +523 -12 lines
```

## 실패 시 롤백

**머지 실패 시:**
```bash
# PR 머지 실패 원인 확인
gh pr checks $PR_NUMBER

# CI/CD 실패 로그 확인
gh run view --log

# 문제 해결 후 재시도
# 1. 로컬에서 수정
# 2. git commit --amend
# 3. git push --force-with-lease
# 4. /jira-complete 재실행
```

**JIRA 상태 롤백:**
```javascript
// 완료 처리가 잘못된 경우 상태 되돌리기
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-42",
  transition: "검토 중"
)
```

## 프롬프트 템플릿

티켓 번호 `{ticket_id}`와 선택적으로 PR 번호 `{pr_number}`를 받아서 다음을 수행하세요:

1. Definition of Done 최종 확인 (자동 + 수동 확인 프롬프트)
2. GitHub PR 정보 조회 (번호 미지정 시 현재 브랜치에서 자동 추출)
3. PR 머지 가능 여부 확인 (Mergeable, Reviews, CI/CD)
4. GitHub PR 머지 실행 (Squash Merge, 브랜치 자동 삭제)
5. JIRA 최종 완료 코멘트 작성:
   - 구현 내역 요약
   - 모든 테스트 결과
   - 브랜치/커밋 정보
   - 배포 정보 (가능한 경우)
6. JIRA 상태를 "완료"로 변경
7. 로컬 환경 정리 (브랜치 삭제, develop 동기화)
8. 완료 요약 메시지 출력

실패 시 명확한 에러 메시지와 해결 방법을 제시하세요.
