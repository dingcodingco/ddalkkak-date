# Development Workflow

## JIRA-Driven Development Process

This project follows a strict JIRA ticket workflow. **Every code change must be linked to a JIRA ticket.**

### 1. Starting a New Task

```bash
# Step 1: Fetch JIRA ticket
mcp__atlassian__getJiraIssue(issueIdOrKey: "SCRUM-XX")

# Step 2: Review ticket carefully
# - Read description
# - Check Definition of Done
# - Understand acceptance criteria
# - Clarify unclear requirements

# Step 3: Transition ticket to "In Progress"
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  transition: "진행 중"
)

# Step 4: Create feature branch
git checkout develop
git pull origin develop
git checkout -b feature/SCRUM-XX-brief-description

# Examples:
# git checkout -b feature/SCRUM-42-region-selection-map
# git checkout -b bugfix/SCRUM-43-login-validation
# git checkout -b hotfix/SCRUM-44-api-timeout
```

### 2. Development Phase

```bash
# Write code following project conventions
# - Frontend: Use TypeScript, Tailwind CSS, follow ESLint rules
# - Backend: Use Lombok, follow REST conventions, DTO pattern

# Commit frequently with meaningful messages
git add .
git commit -m "feat(component): brief description

- Detailed change 1
- Detailed change 2
- Detailed change 3

JIRA: SCRUM-XX"

# Commit message format:
# <type>(<scope>): <subject>
#
# <body>
#
# JIRA: SCRUM-XX

# Types: feat, fix, docs, style, refactor, test, chore
```

### 3. Testing Phase

#### Frontend Testing
```bash
cd frontend

# 1. Lint check
npm run lint

# 2. Format code
npm run format

# 3. Run tests
npm test

# 4. Build check
npm run build

# 5. Manual browser testing
npm run dev
# Open http://localhost:3000
# Test all user flows
# Check mobile responsive
# Verify accessibility
```

#### Backend Testing
```bash
cd backend

# 1. Run unit tests
./gradlew test

# 2. Build project
./gradlew clean build

# 3. Start server
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun

# 4. API testing with curl
curl http://localhost:8080/api/v1/health

# 5. Test your endpoint
curl -X POST http://localhost:8080/api/v1/your-endpoint \
  -H "Content-Type: application/json" \
  -d '{"key": "value"}'

# 6. Check Swagger UI
open http://localhost:8080/swagger-ui.html
```

### 4. Integration Testing (Required)

#### For Frontend Changes
- [ ] Test with real backend API running
- [ ] Test in Chrome and Safari
- [ ] Test on mobile device (or Chrome DevTools)
- [ ] Verify loading states and error handling
- [ ] Check network tab for API calls
- [ ] Verify console has no errors

#### For Backend Changes
- [ ] Test API with curl or Postman
- [ ] Test success cases
- [ ] Test error cases (400, 401, 404, 500)
- [ ] Test edge cases and boundary values
- [ ] Verify database changes
- [ ] Check application logs

### 5. Code Review Preparation

```bash
# Push changes to remote
git push origin feature/SCRUM-XX-brief-description

# Create Pull Request
gh pr create \
  --title "[SCRUM-XX] Brief task description" \
  --body "## Changes\n- Change 1\n- Change 2\n\n## Testing\n- Test scenario 1\n- Test scenario 2\n\nJIRA: SCRUM-XX"

# OR use GitHub web interface
```

### 6. JIRA Update

```bash
# Add progress comment
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  commentBody: "## 진행 상황

### 구현 내역
- Feature 1 implemented
- Feature 2 implemented

### 테스트 결과
- ✅ Unit tests: PASS
- ✅ Integration tests: PASS
- ✅ Build: SUCCESS
- ✅ Lint: PASS

### 브랜치
- feature/SCRUM-XX-brief-description
- PR: #123"
)

# Transition to "검토 중" (Review)
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  transition: "검토 중"
)
```

### 7. Addressing Review Comments

```bash
# Make requested changes
git add .
git commit -m "fix: address review comments

- Fix issue 1
- Improve logic for scenario 2

JIRA: SCRUM-XX"

git push origin feature/SCRUM-XX-brief-description

# Add comment to JIRA
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  commentBody: "## 리뷰 반영\n\n- Comment 1 addressed\n- Comment 2 fixed"
)
```

### 8. Merge to Main/Develop

```bash
# After PR approval, merge via GitHub UI or CLI
gh pr merge <PR_NUMBER> --squash --delete-branch

# OR manually merge
git checkout develop
git pull origin develop
git merge --no-ff feature/SCRUM-XX-brief-description
git push origin develop
git branch -d feature/SCRUM-XX-brief-description
```

### 9. Final JIRA Update

```bash
# Add completion comment
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  commentBody: "## ✅ 완료

### 구현 내역
- Feature 1: Complete
- Feature 2: Complete

### 테스트 결과
- Integration tests: ✅ PASS
- Build: ✅ SUCCESS
- Deployment: ✅ SUCCESS

### 브랜치 정보
- Branch: feature/SCRUM-XX-brief-description
- Merged to: develop
- PR: #123
- Commit: abc1234"
)

# Transition to "완료" (Done)
mcp__atlassian__transitionJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  transition: "완료"
)
```

## Prohibited Actions

### ❌ Never Do These

1. **Direct commits to main/develop**
   - Always use feature branches
   - Always create PR for review

2. **Merge without passing checks**
   - Lint must pass
   - Tests must pass
   - Build must succeed

3. **Merge without testing**
   - Frontend: Browser testing required
   - Backend: API testing required
   - Integration testing mandatory

4. **JIRA updates without evidence**
   - Always include test results
   - Always include branch/PR links
   - Always document what was done

5. **Mark ticket complete prematurely**
   - Definition of Done must be met
   - All checklist items completed
   - Integration testing finished

## Branch Naming Convention

```
<type>/SCRUM-<number>-<brief-description>

Examples:
feature/SCRUM-42-region-selection-map
bugfix/SCRUM-43-login-validation
hotfix/SCRUM-44-api-timeout
refactor/SCRUM-45-service-layer-cleanup
docs/SCRUM-46-api-documentation-update
```

## Git Commit Best Practices

### Good Commit Messages
```
✅ feat(frontend): add region selection interactive map

- Implement SVG-based Seoul district map
- Add hover/click interactions
- Integrate with regions metadata API
- Add responsive design for mobile

JIRA: SCRUM-42
```

```
✅ fix(backend): handle null pointer in course generation

- Add null checks for Claude API response
- Implement fallback logic for timeout
- Add unit tests for edge cases

JIRA: SCRUM-43
```

### Bad Commit Messages
```
❌ "update code"
❌ "fix bug"
❌ "wip"
❌ "changes"
❌ Commit without JIRA reference
```

## Environment-Specific Workflow

### Local Development
- Use `SPRING_PROFILES_ACTIVE=local` for backend
- Use `.env.local` for frontend
- Test with localhost URLs

### Staging/Production
- Use environment-specific profiles
- Test with real URLs (not localhost)
- Verify environment variables
- Check monitoring dashboards

## Emergency Hotfix Process

For critical production bugs:

```bash
# 1. Create hotfix branch from main
git checkout main
git pull origin main
git checkout -b hotfix/SCRUM-XX-critical-bug

# 2. Fix and test thoroughly
# ... make changes ...
./gradlew test  # Backend
npm run build   # Frontend

# 3. Create PR with "HOTFIX" label
gh pr create --title "[HOTFIX] SCRUM-XX: Critical bug" --base main

# 4. Get immediate review and merge
# 5. Deploy to production ASAP
# 6. Backport to develop
git checkout develop
git merge hotfix/SCRUM-XX-critical-bug
git push origin develop
```

## Daily Development Routine

### Morning
1. Pull latest changes: `git pull origin develop`
2. Check JIRA board for assigned tickets
3. Review priorities with team

### During Work
1. Follow JIRA workflow strictly
2. Commit frequently (at least every 2-4 hours)
3. Push to remote at end of day
4. Update JIRA with progress

### End of Day
1. Push all commits: `git push origin <branch>`
2. Update JIRA with status
3. Create PR if task ready for review
4. Document any blockers in JIRA comments
