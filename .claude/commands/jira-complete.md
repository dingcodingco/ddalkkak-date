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

### 6. 문서 업데이트 (docs/ 폴더)

**작업 컨텍스트 및 의사결정 문서화:**

작업 중 발생한 중요한 의사결정, 기술적 선택, 컨텍스트를 `docs/` 폴더에 기록합니다.

**문서 유형 및 위치:**

1. **기술적 의사결정 (ADR - Architecture Decision Records)**
   - 위치: `docs/decisions/ADR-YYYY-MM-DD-{제목}.md`
   - 내용: 기술 스택 선택, 아키텍처 결정, 설계 패턴 채택 이유
   - 예시: `docs/decisions/ADR-2025-01-14-선택-카카오맵-API.md`

2. **작업 컨텍스트 (Work Context)**
   - 위치: `docs/context/SCRUM-{번호}-{제목}.md`
   - 내용: 티켓 작업 중 발견한 이슈, 해결 방법, 학습 내용
   - 예시: `docs/context/SCRUM-42-지역선택-맵-구현.md`

3. **API/기능 명세 (Specifications)**
   - 위치: `docs/specs/{기능명}.md`
   - 내용: API 설계, 데이터 모델, 인터페이스 정의
   - 예시: `docs/specs/course-recommendation-api.md`

4. **운영 가이드 (Operations)**
   - 위치: `docs/operations/{주제}.md`
   - 내용: 배포 절차, 장애 대응, 모니터링 가이드
   - 예시: `docs/operations/sentry-monitoring.md`

**문서 템플릿 예시:**

**기술적 의사결정 (ADR):**
```markdown
# ADR-YYYY-MM-DD: {제목}

## 상태
제안됨 | 승인됨 | 대체됨 | 폐기됨

## 컨텍스트
{어떤 문제를 해결하려고 했는가?}

## 결정 사항
{어떤 선택을 했는가?}

## 근거
{왜 이 선택을 했는가?}

## 대안
{고려했던 다른 옵션들}

## 결과
{이 결정의 긍정적/부정적 영향}

## 관련 티켓
- JIRA: SCRUM-XX
- GitHub PR: #YY
```

**작업 컨텍스트:**
```markdown
# SCRUM-{번호}: {티켓 제목}

## 작업 개요
{티켓 요약}

## 기술적 도전 과제
{구현 중 직면한 어려움}

## 해결 방법
{문제를 어떻게 해결했는가}

## 학습 내용
{이 작업을 통해 배운 것}

## 향후 개선 사항
{리팩토링이나 최적화가 필요한 부분}

## 관련 자료
- 참고 문서
- 외부 링크
- 관련 코드
```

**문서 업데이트 체크리스트:**
```
📝 다음 항목 중 해당하는 문서를 작성/업데이트했습니까?

기술적 의사결정:
- [ ] 새로운 기술 스택 도입
- [ ] 아키텍처 패턴 변경
- [ ] 서드파티 라이브러리 선택

작업 컨텍스트:
- [ ] 복잡한 버그 해결 과정
- [ ] 새로운 기능 구현 인사이트
- [ ] 성능 최적화 방법

API/기능 명세:
- [ ] 새로운 API 엔드포인트
- [ ] 데이터 모델 변경
- [ ] 인터페이스 수정

운영 가이드:
- [ ] 배포 절차 변경
- [ ] 모니터링 설정 추가
- [ ] 장애 대응 프로세스

해당 없음을 확인한 경우 다음 단계로 진행하세요.
```

**문서 작성 예시 (자동 생성):**

```bash
# 작업 컨텍스트 문서 자동 생성
cat > docs/context/SCRUM-42-지역선택-맵-구현.md << 'EOF'
# SCRUM-42: 지역 선택 인터랙티브 맵 UI 구현

## 작업 개요
서울 25개 구를 SVG 기반 인터랙티브 맵으로 구현하여 사용자가 지역을 선택할 수 있도록 함.

## 기술적 도전 과제
1. SVG 좌표 계산의 복잡성
2. 접근성(a11y) 구현 - 키보드 네비게이션
3. 반응형 디자인 적용

## 해결 방법
1. GeoJSON 데이터를 SVG path로 변환
2. ARIA 레이블 및 role 속성 추가
3. CSS transform과 viewport 기반 스케일링

## 학습 내용
- SVG path 데이터 구조 이해
- 키보드 접근성 패턴 (Tab, Enter, Arrow keys)
- Next.js에서 SVG 컴포넌트 최적화

## 향후 개선 사항
- 지도 줌/팬 기능 추가
- 터치 제스처 지원 개선
- 성능 최적화 (path simplification)

## 관련 자료
- GitHub PR: #123
- Figma 디자인: [링크]
- 참고: https://d3js.org/d3-geo
EOF
```

### 7. 배포 확인 (선택)

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
6. **문서 업데이트 (docs/ 폴더):**
   - 작업 컨텍스트 문서 작성/업데이트 체크
   - 기술적 의사결정(ADR) 필요 여부 확인
   - API/기능 명세 업데이트 필요 여부 확인
   - 해당하는 문서 자동 생성 또는 업데이트 가이드 제공
7. JIRA 상태를 "완료"로 변경
8. 배포 확인 (선택)
9. 로컬 환경 정리 (브랜치 삭제, develop 동기화)
10. 완료 요약 메시지 출력

실패 시 명확한 에러 메시지와 해결 방법을 제시하세요.

## 문서화 자동화

작업 완료 시 다음 정보를 바탕으로 적절한 문서를 자동 생성하거나 업데이트 가이드를 제공:

1. **JIRA 티켓 정보**에서:
   - 티켓 제목 및 설명
   - Definition of Done
   - 작업 히스토리

2. **GitHub PR 정보**에서:
   - 커밋 메시지 분석
   - 변경된 파일 목록
   - 코드 리뷰 코멘트

3. **자동 생성 조건**:
   - 새로운 API 엔드포인트 추가 → `docs/specs/` 생성
   - 새로운 기술 스택 도입 → `docs/decisions/ADR` 생성
   - 복잡한 버그 해결 → `docs/context/SCRUM` 생성
   - 배포/운영 절차 변경 → `docs/operations/` 업데이트
