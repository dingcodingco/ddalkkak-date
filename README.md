# 딸깍데이트 (DdalkkakDate)

AI 기반 맞춤형 데이트 코스 추천 서비스

## 프로젝트 소개

딸깍데이트는 사용자의 취향과 예산, 위치를 고려하여 AI가 최적의 데이트 코스를 추천해주는 서비스입니다.

## 기술 스택

### Frontend
- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **State Management**: Zustand
- **Deployment**: Vercel

### Backend
- **Framework**: Spring Boot 3.2
- **Language**: Java 17
- **Build Tool**: Gradle 8.10
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA
- **Documentation**: Springdoc OpenAPI (Swagger)
- **AI Integration**: Anthropic Claude API

### Infrastructure
- **Container**: Docker & Docker Compose
- **Database**: PostgreSQL 15
- **Database Management**: pgAdmin 4 (optional)

## 프로젝트 구조

```
ddalkkak-date/
├── frontend/              # Next.js 웹 애플리케이션
├── backend/               # Spring Boot API 서버
├── infrastructure/        # Terraform IaC (예정)
├── docker-compose.yml     # 로컬 개발 환경 설정
├── .env.example           # 환경 변수 템플릿
├── prd.md                 # Product Requirements Document
└── README.md              # 이 파일
```

## 시작하기

### 사전 요구사항

- Node.js 18 이상
- Java 17 이상
- Docker & Docker Compose
- Git

### 1. 저장소 클론

```bash
git clone <repository-url>
cd ddalkkak-date
```

### 2. 환경 변수 설정

```bash
# 루트 디렉토리에서
cp .env.example .env

# .env 파일을 열어서 필요한 값들을 설정하세요
```

필수 환경 변수:
- `POSTGRES_PASSWORD`: PostgreSQL 비밀번호
- `CLAUDE_API_KEY`: Anthropic Claude API 키
- `NEXT_PUBLIC_KAKAO_MAP_API_KEY`: 카카오맵 API 키

선택 환경 변수 (LLM Observability):
- `LANGFUSE_PUBLIC_KEY`: Langfuse Public Key (선택)
- `LANGFUSE_SECRET_KEY`: Langfuse Secret Key (선택)
- `LANGFUSE_BASE_URL`: Langfuse Base URL (기본값: https://cloud.langfuse.com)

> **Langfuse 설정 (선택)**
> Langfuse를 사용하면 LLM 호출을 추적하고 비용, 성능, 품질을 모니터링할 수 있습니다.
> 1. [Langfuse Cloud](https://cloud.langfuse.com)에서 무료 계정 생성
> 2. 프로젝트 생성 후 Settings > API Keys에서 Public Key와 Secret Key 발급
> 3. `backend/.env` 파일에 키 추가
> 4. 백엔드 재시작 후 AI 코스 생성 API 호출 시 자동으로 Langfuse에 추적 데이터 전송

### 3. Docker Compose로 데이터베이스 시작

```bash
# PostgreSQL 컨테이너 시작
docker-compose up -d

# pgAdmin도 함께 시작하려면 (선택 사항)
docker-compose --profile tools up -d

# 컨테이너 상태 확인
docker-compose ps

# 로그 확인
docker-compose logs -f postgres
```

PostgreSQL 접속 정보:
- Host: `localhost`
- Port: `5432`
- Database: `ddalkkak`
- Username: `postgres`
- Password: `.env` 파일에서 설정한 값

pgAdmin 접속 (optional):
- URL: http://localhost:5050
- Email: `admin@ddalkkak.com`
- Password: `.env` 파일에서 설정한 값

### 4. 백엔드 실행

```bash
cd backend

# 빌드
./gradlew clean build

# 실행
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

백엔드 서버가 http://localhost:8080 에서 실행됩니다.

**API 문서:**
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

### 5. 프론트엔드 실행

```bash
cd frontend

# 의존성 설치
npm install

# 개발 서버 실행
npm run dev
```

프론트엔드가 http://localhost:3000 에서 실행됩니다.

## Sentry 설정 (에러 추적 및 성능 모니터링)

### 1. Sentry 프로젝트 생성

1. [Sentry.io](https://sentry.io)에서 계정 생성/로그인
2. 새 프로젝트 생성:
   - **Frontend**: Next.js 프로젝트 (`ddalkkak-frontend`)
   - **Backend**: Java/Spring Boot 프로젝트 (`ddalkkak-backend`)
3. 프로젝트 생성 후 DSN 복사

### 2. 환경 변수 설정

**Frontend (`frontend/.env.local`):**
```env
NEXT_PUBLIC_SENTRY_DSN=https://your-sentry-dsn@sentry.io/project-id
NEXT_PUBLIC_SENTRY_ENVIRONMENT=development
NEXT_PUBLIC_SENTRY_TRACES_SAMPLE_RATE=1.0

# Source Map 업로드용 (CI/CD에서만 사용)
SENTRY_AUTH_TOKEN=your_sentry_auth_token
SENTRY_ORG=your_org_name
SENTRY_PROJECT=ddalkkak-frontend
```

**Backend (`.env` 또는 환경 변수):**
```env
SENTRY_DSN=https://your-sentry-dsn@sentry.io/project-id
SENTRY_ENVIRONMENT=development
SENTRY_TRACES_SAMPLE_RATE=1.0
```

### 3. Sentry 작동 확인

**Frontend 테스트:**
```bash
cd frontend
npm install
npm run dev

# 브라우저 콘솔에서 테스트 에러 발생
throw new Error('Test Sentry Error')
```

**Backend 테스트:**
```bash
cd backend
./gradlew bootRun

# curl로 테스트 (존재하지 않는 엔드포인트 호출)
curl http://localhost:8080/api/v1/test-error
```

### 4. Sentry 대시보드 확인

1. Sentry.io 대시보드에서 프로젝트 선택
2. **Issues** 탭에서 발생한 에러 확인
3. **Performance** 탭에서 성능 메트릭 확인
4. **Releases** 탭에서 배포 추적 확인

### 5. Slack 알림 설정 (선택 사항)

1. Sentry 프로젝트 → Settings → Integrations
2. Slack 연동 설정
3. Alert Rules 설정:
   - **Critical Error**: 새로운 치명적 에러 발생 시 알림
   - **Error Rate**: 에러 발생률 5% 초과 시 알림
   - **Performance**: 성능 저하 감지 시 알림

### 6. CI/CD 자동화 (프로덕션)

GitHub Actions를 통해 자동으로 Sentry Release와 Source Map이 업로드됩니다:

**필요한 GitHub Secrets 설정:**
```
SENTRY_AUTH_TOKEN=your_sentry_auth_token
SENTRY_ORG=ddalkkak-date
SENTRY_PROJECT=ddalkkak-frontend
NEXT_PUBLIC_SENTRY_DSN=your_frontend_sentry_dsn
```

**자동화 기능:**
- ✅ 빌드 시 Source Map 자동 생성
- ✅ Sentry Release 생성 (commit SHA 기반)
- ✅ Source Map 업로드 및 자동 삭제 (보안)
- ✅ Release Finalization (배포 완료 후)
- ✅ 배포 요약에 Sentry 대시보드 링크 포함

**주요 개선사항:**
- `instrumentation.ts`: Next.js 14 권장 방식으로 Sentry 초기화
- Source Map 자동 삭제: 빌드 후 Source Map 파일을 자동으로 삭제하여 보안 강화
- GitHub Actions 통합: 배포 시 자동으로 Sentry Release 생성 및 Source Map 업로드

## 개발 가이드

### 프론트엔드

자세한 내용은 [`frontend/CLAUDE.md`](./frontend/CLAUDE.md)를 참조하세요.

```bash
cd frontend

# 개발 서버 실행
npm run dev

# 빌드
npm run build

# 린트
npm run lint

# 포맷
npm run format
```

### 백엔드

자세한 내용은 [`backend/README.md`](./backend/README.md)를 참조하세요.

```bash
cd backend

# 빌드
./gradlew clean build

# 테스트
./gradlew test

# 실행
./gradlew bootRun
```

## Docker Compose 명령어

```bash
# 서비스 시작 (백그라운드)
docker-compose up -d

# pgAdmin 포함 시작
docker-compose --profile tools up -d

# 서비스 중지
docker-compose down

# 서비스 중지 + 볼륨 삭제 (데이터 초기화)
docker-compose down -v

# 로그 확인
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f postgres

# 컨테이너 상태 확인
docker-compose ps

# 서비스 재시작
docker-compose restart

# 특정 서비스만 재시작
docker-compose restart postgres
```

## 데이터베이스 관리

### psql로 직접 접속

```bash
# Docker 컨테이너 내부에서 psql 실행
docker-compose exec postgres psql -U postgres -d ddalkkak

# 또는 로컬에서 직접 접속 (psql이 설치되어 있다면)
psql -h localhost -p 5432 -U postgres -d ddalkkak
```

### pgAdmin 사용 (GUI)

1. http://localhost:5050 접속
2. 로그인 (admin@ddalkkak.com / admin)
3. 서버 추가:
   - Host: `postgres` (Docker 네트워크 내부 이름)
   - Port: `5432`
   - Database: `ddalkkak`
   - Username: `postgres`
   - Password: `.env` 파일의 비밀번호

## 문제 해결

### PostgreSQL 연결 실패

```bash
# 컨테이너 상태 확인
docker-compose ps

# PostgreSQL 로그 확인
docker-compose logs postgres

# 컨테이너 재시작
docker-compose restart postgres

# 완전히 재시작
docker-compose down
docker-compose up -d
```

### 포트 충돌

기본 포트가 이미 사용 중이라면 `.env` 파일에서 포트를 변경하세요:

```env
POSTGRES_PORT=5433
PGADMIN_PORT=5051
```

### 데이터 초기화

```bash
# 모든 볼륨을 삭제하고 재시작
docker-compose down -v
docker-compose up -d
```

## Git 브랜치 전략

- `main`: 프로덕션 배포 브랜치
- `feature/SCRUM-XX-description`: 기능 개발 브랜치
- `bugfix/SCRUM-XX-description`: 버그 수정 브랜치
- `hotfix/SCRUM-XX-description`: 긴급 수정 브랜치

## 커밋 메시지 컨벤션

```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 변경
style: 코드 포맷팅
refactor: 코드 리팩토링
test: 테스트 추가/수정
chore: 빌드 설정, 패키지 매니저 설정
```

## 프로젝트 문서

- **PRD**: [`prd.md`](./prd.md) - 전체 제품 요구사항 및 기술 스펙
- **Frontend Guide**: [`frontend/CLAUDE.md`](./frontend/CLAUDE.md) - Next.js 프로젝트 상세 가이드
- **Backend Guide**: [`backend/README.md`](./backend/README.md) - Spring Boot 프로젝트 상세 가이드
- **API Documentation**: Swagger UI (실행 후 접속)

## JIRA 및 이슈 추적

- JIRA Board: https://ddalkkak-date.atlassian.net
- 티켓 작업 프로세스: [`CLAUDE.md`](./CLAUDE.md) 참조

## 라이선스

딸깍데이트 팀 © 2025

## 팀

- 개발: 박현준
- 프로젝트 관리: 박현준

## 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 등록해주세요.
