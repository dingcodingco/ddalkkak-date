# Tech Stack

## Frontend
- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript 5
- **Styling**: Tailwind CSS 3.4
- **State Management**: Zustand 5.0
- **HTTP Client**: Axios
- **Maps/Visualization**: D3.js 7.9, React SVG
- **Testing**: Jest 30 + React Testing Library
- **Deployment**: Vercel

## Backend
- **Framework**: Spring Boot 3.2.10
- **Language**: Java 17
- **Build Tool**: Gradle 8.10
- **ORM**: Spring Data JPA (Hibernate)
- **Database**: PostgreSQL 15
- **Cache**: Redis 7 (ElastiCache)
- **AI**: Anthropic Claude Sonnet 4.5 API
- **Circuit Breaker**: Resilience4j
- **HTTP Client**: WebFlux (reactive)
- **API Docs**: Springdoc OpenAPI 3 (Swagger)
- **DB Migration**: Flyway
- **Testing**: JUnit 5, Spring Boot Test

## Infrastructure
- **Cloud Provider**: AWS
- **Container Runtime**: ECS Fargate
- **Container Registry**: ECR
- **Load Balancer**: ALB
- **Database**: RDS PostgreSQL (db.t3.medium)
- **Cache**: ElastiCache Redis (cache.t3.micro)
- **CDN**: CloudFront
- **Secrets**: AWS Secrets Manager
- **IaC**: Terraform
- **CI/CD**: 
  - Frontend: Vercel (automatic)
  - Backend: GitHub Actions → Docker → ECR → ECS

## Monitoring
- **Infrastructure**: AWS CloudWatch (metrics, logs, alarms)
- **APM & Errors**: Sentry (backend + frontend)
- **LLM Observability**: Langfuse (Claude API tracking, cost analysis)
- **Health Check**: Spring Boot Actuator

## External APIs
- **Places Data**: Kakao Local API (300K req/day)
- **Review Data**: Naver Blog Search API (25K req/day)
- **Authentication**: Kakao OAuth 2.0
- **AI**: Claude Sonnet 4.5 API
