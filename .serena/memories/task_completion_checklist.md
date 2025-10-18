# Task Completion Checklist

When completing a task in this project, **ALWAYS** follow this checklist based on the task type.

## Frontend Task Completion

### 1. Code Quality Checks
```bash
cd frontend

# ESLint must pass (will fail CI if errors)
npm run lint

# Prettier formatting (auto-fix)
npm run format

# Build must succeed
npm run build
```

### 2. Browser Testing (Required)
- [ ] Test in Chrome/Safari (desktop)
- [ ] Test on mobile device or Chrome DevTools mobile view
- [ ] Check responsive design (mobile, tablet, desktop)
- [ ] Verify accessibility (keyboard navigation, screen reader)
- [ ] Test loading states and error handling

### 3. Integration Testing
- [ ] Verify API integration works correctly
- [ ] Test with real backend API (if available)
- [ ] Test fallback/error scenarios
- [ ] Verify data persistence (localStorage, if used)

### 4. Performance Checks
- [ ] Check bundle size impact (keep initial < 500KB)
- [ ] Verify images use Next.js Image component
- [ ] Test loading performance (Lighthouse if possible)
- [ ] Check for console errors/warnings

## Backend Task Completion

### 1. Code Quality Checks
```bash
cd backend

# Unit tests must pass
./gradlew test

# Build must succeed
./gradlew clean build

# Code style (if configured)
./gradlew checkstyle
```

### 2. API Testing (Required)
```bash
# Health check
curl http://localhost:8080/api/v1/health

# Test your new endpoint (example)
curl -X POST http://localhost:8080/api/v1/courses/generate \
  -H "Content-Type: application/json" \
  -d '{"region": "홍대", "date_type": "문화데이트", "budget": 100000}'
```

### 3. Integration Testing
- [ ] Test with real database (PostgreSQL)
- [ ] Test with real external APIs (Kakao, Claude)
- [ ] Verify error handling (400, 404, 500 responses)
- [ ] Test performance under load (if applicable)
- [ ] Check logs for errors/warnings

### 4. Database Checks (if applicable)
- [ ] Verify migrations run successfully
- [ ] Check database schema matches entity definitions
- [ ] Test queries with real data
- [ ] Verify indexes are created correctly

## Infrastructure/DevOps Task Completion

### 1. Terraform Validation
```bash
cd terraform

# Initialize Terraform
terraform init

# Validate configuration
terraform validate

# Plan changes (dry run)
terraform plan -var-file=environments/dev/terraform.tfvars

# Apply changes (if approved)
terraform apply -var-file=environments/dev/terraform.tfvars
```

### 2. Docker/Container Testing
```bash
# Build Docker image
docker build -t ddalkkak-backend:test .

# Run container locally
docker run -p 8080:8080 ddalkkak-backend:test

# Test health check
curl http://localhost:8080/api/v1/health
```

### 3. Deployment Verification
- [ ] Check ECS task is running healthy
- [ ] Verify ALB health checks pass
- [ ] Test application via public URL
- [ ] Check CloudWatch logs for errors
- [ ] Verify Sentry is receiving telemetry

## JIRA Workflow Integration

### 1. Before Starting Task
```bash
# 1. Fetch JIRA ticket details
mcp__atlassian__getJiraIssue(issueIdOrKey: "SCRUM-XX")

# 2. Transition ticket to "In Progress"
mcp__atlassian__transitionJiraIssue(issueIdOrKey: "SCRUM-XX", transition: "진행 중")

# 3. Create feature branch
git checkout -b feature/SCRUM-XX-brief-description
```

### 2. During Development
- [ ] Review Definition of Done from JIRA ticket
- [ ] Track progress with TodoWrite tool
- [ ] Commit frequently with meaningful messages

### 3. Before Marking Complete
- [ ] ALL checklist items above must pass
- [ ] Definition of Done criteria met
- [ ] Add JIRA comment with implementation details

```bash
mcp__atlassian__addCommentToJiraIssue(
  issueIdOrKey: "SCRUM-XX",
  commentBody: "## 진행 상황\n\n- 구현 완료\n- 테스트 통과\n- 브랜치: feature/SCRUM-XX-..."
)
```

### 4. After Task Completion
```bash
# 1. Push changes
git push origin feature/SCRUM-XX-brief-description

# 2. Create/update PR
gh pr create --title "[SCRUM-XX] Task title" --body "Description"

# 3. Transition JIRA to "검토 중" or "완료"
mcp__atlassian__transitionJiraIssue(issueIdOrKey: "SCRUM-XX", transition: "완료")
```

## Definition of Done (General)

Every task is considered "Done" when:

### Code Quality
- [ ] No ESLint errors (frontend)
- [ ] All tests pass (frontend & backend)
- [ ] Code follows project conventions
- [ ] No console.error or System.err in production code

### Functionality
- [ ] Feature works as specified in JIRA ticket
- [ ] All edge cases handled
- [ ] Error messages are user-friendly
- [ ] Loading states implemented

### Testing
- [ ] Manual testing completed
- [ ] Integration testing with real APIs
- [ ] Responsive design verified (frontend)
- [ ] Accessibility checked (frontend)

### Documentation
- [ ] Code comments for complex logic
- [ ] API documentation updated (Swagger)
- [ ] README updated if needed
- [ ] JIRA ticket updated with details

### Deployment
- [ ] Build succeeds without warnings
- [ ] No new security vulnerabilities introduced
- [ ] Environment variables documented
- [ ] Backward compatibility maintained

## Common Pitfalls to Avoid

❌ **Don't Mark Complete If:**
- Tests fail or are skipped
- Build has errors or critical warnings
- Integration testing not performed
- JIRA ticket not updated
- Definition of Done not met
- Code not pushed to remote branch

✅ **Always Do:**
- Run ALL quality checks before marking complete
- Test with real data and real APIs
- Verify changes in actual browser/environment
- Update JIRA with implementation notes
- Add comments to PR linking to JIRA ticket
