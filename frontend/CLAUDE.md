# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**딸깍데이트 (Ddalkkak Date)** - AI-powered date course recommendation service frontend built with Next.js 14, TypeScript, and Tailwind CSS. This is the web-first MVP (mobile-responsive) targeting Korean Gen Z couples.

**Key Value Proposition**: Reduce date planning time from 3.5 hours to 60 seconds using Claude AI recommendations.

## Development Commands

### Local Development
```bash
# Install dependencies
npm install

# Run development server (http://localhost:3000)
npm run dev

# Build for production
npm run build

# Start production server
npm start

# Code quality
npm run lint          # Run ESLint
npm run format        # Run Prettier formatting
```

### Environment Setup
1. Copy `.env.example` to `.env.local`
2. Set required environment variables:
   - `NEXT_PUBLIC_API_BASE_URL`: Backend API endpoint (default: http://localhost:8080/api/v1)
   - `NEXT_PUBLIC_KAKAO_MAP_API_KEY`: Kakao Map API key
   - `NEXT_PUBLIC_KAKAO_LOGIN_KEY`: Kakao Login API key

## Architecture & Key Patterns

### Directory Structure Philosophy
- **`src/app/`**: Next.js 14 App Router with file-based routing
- **`src/components/`**: Reusable React components (organize by feature or common)
- **`src/lib/`**: Utility functions, API clients, constants
- **`src/types/`**: TypeScript type definitions

### Import Path Aliases
Use `@/*` alias for all imports from `src/`:
```typescript
import { Button } from '@/components/common/Button';
import { fetchCourses } from '@/lib/api/courses';
import type { Course } from '@/types/models';
```

### Code Style Requirements
- **ESLint + Prettier**: Integrated; formatting errors fail builds
- **TypeScript**: Strict mode enabled; avoid `any` types (warn only)
- **Unused Variables**: Warnings, not errors (see `.eslintrc.json`)

### Authentication Strategy
This app implements **optional login** - users can access core features without authentication:
- **No login required**: Course generation, course viewing, sharing, regeneration
- **Login required**: Save courses, view saved courses, post-date feedback
- **Login provider**: Kakao Talk only (single OAuth provider)

When implementing features, consider whether the feature should:
1. Work for guest users (prefer this)
2. Show login prompt on action (e.g., "Save" button)
3. Require authentication upfront (rare)

### Regional Map UI Pattern
The app features an **interactive visual map** for region selection (not dropdown):
- SVG-based illustration map of Seoul districts
- Regions styled as blocks with emojis and hover effects
- Data fetched from `GET /api/v1/regions/metadata`
- D3.js or React SVG for interactivity
- Mobile: touch gestures; Desktop: mouse hover/click
- Quick select buttons below map for accessibility

Expected API response structure:
```typescript
{
  regions: Array<{
    id: string;              // "hongdae", "gangnam"
    display_name: string;    // "홍대", "강남"
    emoji: string;           // "🎨", "🏙️"
    coordinates: { latitude: number; longitude: number };
    map_position: { x: number; y: number };
    svg_path: string;        // SVG Path data for region shape
    popularity_score: number;
    available_places_count: number;
    keywords: string[];
    tier: 1 | 2 | 3;        // 1=필수, 2=중요, 3=선택
    is_active: boolean;
  }>
}
```

### State Management
- **Local state**: React `useState` for component-specific state
- **Global state**: Zustand (to be configured) for app-wide state
- **Server state**: Consider React Query/SWR for API data caching

### API Integration Pattern
1. Create API client functions in `src/lib/api/`
2. Use axios for HTTP requests
3. Handle errors consistently with try/catch
4. Type all API responses with TypeScript interfaces in `src/types/`

Example:
```typescript
// src/lib/api/courses.ts
import axios from 'axios';
import type { Course } from '@/types/models';

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL;

export async function generateCourses(params: {
  region: string;
  date_type: string;
  budget: number;
}): Promise<{ courses: Course[] }> {
  const response = await axios.post(`${API_BASE}/courses/generate`, params);
  return response.data;
}
```

### Performance Considerations
- **Images**: Use Next.js `<Image>` component with lazy loading
- **Code splitting**: Leverage Next.js automatic code splitting
- **Bundle size**: Keep initial bundle < 500KB
- **Target metrics**: LCP < 2.5s, FID < 100ms, CLS < 0.1

## Backend Integration

### API Endpoints
Backend runs on Spring Boot at `http://localhost:8080/api/v1`

Key endpoints:
- `POST /courses/generate` - Generate AI course recommendations (60s timeout)
- `GET /regions/metadata` - Get region selection map data
- `POST /courses/save` - Save course (requires auth)
- `GET /courses/saved` - Get saved courses (requires auth)
- `POST /auth/kakao` - Kakao OAuth login
- `POST /feedback` - Submit post-date feedback (requires auth)

### Response Time Expectations
- Course generation: P95 < 8 seconds (Claude AI call)
- Standard APIs: P95 < 500ms
- Cache hit: < 500ms (Redis cached results)

### Error Handling
Backend may return fallback rule-based recommendations if Claude API times out. Handle gracefully in UI with appropriate messaging.

## UI/UX Guidelines

### Design System
- **Primary Color**: #FF6B6B (Coral Pink)
- **Secondary Color**: #4ECDC4 (Mint)
- **Background**: #FFFBF0 (Ivory)
- **Text**: #2D3436 (Dark Gray)
- **Component Library**: Tailwind CSS + shadcn/ui

### Mobile-First Responsive Design
- Design mobile layouts first, then scale up
- Breakpoints: mobile (default), tablet (768px), desktop (1024px)
- Touch-friendly interactive elements (min 44×44px)

### Accessibility Requirements
- WCAG 2.1 AA compliance minimum
- Semantic HTML elements
- Keyboard navigation support
- Screen reader compatible

## Monitoring & Error Tracking

### Frontend Monitoring
- **Sentry**: Error tracking and Web Vitals monitoring
- **Next.js Analytics**: Core Web Vitals (LCP, FID, CLS)
- Release tracking integrated with CI/CD

### What to Log
- API errors with request context
- User actions for analytics
- Performance bottlenecks

### What NOT to Log
- User personal information
- Authentication tokens
- Sensitive data

## Deployment

### Production Deployment
- **Platform**: Vercel (automatic deployment from `main` branch)
- **Environment**: Node.js 20+, Next.js 14
- **CDN**: CloudFront for static assets
- **SSL**: Automatic HTTPS via Vercel

### Build Process
1. GitHub push to `main` triggers Vercel deployment
2. Vercel runs `npm run build`
3. ESLint/TypeScript errors fail the build
4. Successful build deploys to https://date-click.com
5. Sentry release tracking automatically updates

## Project Context

### Target Users
- **Primary**: Korean Gen Z couples (20-29 years old)
- **Use Case**: Quick date planning with minimal research time
- **Platform**: Web-first (mobile responsive), native apps in Phase 2

### Success Metrics (MVP)
- Time-to-First-Course: < 30 seconds
- D7 Retention: 40%+
- Course Save Rate: 40%+
- Guest Conversion Rate: 30%+ (guest → login)

### Tech Stack Rationale
- **Next.js 14**: App Router for modern React patterns, SSR/SSG, optimal SEO
- **TypeScript**: Type safety for large-scale app
- **Tailwind CSS**: Rapid UI development with consistent design
- **Vercel**: Zero-config deployment optimized for Next.js

## Common Development Patterns

### Adding New API Integration
1. Define TypeScript types in `src/types/`
2. Create API function in `src/lib/api/`
3. Use in components with proper error handling
4. Add loading states and error messages

### Creating Reusable Components
1. Place in `src/components/common/` or `src/components/features/`
2. Use TypeScript for props
3. Export named components (not default)
4. Document props with JSDoc comments

### Handling Authentication
1. Check if feature requires login
2. If yes, show login modal on action (not upfront)
3. Use Kakao OAuth flow
4. Store JWT tokens securely (HTTP-only cookies)

## Known Constraints

### MVP Scope Limitations
- Seoul only (no other cities in Phase 1)
- Korean language only
- Kakao login only (no Naver/Google)
- Web only (no native apps yet)
- No real-time features (chat, live updates)

### Technical Limitations
- Claude API may timeout (30s) → fallback to rule-based recommendations
- Kakao API rate limit: 300,000 requests/day
- Guest users tracked via localStorage (session-based, no persistence)

## Important Files

- `.eslintrc.json` - ESLint + Prettier rules (enforce on save)
- `.prettierrc.json` - Code formatting rules
- `tsconfig.json` - TypeScript compiler config with path aliases
- `next.config.mjs` - Next.js configuration
- `tailwind.config.ts` - Tailwind CSS theme customization
- `.env.example` - Required environment variables template

## Related Documentation

- **Backend API**: See PRD at `/Users/hyunjoon/project/ai-native-developer-dingco/ddalkkak-date/prd.md`
- **Design System**: Figma link in PRD (color palette, component specs)
- **Architecture**: System diagram in PRD (ECS, RDS, Redis, Claude API)

## Development Tips

1. **Always run `npm run lint` before committing** - CI will fail on lint errors
2. **Use TypeScript strictly** - Avoid `any`, prefer proper typing
3. **Test on mobile first** - Most users will be on mobile web
4. **Consider guest users** - Don't force login unless necessary
5. **Handle loading states** - Course generation takes up to 60 seconds
6. **Optimize images** - Use Next.js Image component with WebP format
7. **Check accessibility** - Use semantic HTML and ARIA labels
8. **Monitor bundle size** - Keep < 500KB initial bundle
