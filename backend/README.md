# 딸깍데이트 백엔드

딸깍데이트 서비스의 Spring Boot 기반 백엔드 API 서버입니다.

## 기술 스택

- **Framework**: Spring Boot 3.2.10
- **Language**: Java 17
- **Build Tool**: Gradle 8.10
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA (Hibernate)
- **Documentation**: Springdoc OpenAPI 3 (Swagger)

## 프로젝트 구조

```
backend/
├── src/main/java/com/ddalkkak/
│   ├── config/           # 설정 클래스 (Swagger 등)
│   ├── controller/       # REST API 컨트롤러
│   ├── service/          # 비즈니스 로직
│   ├── repository/       # 데이터 액세스 레이어
│   ├── domain/           # JPA 엔티티
│   ├── dto/              # Data Transfer Objects
│   └── DdalkkakApplication.java
├── src/main/resources/
│   ├── application.yml        # 기본 설정
│   └── application-local.yml  # 로컬 개발 환경 설정
├── src/test/             # 테스트 코드
├── build.gradle          # Gradle 빌드 설정
└── .env.example          # 환경 변수 템플릿
```

## 시작하기

### 사전 요구사항

- Java 17 이상
- PostgreSQL 14 이상 (선택 사항 - Health Check API는 DB 없이 동작)

### 환경 변수 설정

`.env.example` 파일을 참고하여 환경 변수를 설정하세요:

```bash
cp .env.example .env

# .env 파일 수정
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ddalkkak
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password
CLAUDE_API_KEY=your_anthropic_api_key
```

### 애플리케이션 실행

```bash
# 빌드
./gradlew clean build

# 실행 (local 프로파일)
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun

# 또는 JAR 파일로 실행
java -jar build/libs/ddalkkak-backend-0.0.1-SNAPSHOT.jar
```

서버가 시작되면 http://localhost:8080 에서 접근 가능합니다.

## API 문서

애플리케이션 실행 후 Swagger UI를 통해 API 문서를 확인할 수 있습니다:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 주요 엔드포인트

### Health Check

서버 상태를 확인합니다.

```bash
GET /api/v1/health
```

**응답 예시:**
```json
{
  "status": "UP",
  "message": "딸깍데이트 백엔드 서버가 정상적으로 실행 중입니다.",
  "timestamp": "2025-10-14T22:31:46.299213",
  "version": "0.0.1-SNAPSHOT"
}
```

## 개발 가이드

### 코드 스타일

- Java 17 features 사용
- Lombok 어노테이션 활용
- RESTful API 규칙 준수
- DTO 패턴 사용 (Entity 직접 반환 금지)

### 테스트 실행

```bash
./gradlew test
```

### 빌드 (테스트 제외)

```bash
./gradlew clean build -x test
```

## 배포

배포 관련 설정은 추후 추가될 예정입니다.

## 라이선스

딸깍데이트 팀 © 2025
