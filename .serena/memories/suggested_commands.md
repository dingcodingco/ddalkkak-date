# Suggested Commands

## Frontend (Next.js)

### Development
```bash
cd frontend

# Install dependencies
npm install

# Run development server (http://localhost:3000)
npm run dev

# Build for production
npm run build

# Start production server
npm start
```

### Code Quality
```bash
cd frontend

# Run ESLint (check code quality)
npm run lint

# Run Prettier (format all code)
npm run format

# Run tests
npm test
npm run test:watch      # Watch mode
npm run test:coverage   # With coverage report
```

### Environment Setup
```bash
cd frontend

# Copy environment template
cp .env.example .env.local

# Edit .env.local with your API keys
# Required:
#   NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api/v1
#   NEXT_PUBLIC_KAKAO_MAP_API_KEY=your_key
#   NEXT_PUBLIC_KAKAO_LOGIN_KEY=your_key
```

## Backend (Spring Boot)

### Development
```bash
cd backend

# Build project
./gradlew clean build

# Run with local profile (http://localhost:8080)
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun

# Build without tests
./gradlew clean build -x test

# Run JAR directly
java -jar build/libs/ddalkkak-backend-0.0.1-SNAPSHOT.jar
```

### Testing
```bash
cd backend

# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests CourseGenerationControllerTest

# Run tests with coverage
./gradlew test jacocoTestReport
```

### Environment Setup
```bash
cd backend

# Copy environment template
cp .env.example .env

# Edit .env with your credentials
# Required:
#   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ddalkkak
#   SPRING_DATASOURCE_USERNAME=postgres
#   SPRING_DATASOURCE_PASSWORD=your_password
#   CLAUDE_API_KEY=your_anthropic_api_key
```

## Database (Docker Compose)

### PostgreSQL Management
```bash
# Start PostgreSQL container
docker-compose up -d

# Start with pgAdmin (optional)
docker-compose --profile tools up -d

# Check container status
docker-compose ps

# View PostgreSQL logs
docker-compose logs -f postgres

# Connect to PostgreSQL via psql
docker-compose exec postgres psql -U postgres -d ddalkkak

# Stop containers
docker-compose down

# Stop and remove all data (reset)
docker-compose down -v

# Restart containers
docker-compose restart

# Restart specific service
docker-compose restart postgres
```

### pgAdmin Access (if started with --profile tools)
- URL: http://localhost:5050
- Email: admin@ddalkkak.com
- Password: (from .env file)

## Git Workflow

### Branch Management
```bash
# Create feature branch
git checkout -b feature/SCRUM-XX-brief-description

# Examples:
git checkout -b feature/SCRUM-42-region-selection-map
git checkout -b bugfix/SCRUM-43-login-validation
git checkout -b hotfix/SCRUM-44-api-timeout
```

### Commit & Push
```bash
# Stage changes
git add .

# Commit with message
git commit -m "feat(component): description

- Detail 1
- Detail 2
- Detail 3

JIRA: SCRUM-XX"

# Push to remote
git push origin feature/SCRUM-XX-brief-description
```

### Merge to Main
```bash
# Update main branch
git checkout main
git pull origin main

# Merge feature branch
git merge --no-ff feature/SCRUM-XX-brief-description

# Push merged changes
git push origin main

# Delete feature branch
git branch -d feature/SCRUM-XX-brief-description
```

## Monitoring & Debugging

### Backend Health Check
```bash
# Check server status
curl http://localhost:8080/api/v1/health

# Expected response:
# {
#   "status": "UP",
#   "message": "딸깍데이트 백엔드 서버가 정상적으로 실행 중입니다.",
#   "timestamp": "2025-10-18T...",
#   "version": "0.0.1-SNAPSHOT"
# }
```

### View API Documentation
```bash
# Swagger UI
open http://localhost:8080/swagger-ui.html

# OpenAPI JSON
open http://localhost:8080/api-docs
```

### View Application Logs
```bash
# Backend logs (if running with Gradle)
tail -f backend/logs/application.log

# Docker container logs
docker-compose logs -f backend
docker-compose logs -f postgres
```

## macOS-Specific Commands

### File Operations
```bash
# Find files (case-insensitive on macOS)
find . -name "*.java" -type f

# Search in files
grep -r "searchterm" src/

# List directory tree
tree -L 2  # Install with: brew install tree
```

### Process Management
```bash
# Find process using port
lsof -i :8080  # Backend port
lsof -i :3000  # Frontend port

# Kill process by port
kill -9 $(lsof -t -i:8080)
```

### Package Management (Homebrew)
```bash
# Install Java 17
brew install openjdk@17

# Install Node.js
brew install node

# Install PostgreSQL client
brew install postgresql@15

# Install Docker Desktop
brew install --cask docker
```

## Task Completion Checklist

Before marking a task as complete, always run:

### Frontend Tasks
```bash
cd frontend
npm run lint      # Must pass
npm run build     # Must succeed
npm test          # Must pass (if tests exist)
```

### Backend Tasks
```bash
cd backend
./gradlew test    # Must pass
./gradlew build   # Must succeed
```

### Integration Testing
- Frontend: Test in browser (http://localhost:3000)
- Backend: Test with curl or Postman
- End-to-end: Test full user flow with Playwright (if configured)
