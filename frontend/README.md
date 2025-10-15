# 딸깍데이트 프론트엔드

딸깍데이트(Ddalkkak Date) 프로젝트의 Next.js 14 기반 프론트엔드 애플리케이션입니다.

## 기술 스택

- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript 5.x
- **Styling**: Tailwind CSS 3.x
- **Code Quality**: ESLint + Prettier
- **Runtime**: React 18

## 프로젝트 구조

```
frontend/
├── src/
│   ├── app/              # App Router (페이지 및 라우팅)
│   ├── components/       # 재사용 가능한 컴포넌트
│   ├── lib/              # 유틸리티 함수 및 헬퍼
│   └── types/            # TypeScript 타입 정의
├── public/               # 정적 파일
├── .env.example          # 환경 변수 예시
└── package.json
```

## 로컬 개발 환경 설정

### 1. 의존성 설치

```bash
npm install
```

### 2. 환경 변수 설정

`.env.example` 파일을 복사하여 `.env.local` 파일을 생성하고, 필요한 API 키를 설정하세요:

```bash
cp .env.example .env.local
```

`.env.local` 파일 내용:

```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api/v1
NEXT_PUBLIC_KAKAO_MAP_API_KEY=your_kakao_map_api_key_here
NEXT_PUBLIC_KAKAO_LOGIN_KEY=your_kakao_login_key_here
```

### 3. 개발 서버 실행

```bash
npm run dev
```

브라우저에서 [http://localhost:3000](http://localhost:3000)을 열어 애플리케이션을 확인하세요.

## 사용 가능한 스크립트

```bash
# 개발 서버 실행
npm run dev

# 프로덕션 빌드
npm run build

# 프로덕션 서버 실행
npm start

# 린트 검사
npm run lint

# 코드 포맷팅 (Prettier)
npm run format
```

## 코드 스타일

이 프로젝트는 ESLint와 Prettier를 사용하여 일관된 코드 스타일을 유지합니다.

- ESLint: 코드 품질 및 잠재적 오류 검사
- Prettier: 코드 포맷팅 자동화

## 개발 가이드

### 컴포넌트 작성

재사용 가능한 컴포넌트는 `src/components/` 디렉토리에 작성합니다.

### API 호출

API 호출 관련 함수는 `src/lib/api/` 디렉토리에 작성합니다.

### 타입 정의

공통으로 사용되는 TypeScript 타입은 `src/types/` 디렉토리에 정의합니다.

## 참고 자료

- [Next.js Documentation](https://nextjs.org/docs)
- [React Documentation](https://react.dev)
- [Tailwind CSS Documentation](https://tailwindcss.com/docs)
- [TypeScript Documentation](https://www.typescriptlang.org/docs)
