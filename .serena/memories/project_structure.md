# Project Structure

## Root Directory Layout
```
ddalkkak-date/
├── frontend/          # Next.js 14 웹 애플리케이션
├── backend/           # Spring Boot API 서버
├── terraform/         # Infrastructure as Code (AWS)
├── docs/              # Additional documentation
├── .github/           # GitHub Actions CI/CD workflows
├── docker-compose.yml # Local development environment
├── .env.example       # Environment variables template
├── prd.md             # Product Requirements Document
├── README.md          # Project overview
└── CLAUDE.md          # Claude Code instructions
```

## Frontend Structure (Next.js 14)
```
frontend/
├── src/
│   ├── app/           # Next.js App Router pages
│   │   ├── layout.tsx
│   │   ├── page.tsx
│   │   └── ...
│   ├── components/    # React components
│   │   ├── common/    # Reusable components (Button, Card, etc.)
│   │   └── features/  # Feature-specific components
│   ├── lib/           # Utility functions & API clients
│   │   ├── api/       # API client functions
│   │   ├── utils/     # Helper functions
│   │   └── constants/ # Constants
│   └── types/         # TypeScript type definitions
├── public/            # Static assets (images, fonts, etc.)
├── package.json       # Dependencies & scripts
├── tsconfig.json      # TypeScript configuration
├── tailwind.config.ts # Tailwind CSS configuration
├── next.config.mjs    # Next.js configuration
├── .eslintrc.json     # ESLint rules
├── .prettierrc.json   # Prettier rules
├── .env.example       # Environment variables template
└── CLAUDE.md          # Frontend-specific instructions
```

### Key Frontend Files
- **`src/app/page.tsx`**: Main landing page
- **`src/components/common/`**: Reusable UI components
- **`src/lib/api/`**: API integration functions
- **`src/types/models.ts`**: Core TypeScript types
- **`package.json`**: Scripts: `dev`, `build`, `lint`, `format`, `test`

## Backend Structure (Spring Boot 3)
```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/ddalkkak/
│   │   │   ├── config/        # Configuration classes
│   │   │   │   ├── OpenApiConfig.java       # Swagger configuration
│   │   │   │   ├── ClaudeApiConfig.java     # Claude API setup
│   │   │   │   ├── KakaoApiConfig.java      # Kakao API setup
│   │   │   │   ├── RedisConfig.java         # Redis caching
│   │   │   │   └── LangfuseConfig.java      # LLM observability
│   │   │   ├── controller/    # REST API controllers
│   │   │   │   ├── HealthCheckController.java
│   │   │   │   ├── CourseGenerationController.java
│   │   │   │   └── PlaceCollectionController.java
│   │   │   ├── service/       # Business logic
│   │   │   │   ├── ClaudeApiService.java
│   │   │   │   ├── CourseGenerationService.java
│   │   │   │   ├── CourseCacheService.java
│   │   │   │   ├── KakaoLocalService.java
│   │   │   │   ├── PlaceCurationService.java
│   │   │   │   ├── PlaceCollectionBatchService.java
│   │   │   │   └── LangfuseTraceService.java
│   │   │   ├── repository/    # Data access layer
│   │   │   │   └── PlaceRepository.java
│   │   │   ├── domain/        # JPA entities
│   │   │   │   └── Place.java
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   │   ├── CourseGenerationRequest.java
│   │   │   │   ├── CourseGenerationResponse.java
│   │   │   │   ├── HealthCheckResponse.java
│   │   │   │   ├── KakaoLocalSearchResponse.java
│   │   │   │   └── PlaceCurationResult.java
│   │   │   └── DdalkkakApplication.java  # Main application
│   │   └── resources/
│   │       ├── application.yml          # Main configuration
│   │       ├── application-local.yml    # Local profile
│   │       └── db/migration/            # Flyway migrations
│   └── test/          # Test classes
├── build.gradle       # Gradle build configuration
├── .env.example       # Environment variables template
└── README.md          # Backend-specific documentation
```

### Key Backend Files
- **`DdalkkakApplication.java`**: Spring Boot entry point
- **`HealthCheckController.java`**: Health check endpoint
- **`CourseGenerationController.java`**: Main course generation API
- **`ClaudeApiService.java`**: Claude API integration
- **`PlaceRepository.java`**: Database access for places
- **`application.yml`**: Spring Boot configuration

## Infrastructure Structure (Terraform)
```
terraform/
├── environments/
│   ├── dev/         # Development environment
│   ├── staging/     # Staging environment
│   └── prod/        # Production environment
└── modules/
    ├── vpc/         # VPC, Subnets, Security Groups
    ├── ecs/         # ECS Fargate cluster & services
    ├── rds/         # PostgreSQL RDS instance
    ├── elasticache/ # Redis cache
    ├── alb/         # Application Load Balancer
    └── secrets/     # AWS Secrets Manager
```

## Configuration Files

### Environment Variables
- **Root**: `.env.example` (PostgreSQL, pgAdmin)
- **Frontend**: `frontend/.env.example` (API URLs, Kakao keys)
- **Backend**: `backend/.env.example` (Database, Claude API, Kakao API)

### Build & Deploy
- **Frontend**: `vercel.json` (Vercel deployment config)
- **Backend**: `Dockerfile` (Container image build)
- **CI/CD**: `.github/workflows/*.yml` (GitHub Actions)

## Documentation Files
- **`README.md`**: Project overview and quick start
- **`CLAUDE.md`**: Claude Code assistant instructions
- **`prd.md`**: Product Requirements Document (detailed specs)
- **`frontend/CLAUDE.md`**: Frontend-specific guidelines
- **`backend/README.md`**: Backend-specific documentation
- **`backend/API_DOCUMENTATION.md`**: API endpoint specifications
