# Code Style & Conventions

## Frontend (Next.js + TypeScript)

### General Principles
- **Strict TypeScript**: Avoid `any` types (warn only, not error)
- **ESLint + Prettier**: Integrated, auto-format on save
- **Import Aliases**: Use `@/*` for all imports from `src/`
- **Component Naming**: PascalCase, named exports (not default)

### File Organization
```
src/
├── app/           # Next.js App Router pages
├── components/    # React components (by feature or common)
├── lib/           # Utility functions, API clients, constants
└── types/         # TypeScript type definitions
```

### Code Style Examples
```typescript
// ✅ Good: Named import with alias
import { Button } from '@/components/common/Button';
import { fetchCourses } from '@/lib/api/courses';
import type { Course } from '@/types/models';

// ❌ Bad: Relative imports
import { Button } from '../../components/common/Button';

// ✅ Good: Typed props with JSDoc
interface ButtonProps {
  /** Button text content */
  children: React.ReactNode;
  /** Click handler */
  onClick: () => void;
}

// ✅ Good: Named export
export function Button({ children, onClick }: ButtonProps) {
  return <button onClick={onClick}>{children}</button>;
}
```

### Linting Rules
- Unused variables: **warn** (not error)
- Missing dependencies in useEffect: **warn**
- TypeScript `any`: **warn**
- Prettier format errors: **error** (fails build)

## Backend (Spring Boot + Java)

### General Principles
- **Java 17 Features**: Use modern Java syntax (records, switch expressions, var)
- **Lombok**: Minimize boilerplate (@Data, @Builder, @AllArgsConstructor)
- **RESTful APIs**: Follow REST conventions
- **DTO Pattern**: Never expose entities directly in controllers

### Package Structure
```
com.ddalkkak/
├── config/        # Configuration classes (Swagger, Redis, etc.)
├── controller/    # REST API controllers
├── service/       # Business logic layer
├── repository/    # Data access layer (JPA repositories)
├── domain/        # JPA entities
└── dto/           # Data Transfer Objects
```

### Naming Conventions
- **Controllers**: `*Controller` (e.g., `CourseGenerationController`)
- **Services**: `*Service` (e.g., `ClaudeApiService`)
- **Repositories**: `*Repository` (e.g., `PlaceRepository`)
- **DTOs**: `*Request`, `*Response` (e.g., `CourseGenerationRequest`)
- **Entities**: Singular noun (e.g., `Place`, `User`)

### Code Style Examples
```java
// ✅ Good: Lombok + Builder pattern
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseGenerationRequest {
    private String region;
    private String dateType;
    private Integer budget;
}

// ✅ Good: Service with dependency injection
@Service
@RequiredArgsConstructor
public class CourseGenerationService {
    private final ClaudeApiService claudeApiService;
    private final PlaceRepository placeRepository;
    
    public CourseGenerationResponse generateCourses(CourseGenerationRequest request) {
        // Business logic here
    }
}

// ✅ Good: Controller with proper REST annotations
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseGenerationController {
    private final CourseGenerationService courseGenerationService;
    
    @PostMapping("/generate")
    public ResponseEntity<CourseGenerationResponse> generateCourses(
            @Valid @RequestBody CourseGenerationRequest request) {
        return ResponseEntity.ok(courseGenerationService.generateCourses(request));
    }
}
```

## Git Commit Messages
Follow Conventional Commits:
```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 변경
style: 코드 포맷팅 (기능 변경 없음)
refactor: 코드 리팩토링
test: 테스트 추가/수정
chore: 빌드 설정, 패키지 매니저 설정
```

Example:
```
feat(frontend): 지역 선택 인터랙티브 맵 UI 구현

- SVG 기반 서울 지도 컴포넌트 추가
- 지역 hover/click 인터랙션 구현
- API 연동 및 데이터 바인딩
- 반응형 디자인 적용

JIRA: SCRUM-42
```

## Testing Standards

### Frontend
- Unit tests with Jest + React Testing Library
- Component tests for UI logic
- Integration tests for API calls
- Target: >80% coverage for critical paths

### Backend
- Unit tests with JUnit 5
- Integration tests with Spring Boot Test
- Mock external APIs (Claude, Kakao, Naver)
- Target: >70% coverage for services
