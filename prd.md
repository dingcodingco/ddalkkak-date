# 데이트 딸깍 MVP - Product Requirements Document (PRD)

**문서 버전:** v1.6
**작성일:** 2025년 10월 13일
**작성자:** Product Team
**검토자:** Engineering Lead, Design Lead
**승인 필요:** CEO, CTO

---

## 📋 Executive Summary (개요)

### 제품 개요
**데이트 딸깍**은 Z세대 연인들이 겪는 "데이트 계획의 고통"을 해결하는 AI 기반 데이트 코스 추천 서비스입니다. 사용자가 `지역`, `데이트 유형`, `예산` 3가지만 입력하면, Claude AI가 최적화된 데이트 코스 3가지를 60초 이내에 추천합니다.

**MVP 플랫폼:** 웹 우선 (모바일 웹 반응형)

### 핵심 가치 제안 (Value Proposition)
```
Before: 데이트 준비 3시간 + 불안감 + 예산 초과
After: 60초 완성 + 확신 + 예산 내 보장

- 리서치 시간: 3.5시간 → 60초 (210배 단축)
- 의사결정 스트레스: 高 → 低
- 예산 관리: 불확실 → 자동 계산
```

### MVP 목표
- **런칭 시점:** 2025년 11월 15일 (D-33일)
- **플랫폼:** 웹 (모바일 웹 반응형), 네이티브 앱은 Phase 2
- **타겟 MAU:** 10,000명 (첫 3개월)
- **PMF 검증 기준:** D7 Retention 40%+, NPS 40+

---

## 🎯 Problem Statement (해결하려는 문제)

### Problem 1: 리서치 시간 폭탄
```
현상: 데이트 계획에 평균 3.5시간 소요
원인: 블로그/인스타 무한 스크롤 → 정보 과부하 → 선택 마비
영향: 다음날 업무 집중력 30% 하락, 관계 스트레스 증가
```

### Problem 2: 예산 관리 실패
```
현상: 예산 5만원 생각 → 실제 지출 10만원
원인: 각 장소 가격 합산 안 함, 숨은 비용 간과
영향: 월말 카드값 폭탄, 데이트 빈도 감소
```

### Problem 3: 동선 비효율
```
현상: 강남 식당 → 홍대 카페 → 왕복 2시간
원인: 각 장소를 개별적으로 선택, 통합 계획 부재
영향: 이동 시간에 데이트 시간 소진, 피로도 증가
```

**→ 해결책: 60초 만에 예산 내에서 동선 최적화된 코스 자동 생성**

---

## 📊 Goals & Success Metrics (목표와 성공 지표)

### North Star Metric
**"실제로 추천 코스로 데이트 다녀온 사용자 수"**
- 목표: 월 7,000명 (MAU 10,000명 × 70% 실행률)

### HEART Framework Metrics

| Category | Metric | Target | 측정 방법 |
|----------|--------|--------|----------|
| **Happiness** | NPS | 40+ | 데이트 후 48시간 내 설문 (로그인 사용자) |
| | Post-Date Satisfaction | 4.2/5.0 | 5점 척도 만족도 |
| **Engagement** | DAU/MAU | 25%+ | 일간/월간 활성 사용자 비율 (비로그인 포함) |
| | Course Save Rate | 40%+ | 저장 수 / 조회 수 (로그인 전환율) |
| | Guest Conversion Rate | 30%+ | 비로그인 → 로그인 전환율 |
| **Adoption** | Time-to-First-Course | <30초 | 웹 접속 → 첫 코스 생성 (비로그인) |
| | Feature Discovery (D7) | 60%+ | 7일 내 2개 이상 기능 사용 |
| | Login Conversion (D7) | 40%+ | 7일 내 최소 1회 로그인 |
| **Retention** | D7 Retention | 40%+ | 첫 방문 후 7일차 재방문율 (비로그인 포함) |
| | D30 Retention | 25%+ | 첫 방문 후 30일차 재방문율 |
| **Task Success** | Course Generation Success | 95%+ | 코스 생성 요청 → 성공 (비로그인 포함) |
| | Share Rate | 50%+ | 코스 조회 → 공유 (비로그인 포함) |
| | Recommendation-to-Execution | 70%+ | 저장 → 실제 방문 (로그인 사용자) |

---

## 👥 User Personas (타겟 사용자)

### Persona 1: "완벽주의 계획러" 김민준
```
나이: 27세
직업: IT 스타트업 PM
연봉: 5,500만원
연애: 6개월차

Pain Points:
- 데이트 계획에 매주 3.5시간 소요
- "이게 진짜 좋은 곳일까?" 불안감
- 동선/예산 통합 계획의 어려움

Value Delivered:
- 시간 210배 단축 (3.5시간 → 60초)
- AI 기반 확신 제공
- 예산/동선 자동 최적화
```

### Persona 2: "오늘뭐하지형" 박서연
```
나이: 24세
직업: 패션 브랜드 마케터
연봉: 3,800만원
연애: 소개팅 활발, 3개월 내 짧은 연애

Pain Points:
- "오늘 뭐해?" 메시지 공포
- 결정 장애 + FOMO
- 예산 관리 실패 (예상 5만원 → 실제 10만원)

Value Delivered:
- 5초 의사결정 (즉흥성 유지)
- "AI가 골라줬으니 후회 없어"
- 예산 내 보장 (입력값 절대 초과 안 함)
```

---

## 📱 User Stories & Use Cases

### Epic 1: 코스 추천 받기 (Core Flow)

#### User Story 1.1: 빠른 코스 생성
```
As a 사용자
I want to 지역/유형/예산만 입력하면 코스가 자동 생성되길
So that 고민 없이 빠르게 데이트 계획을 세울 수 있다

Acceptance Criteria:
✅ 입력 필드 3개: 지역(지도 기반 비주얼 선택), 유형(카드 선택), 예산(슬라이더)
✅ 입력 완료 후 60초 이내 추천 완료
✅ 추천 코스 3개 제시 (각각 3~4곳 장소 포함)
✅ 총 예산이 입력값의 95~100% 범위 내
```

**Use Case (비로그인 사용자 메인 플로우):**
```
1. [사용자] 웹사이트 접속 (date-click.com)
2. [시스템] 랜딩 페이지 표시 (로그인 없이 바로 "코스 만들기" 버튼)
3. [사용자] "코스 만들기" 버튼 클릭 (비로그인 상태)
4. [사용자] 지역 선택: 서울 지도 기반 UI에서 "홍대" 영역 클릭 (비주얼 맵 선택)
   - 서울 주요 상권이 일러스트 지도 형태로 표시
   - 각 지역 영역을 클릭하면 선택 (예: 홍대, 강남, 성수, 연남, 이태원 등)
   - 모바일: 터치, 데스크톱: 마우스 클릭/호버
5. [사용자] 유형 선택: "문화데이트" (카드 선택)
   - "감성데이트" 선택 시 → "만 19세 이상 확인 필요" 팝업
6. [사용자] 예산 입력: 100,000원 (슬라이더)
7. [사용자] "추천 받기" 버튼 클릭
8. [시스템] 로딩 인디케이터 표시 ("코스 생성 중... 20초")
9. [시스템] 3개 코스 카드 형태로 표시
10. [사용자] 마음에 드는 코스 선택 → 상세 보기
11. [사용자] "저장" 버튼 클릭 → [시스템] 로그인 팝업 표시
    - "카카오톡으로 3초 만에 저장하기"
    - "나중에" 버튼으로 팝업 닫기 가능
12. [사용자] "공유" 버튼 클릭 → 카카오톡/링크 복사 (로그인 불필요)

주요 특징:
✅ 온보딩 없음 (즉시 메인 기능 사용)
✅ 로그인 강제 없음 (개인화 기능 사용 시에만)
✅ 비로그인 사용자도 코스 생성/조회/공유 가능
```

#### User Story 1.2: 코스 상세 정보 확인
```
As a 사용자
I want to 추천받은 코스의 상세 정보를 보고 싶다
So that 실제로 갈지 판단할 수 있다

Acceptance Criteria:
✅ 각 장소별 정보: 이름, 주소, 예상 비용, 예상 소요 시간
✅ 전체 동선 지도 표시 (순서대로 연결)
✅ 총 예산 & 총 소요 시간 표시
✅ "네이버 지도 열기" 버튼
✅ 각 장소별 "더보기" → 외부 링크 (네이버 플레이스)
```

#### User Story 1.3: 코스 저장 & 공유
```
As a 사용자
I want to 마음에 드는 코스를 저장하고 공유하고 싶다
So that 나중에 다시 보거나 친구/연인에게 보낼 수 있다

Acceptance Criteria:
✅ "저장" 버튼 → 로그인 팝업 표시 (비로그인 시)
✅ 로그인 후 → 내 코스 보관함에 저장
✅ "공유" 버튼 → 카카오톡/링크 복사 (로그인 불필요)
✅ 공유 링크: 코스 요약 페이지 + 메타태그(OG) 최적화

로그인 팝업 UX:
- 제목: "코스를 저장하시겠어요?"
- 설명: "카카오톡 로그인 후 언제든 다시 볼 수 있어요"
- 버튼 1: "카카오톡으로 저장하기" (Primary CTA)
- 버튼 2: "나중에" (Secondary, 팝업 닫기)
```

---

### Epic 2: 피드백 & 개선

#### User Story 2.1: 데이트 후 만족도 평가
```
As a 로그인 사용자
I want to 다녀온 데이트에 대해 피드백을 남기고 싶다
So that 더 나은 추천을 받을 수 있다

Acceptance Criteria:
✅ 저장 후 48시간 뒤 푸시 알림: "다녀오셨나요?" (로그인 사용자만)
✅ 간단한 평가: ⭐ 5점 척도
✅ 선택적 코멘트: "어떤 점이 좋았나요?" (텍스트)
✅ 각 장소별 개별 평가 가능

비로그인 사용자:
- 평가 기능 사용 불가 (저장된 코스가 없으므로)
- 저장 시 로그인 유도
```

#### User Story 2.2: 재추천 받기
```
As a 사용자 (비로그인도 가능)
I want to 마음에 안 들면 다른 코스를 다시 추천받고 싶다
So that 만족할 때까지 여러 옵션을 볼 수 있다

Acceptance Criteria:
✅ "다른 코스 보기" 버튼 (무제한, 로그인 불필요)
✅ 이전 코스와 중복 장소 최소화 (80% 이상 다른 장소)
✅ 재추천 속도: 10초 이내 (캐싱 활용)
✅ 비로그인 사용자도 무제한 재추천 가능

구현 방식:
- 세션 기반 중복 방지 (localStorage)
- 로그인 사용자: DB 히스토리 기반 중복 방지
- 비로그인 사용자: 현재 세션 내 중복만 방지
```

---

### Epic 3: 마이페이지 & 히스토리

#### User Story 3.1: 저장한 코스 관리
```
As a 로그인 사용자
I want to 저장한 코스 목록을 보고 싶다
So that 과거 계획을 다시 활용할 수 있다

Acceptance Criteria:
✅ "내 코스" 탭 클릭 시:
   - 로그인 사용자: 저장 목록 표시
   - 비로그인 사용자: 로그인 팝업 표시
✅ 최신순 정렬, 날짜별 필터
✅ 각 코스 카드: 썸네일, 제목, 예산, 날짜
✅ 스와이프 삭제 기능

비로그인 사용자 UX:
- "내 코스" 탭 클릭 → 로그인 팝업
- 제목: "저장한 코스를 확인하시겠어요?"
- 설명: "카카오톡 로그인 후 언제 어디서나 확인할 수 있어요"
- CTA: "카카오톡으로 시작하기"
```

---

## 🗄️ Data Sources & Integration (데이터 소스 및 연동)

### 장소 데이터 수집 전략

#### 1. Kakao Local API (1순위)
```
장점:
- 무료 사용 가능 (일 300,000건 제한)
- 공식 API로 법적 안정성 보장
- 위치 기반 검색, 카테고리 필터링 지원
- 최신 정보 자동 업데이트

사용 방법:
- Kakao Developers 계정 생성
- REST API 키 발급
- 키워드 검색 API:
  GET https://dapi.kakao.com/v2/local/search/keyword.json
  Query Params:
    - query: "홍대 카페" (지역 + 카테고리)
    - category_group_code: FD6 (음식점), CE7 (카페)
    - x, y: 중심 좌표 (위도/경도)
    - radius: 검색 반경 (미터)
    - size: 결과 개수 (최대 15)

응답 데이터:
{
  "documents": [
    {
      "place_name": "카페 이름",
      "address_name": "서울 마포구...",
      "road_address_name": "서울 마포구...",
      "category_name": "음식점 > 카페",
      "x": "126.924",  // 경도
      "y": "37.556",   // 위도
      "place_url": "http://place.map.kakao.com/...",
      "id": "12345678"  // 카카오 플레이스 ID
    }
  ]
}

수집 프로세스:
1. Tier 1 지역별 카테고리 조합 검색
   - 홍대 × [카페, 식당, 술집, 전시관, 액티비티]
2. 검색 결과 → places 테이블 저장
   - data_source = "kakao_api"
3. 매일 자정 배치 작업 (데이터 갱신)
4. Rate Limiting 준수 (300,000 req/day)
```

#### 2. 크롤링 (2순위, Fallback)
```
필요 조건:
- Kakao API로 부족한 데이터 보완
- robots.txt 준수 필수
- 법무팀 검토 완료 후 진행

기술 스택:
- Playwright (Headless Browser)
- Python (크롤링 스크립트)
- Celery (비동기 작업 큐)

크롤링 대상:
- 카카오맵 웹사이트 (공개 정보만)
- 네이버 플레이스 (리뷰, 가격대)
- 인스타그램 해시태그 페이지

주의사항:
- User-Agent 명시
- Rate Limiting (초당 1-2 요청)
- 장애 발생 시 즉시 중단
- 개인정보 수집 금지
```

#### 3. 수작업 큐레이션 (3순위, 품질 보증)
```
목적:
- 데이터 품질 검증
- 누락된 핵심 장소 추가
- 카테고리 재분류

큐레이션 프로세스:
1. PM/디자이너가 직접 방문/조사
2. 장소별 특징, 추천 포인트 작성
3. 예상 비용, 소요 시간 검증
4. data_source = "manual"
```

### Social Proof 전략 (리뷰 신뢰도 강화)

#### Instagram Graph API 제약사항
```
❌ 현실 불가능한 이유:
- Hashtag Search API: 2018년 이후 Deprecated (중단됨)
- 공개 게시물 크롤링: Meta 정책상 원천 차단
- 타인 계정 접근: 본인 소유 비즈니스 계정만 가능
- Graph API 정책: "자신의 콘텐츠 관리 용도"로만 제공

→ Instagram 데이터 자동 수집 전략 폐기
```

#### 대안 1: 블로그 리뷰 크롤링 (네이버 블로그)
```
목적:
- 실제 방문 후기 텍스트 제공
- 데이터 신빙성 강화
- 장소별 분위기/특징 파악

데이터 소스:
- 네이버 블로그 검색 API (공식, 무료)
- 일 25,000건 제한

API Endpoint:
GET https://openapi.naver.com/v1/search/blog.json
Query Params:
  - query: "홍대 연남동 카페 후기"
  - display: 10 (결과 개수)
  - sort: sim (정확도순) or date (최신순)

Response:
{
  "items": [
    {
      "title": "연남동 숨은 카페 찾았어요!",
      "link": "https://blog.naver.com/...",
      "description": "분위기 좋고 커피 맛있어요...",
      "bloggername": "블로거명",
      "postdate": "20251001"
    }
  ]
}

데이터 수집 전략:
1. 장소별 키워드 매핑
   - places.blog_keywords 컬럼 추가
   - 예: "연남동 카페 이름" + "후기"
2. 매일 배치 작업 (오전 2시)
   - 최근 6개월 블로그 포스트 수집
   - 방문 후기 키워드 필터링 ("다녀왔어요", "추천", "맛있어요")
3. AI 요약 및 평가
   - Claude API로 리뷰 요약 (3줄)
   - 긍정/부정 감성 분석
   - 신뢰도 스코어 (1~5)
4. blog_reviews 테이블 저장

UI 활용:
- 코스 상세 화면에 "블로그 후기 요약" 섹션
- "실제 방문자 리뷰 N개" 배지
- 원문 링크 제공 (네이버 블로그)
```

#### 대안 2: 사용자 생성 콘텐츠 (UGC)
```
목적:
- 자체 플랫폼 내 리뷰 데이터 축적
- 1st Party Data 확보
- 피드백 루프 강화

기능:
1. 데이트 후 사진 업로드 (선택)
   - "데이트 인증샷 남기기" 버튼
   - S3에 이미지 저장 (CloudFront CDN)
   - 장소별 갤러리 형태로 표시
2. 간단한 한줄 리뷰
   - "이 장소가 어땠나요?" (텍스트)
   - 별점 + 키워드 태그 (#분위기좋음, #가성비)
3. 리워드 시스템
   - 사진 업로드 시 포인트 적립 (추후)
   - 베스트 리뷰 선정 (월 1회)

user_reviews 테이블:
CREATE TABLE user_reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    place_id BIGINT REFERENCES places(id),
    saved_course_id BIGINT REFERENCES saved_courses(id),
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    photo_url TEXT,  -- S3 URL
    tags JSONB,  -- ["분위기좋음", "가성비"]
    created_at TIMESTAMP DEFAULT NOW(),

    INDEX idx_place_rating (place_id, rating DESC)
);

초기 전략:
- MVP에서는 간단한 별점 + 한줄평만 수집
- Phase 2에서 사진 업로드 기능 추가
- 3개월 후 충분한 UGC 확보 시 Instagram 대체
```

#### 대안 3: 큐레이션 콘텐츠 (수작업)
```
목적:
- 초기 데이터 품질 보증
- 에디터 추천 장소 강조
- 브랜드 신뢰도 구축

프로세스:
1. PM/디자이너가 직접 방문 후 작성
   - 장소별 특징, 추천 포인트
   - 대표 사진 촬영 (자체 보유)
   - 예상 비용, 분위기, 추천 시간대
2. places 테이블에 저장
   - editor_review TEXT (에디터 리뷰)
   - editor_photos JSONB (사진 URL 배열)
   - is_editor_pick BOOLEAN (에디터 추천)
3. UI 강조
   - "에디터 추천" 배지
   - 코스 생성 시 가중치 부여

초기 목표:
- Tier 1 지역 500곳 중 100곳 (20%)
- 주 10곳씩 방문 → 10주 완료
```

#### 최종 Social Proof 전략
```
Phase별 접근:
- Phase 0-1 (MVP):
  * 큐레이션 콘텐츠 (100곳, 에디터 직접 작성)
  * 네이버 블로그 리뷰 API (자동 수집)

- Phase 2 (PMF 검증 후):
  * 사용자 리뷰 수집 (별점 + 한줄평)
  * 리워드 시스템 도입

- Phase 3 (성장기):
  * 사진 업로드 기능 추가
  * 베스트 리뷰 선정 및 노출
  * 자체 UGC로 Instagram 완전 대체

Instagram 완전 배제 이유:
1. API 정책상 타인 콘텐츠 수집 불가
2. 크롤링은 법적 리스크 高
3. 자체 UGC가 더 신뢰도 높음
4. 1st Party Data 확보가 장기 전략
```

---

## 🛠️ Functional Requirements (기능 요구사항)

### FR-1: 사용자 인증 (Authentication)

#### FR-1.1: 선택적 로그인 전략 (Optional Login)
```
핵심 전략:
- 비로그인 사용자도 모든 메인 기능 사용 가능
- 개인화 기능 사용 시에만 로그인 유도
- 최대한 많은 사용자가 서비스를 경험하도록 설계

비로그인 사용 가능 기능 (Core Features):
✅ 코스 생성 (지역/유형/예산 입력 → AI 추천)
✅ 코스 상세 보기 (장소 정보, 지도, 동선)
✅ 코스 공유하기 (링크 복사, 카카오톡 공유)
✅ 재추천 받기 ("다른 코스 보기")

로그인 필요 기능 (Personalization):
🔒 코스 저장하기 (내 보관함)
🔒 저장된 코스 목록 보기
🔒 데이트 후 만족도 평가
🔒 개인화 추천 (과거 이력 기반)

로그인 유도 시점:
- "저장" 버튼 클릭 시 → 로그인 팝업
- "내 코스" 탭 클릭 시 → 로그인 팝업
- 평가 요청 푸시 알림 클릭 시 → 로그인 팝업
```

#### FR-1.2: 카카오톡 간편 로그인 (단일 인증 수단)
```
요구사항:
- 카카오톡 간편 로그인만 지원
- 다른 소셜 로그인 제외 (네이버, 구글 불필요)
- 이유: 타겟 사용자(Z세대)의 99%가 카카오톡 사용

기술 스펙:
- OAuth 2.0 표준
- JWT 토큰 기반 세션 관리
- Refresh Token: 30일
- Access Token: 1시간
- 웹: HTTP-only 쿠키 + localStorage 병행

API Endpoint:
POST /api/v1/auth/kakao
Request:
{
  "authorization_code": "string"
}
Response:
{
  "access_token": "string",
  "refresh_token": "string",
  "user_id": "string",
  "is_new_user": boolean
}

Set-Cookie: refresh_token=...; HttpOnly; Secure; SameSite=Strict

로그인 팝업 디자인:
- 간결한 모달 형태
- "카카오톡으로 3초 만에 시작하기" CTA
- 혜택 명시: "저장 & 히스토리 기능 사용"
- "나중에" 버튼 제공 (강제하지 않음)
```

#### FR-1.3: 만 19세 이상 인증 (선택적)
```
요구사항:
- 본인인증 선택적 (로그인 후에도 필수 아님)
- 술집/바 코스 생성 시에만 인증 유도
- 청소년보호법 준수

플로우:
1. 비인증 사용자가 "감성데이트" 선택 (술집 포함 가능)
2. [시스템] "만 19세 이상 확인이 필요해요" 팝업
3. [사용자] "본인인증" 버튼 클릭 → PASS 본인인증
4. 만 19세 미만 → "감성데이트는 만 19세부터 이용 가능해요" 메시지
5. 인증 완료 → user.is_adult = true 저장

미인증 사용자 제한:
- "감성데이트" 유형 선택 불가 (비활성화)
- 다른 유형(문화데이트, 맛집투어, 액티비티)은 자유롭게 사용
```

---

### FR-2: 코스 추천 엔진 (Recommendation Engine)

#### FR-2.1: 사용자 입력 처리
```
Input Parameters:
1. region: string (예: "hongdae", "gangnam", "seongsu")
   - 지도 기반 비주얼 선택 UI (인터랙티브 맵)
   - 서울 일러스트 지도에 주요 상권 영역을 블록 형태로 시각화
   - 각 영역을 터치/클릭하여 선택
   - 초기 지원 지역 (3단계 우선순위):
     * Tier 1 (필수): 홍대, 강남, 성수, 연남, 이태원
     * Tier 2 (중요): 압구정, 잠실, 여의도, 건대, 신촌
     * Tier 3 (선택): 망원, 합정, 서촌, 북촌, 혜화
   - UI 특징:
     * 감성적인 일러스트 맵 디자인 (Seoul Date Pop 스타일)
     * 각 지역 아이콘(이모지)과 이름 표시
     * 선택 시 하이라이트 + 확대 애니메이션
     * 하단 빠른 선택 버튼 제공 (스크롤 가능)
     * 선택된 지역 정보 카드 표시 (추천 가능한 장소 수)

2. date_type: enum
   - "문화데이트" (전시, 뮤지엄, 카페)
   - "맛집투어" (식당, 디저트)
   - "액티비티" (방탈출, 볼링, 스크린골프)
   - "감성데이트" (루프탑, 와인바, 야경)

3. budget: integer (단위: 원)
   - 슬라이더 UI: 30,000원 ~ 300,000원
   - 기본값: 100,000원
   - 간격: 10,000원

Validation:
- region: NOT NULL, 지원 지역 리스트 내
- date_type: NOT NULL, enum 값만 허용
- budget: 30,000 <= budget <= 300,000
```

#### FR-2.2: AI 추천 로직
```
Process Flow:
1. [Cache Check] Redis에서 동일 조건 캐시 확인
   - Cache Key: MD5(region + date_type + budget)
   - Cache Hit → 즉시 반환 (응답시간 0.5초)
   
2. [Cache Miss] DB에서 장소 후보 조회
   - SQL:
     SELECT * FROM places 
     WHERE region = ? 
     AND date_type LIKE %?%
     AND price_range <= ?
     AND is_active = true
     LIMIT 50
   
3. [AI Call] Claude Sonnet 4.5 API 호출
   - Prompt Template:
     """
     당신은 데이트 코스 큐레이터입니다.
     
     조건:
     - 지역: {region}
     - 데이트 유형: {date_type}
     - 예산: {budget}원
     
     사용 가능한 장소 목록:
     {places_json}
     
     요구사항:
     1. 3개의 코스를 추천하세요 (각 코스는 3~4곳)
     2. 총 예산은 {budget}원의 95~100% 범위
     3. 동선을 최적화하세요 (이동 시간 최소화)
     4. 각 코스는 최소 50% 이상 다른 장소로 구성
     
     응답 형식 (JSON):
     {
       "courses": [
         {
           "title": "코스 제목",
           "places": [
             {
               "place_id": "id",
               "name": "장소명",
               "estimated_cost": 35000,
               "estimated_time": "1시간"
             }
           ],
           "total_cost": 95000,
           "total_time": "4시간"
         }
       ]
     }
     """
   
4. [Validation] AI 응답 검증
   - JSON 파싱 가능한가?
   - courses 배열 길이 = 3
   - 각 코스의 total_cost <= budget
   - place_id가 DB에 존재하는가?
   
5. [Cache Save] Redis에 결과 저장 (TTL: 24시간)

6. [Response] 클라이언트에 반환
```

**API Endpoint:**
```
POST /api/v1/courses/generate
Request:
{
  "region": "홍대",
  "date_type": "문화데이트",
  "budget": 100000
}

Response:
{
  "request_id": "uuid",
  "generated_at": "2025-10-13T14:30:00Z",
  "courses": [
    {
      "course_id": "c1",
      "title": "홍대 감성 문화 코스",
      "places": [
        {
          "place_id": "p123",
          "name": "서울숲 갤러리",
          "category": "전시",
          "address": "서울 성동구...",
          "estimated_cost": 15000,
          "estimated_time": "1.5시간",
          "naver_place_url": "https://..."
        },
        {
          "place_id": "p456",
          "name": "카페 온더그라운드",
          "category": "카페",
          "estimated_cost": 18000,
          "estimated_time": "1시간"
        },
        {
          "place_id": "p789",
          "name": "이태원 루프탑 바",
          "category": "바",
          "estimated_cost": 62000,
          "estimated_time": "2시간"
        }
      ],
      "total_cost": 95000,
      "total_time": "4.5시간",
      "route_map_url": "https://map.kakao.com/..."
    },
    // ... 2개 더
  ]
}
```

#### FR-2.3: 성능 요구사항
```
SLA:
- P50 (중앙값): 3초 이내
- P95: 8초 이내
- P99: 15초 이내
- Timeout: 30초 (그 이후 Fallback)

Fallback Strategy:
1. Claude API 타임아웃 → 룰 베이스 추천
2. 룰 베이스 예시:
   - 홍대 + 문화데이트 + 10만원 → 미리 정의된 템플릿 3개 반환
   - DB에 저장된 "인기 코스 TOP 10" 활용

Circuit Breaker:
- Failure Rate > 50% → Circuit Open (30초간 Fallback만 사용)
- 30초 후 Half-Open (1개 요청 테스트) → 성공 시 Closed
```

---

### FR-3: 코스 저장 & 관리

#### FR-3.1: 코스 저장
```
API Endpoint:
POST /api/v1/courses/save
Request:
{
  "course_id": "c1",
  "request_id": "uuid"  // 원본 생성 요청 ID
}

Response:
{
  "saved_course_id": "sc123",
  "saved_at": "2025-10-13T14:35:00Z"
}

Database Schema:
Table: saved_courses
- id (PK)
- user_id (FK)
- course_id
- saved_at
- is_deleted (soft delete)
- visited_at (nullable, 실제 방문 날짜)
```

#### FR-3.2: 코스 목록 조회
```
API Endpoint:
GET /api/v1/courses/saved?page=1&size=20&sort=latest

Response:
{
  "courses": [
    {
      "saved_course_id": "sc123",
      "course_title": "홍대 감성 문화 코스",
      "saved_at": "2025-10-13T14:35:00Z",
      "total_cost": 95000,
      "region": "홍대",
      "thumbnail_url": "https://..."
    }
  ],
  "pagination": {
    "current_page": 1,
    "total_pages": 5,
    "total_count": 87
  }
}
```

---

### FR-4: 피드백 시스템

#### FR-4.1: 만족도 평가
```
API Endpoint:
POST /api/v1/feedback
Request:
{
  "saved_course_id": "sc123",
  "overall_rating": 5,  // 1~5
  "visited": true,
  "place_feedbacks": [
    {
      "place_id": "p123",
      "still_open": true,
      "actual_cost": 17000,
      "rating": 5
    }
  ],
  "comment": "최고였어요!" (optional)
}

Response:
{
  "feedback_id": "f123",
  "created_at": "2025-10-15T20:00:00Z"
}

Trigger:
- 저장 후 48시간 뒤 푸시 알림 발송
- 푸시 제목: "데이트 다녀오셨나요? 😊"
- 푸시 내용: "5초만 평가해주시면 더 좋은 추천을 드릴게요!"
```

#### FR-4.2: 데이터 활용
```
피드백 → DB 자동 업데이트:
1. still_open = false (2건 이상) → places.is_active = false
2. actual_cost 평균 → places.price_range 업데이트
3. rating 평균 → places.user_rating 업데이트

AI 프롬프트 개선:
- 평점 4.5 이상 장소 → "강력 추천" 가중치 상승
- 평점 3.0 이하 장소 → 제외 처리
```

---

## 🔧 Non-Functional Requirements (비기능 요구사항)

### NFR-1: 성능 (Performance)
```
요구사항:
- 코스 생성 API: P95 < 8초
- 일반 API: P95 < 500ms
- 웹 초기 로딩: 3초 이내 (LCP < 2.5s)
- 이미지 로딩: Lazy Loading + WebP 포맷

측정 도구:
- Backend: Spring Boot Actuator
- Frontend: Lighthouse CI, Next.js Analytics
- Monitoring:
  * AWS CloudWatch: 시스템 메트릭, 로그 집계, 알람
  * Sentry: 에러 추적, 성능 분석 (APM), 트랜잭션 모니터링

모니터링 상세:
1. AWS CloudWatch
   - 메트릭 수집:
     * ECS Task: CPU, Memory, Network (1분 간격)
     * RDS: Connection Count, CPU, IOPS
     * ElastiCache: Hit Rate, CPU, Network
     * ALB: Request Count, Target Response Time, HTTP 5xx
   - 로그 수집:
     * ECS Container Logs (Spring Boot 애플리케이션 로그)
     * Access Logs (ALB)
   - 알람 설정:
     * ECS CPU > 80% (5분 연속) → Slack 알람
     * RDS Connection > 80개 → Slack 알람
     * ALB HTTP 5xx > 1% → Slack Critical 알람
     * Error Rate > 5% → Slack Critical 알람

2. Sentry
   - 에러 추적:
     * Backend: Java Exception 자동 캡처
     * Frontend: JavaScript Error, React Error Boundary
     * Source Map 연동 (프로덕션 배포 시)
   - 성능 모니터링 (APM):
     * Transaction Tracing: API 요청별 실행 시간
     * Database Query 성능: Slow Query 감지
     * External API 호출: 외부 API 응답 시간 추적
     * Frontend Web Vitals: LCP, FID, CLS
   - 알람 설정:
     * New Critical Error → Slack 알람
     * Performance Degradation > 2x → Slack 알람
     * Error Rate Spike > 10% → Slack Critical 알람
   - 통합:
     * Sentry Release Tracking (배포 추적)
     * GitHub PR 연동 (이슈 자동 태그)

3. Langfuse (LLM Observability)
   - AI 요청/응답 추적:
     * Claude API 호출별 Trace 기록
     * Request ID와 Langfuse Trace ID 매핑
     * 프롬프트 입력/출력 로깅
     * 응답 시간, 토큰 사용량 추적
   - 비용 분석:
     * 일별/월별 Claude API 토큰 사용량
     * 요청당 평균 비용 계산
     * 예산 초과 알람 (월 $500 초과 시)
   - 프롬프트 버전 관리:
     * 프롬프트 템플릿 버전 관리
     * A/B 테스트 지원 (프롬프트 변경 시)
     * 성공률/만족도별 프롬프트 성능 비교
   - 품질 모니터링:
     * AI 응답 품질 스코어링
     * JSON 파싱 실패율 추적
     * Fallback 실행 빈도 모니터링
   - 대시보드:
     * Langfuse Cloud 대시보드 (웹)
     * 주간 AI 성능 리포트 자동 생성
```

### NFR-2: 확장성 (Scalability)
```
목표:
- 동시 사용자: 500명 (피크 타임)
- 월간 요청: 100만 건 (성장 시)

Architecture:
- Stateless API 서버 → 수평 확장 가능
- Redis 캐싱 → DB 부하 감소
- CDN (CloudFront) → 정적 파일 분산

Load Testing:
- nGrinder: 500 concurrent users, 10분 지속
- 목표: Error Rate < 1%, Response Time P95 < 10초
```

### NFR-3: 가용성 (Availability)
```
SLA: 99.5% uptime (월 3.6시간 다운타임 허용)

장애 대응:
- Health Check: /actuator/health (30초마다, ECS Task Health Check)
- Auto Restart: 3회 연속 실패 시 ECS 컨테이너 재시작
- Circuit Breaker: Claude API 장애 시 Fallback (룰 베이스 추천)
- Runbook: 주요 장애 대응 매뉴얼 문서화

Monitoring & Alerting:
- AWS CloudWatch Alarms:
  * ECS Service Health Check Failed → Slack 알람
  * RDS CPU > 90% → Slack 알람
  * ALB Unhealthy Target Count > 0 → Slack 알람
  * CloudWatch Logs Insights: 에러 패턴 자동 분석
- Sentry Alerts:
  * New Critical Error → Slack Critical 알람
  * Error Rate > 5% → Slack 알람
  * Performance Regression Detected → Slack 알람
- Langfuse Alerts:
  * Claude API 비용 > $500/월 → Slack 알람
  * AI 응답 실패율 > 10% → Slack 알람
  * Fallback 실행율 > 20% → Slack 알람

알람 통합:
- CloudWatch → SNS → Slack Webhook
- Sentry → Slack Integration
- Langfuse → Webhook → Slack (비용/품질 알람)
```

### NFR-4: 보안 (Security)
```
요구사항:
1. HTTPS 필수 (Let's Encrypt SSL)
2. API Rate Limiting: 10 req/min (무료 유저)
3. SQL Injection 방지: PreparedStatement 사용
4. XSS 방지: 사용자 입력 sanitization
5. 개인정보 암호화: AES-256 (전화번호, 위치 정보)

데이터 보관:
- 데이트 기록: 1년 후 자동 삭제
- 피드백: 익명화 후 영구 보관
- 접근 로그: 3개월 보관
```

### NFR-5: 법적 준수 (Compliance)
```
필수 사항:
1. 개인정보보호법
   - 개인정보처리방침 명시
   - 위치정보 수집 동의 (명시적)
   - 마케팅 수신 동의 (선택적)
   
2. 청소년보호법
   - 만 19세 미만 가입 차단
   - 본인인증 필수 (PASS/NICE)
   
3. 이용약관
   - 서비스 제공 범위 명시
   - 면책 조항: "추천 장소의 영업/품질 보증하지 않음"
   - 환불 정책 (프리미엄)

개인정보 수집 항목:
- 필수: 이름, 전화번호, 생년월일 (본인인증)
- 선택: 프로필 사진, 선호 지역
- 자동 수집: 서비스 이용 기록, 접속 로그
```

---

## 🚫 Out of Scope (MVP 범위 밖)

### Phase 2 이후 개발 (MVP에 포함 안 함)
```
❌ 네이티브 모바일 앱 (iOS/Android) - PMF 검증 후 개발
❌ 네이버/구글 로그인 - 카카오톡만으로 충분
❌ 회원가입 시 본인인증 필수 - 선택적으로만 (감성데이트 시)
❌ 실시간 채팅 (연인 간)
❌ 커뮤니티 기능 (후기 게시판)
❌ 영상 콘텐츠 (데이트 Vlog)
❌ 프리미엄 구독 (MVP는 전체 무료)
❌ 식당 예약 연동 (추후 B2B 제휴 시)
❌ 데이트 일정 캘린더
❌ 커플 앱 연동 (Between, 하루)
❌ 지방 광역시 (부산/대구) - MVP는 서울만
❌ 외국어 지원 (영어/일본어) - MVP는 한국어만
```

### 의도적 제외 사유
```
이유 1: 빠른 PMF 검증 집중
- 코어 기능(코스 추천)만 완벽하게 구현
- 부가 기능은 PMF 달성 후 추가

이유 2: 리소스 제약
- 개발 기간: 30일
- 팀 구성: Backend 2명, Frontend 1명, DevOps 1명, Designer 1명, PM 1명
- 예산: 인프라 월 500만원 이내
  * ECS Fargate: ~300만원 (0.5 vCPU, 1GB RAM × 2 tasks)
  * RDS: ~100만원 (db.t3.medium)
  * ElastiCache: ~50만원 (cache.t3.micro)
  * 기타 (ALB, CloudWatch, Secrets Manager): ~50만원

이유 3: 기술적 복잡도 최소화
- Monolith 구조 유지
- 외부 연동 최소화 (Claude API만)
```

---

## 🏗️ Technical Architecture (기술 아키텍처)

### Tech Stack
```
Frontend:
- Framework: Next.js 14 (App Router)
- Language: TypeScript
- State Management: Zustand
- HTTP Client: Axios
- UI Library: Tailwind CSS + shadcn/ui
- Maps/SVG: React SVG + D3.js (지역 맵 인터랙션)
- Deployment: Vercel

Backend:
- Framework: Spring Boot 3.2 (Java 17)
- ORM: Spring Data JPA + QueryDSL
- Database: PostgreSQL 15
- Cache: Redis 7
- AI: Anthropic Claude Sonnet 4.5 API
- Monitoring:
  * Metrics: AWS CloudWatch (시스템 메트릭, 로그 집계)
  * APM & Error Tracking: Sentry (에러 추적, 성능 분석)
  * LLM Observability: Langfuse (AI 요청/응답 추적, 비용 분석, 프롬프트 버전 관리)
  * Health Check: Spring Boot Actuator
- Data Sources:
  * Places: Kakao Local API (무료, 일 300,000건)
  * Reviews: Naver Blog Search API (무료, 일 25,000건)
  * UGC: 사용자 리뷰 (자체 수집, Phase 2)

Infrastructure:
- Cloud: AWS
- Container: ECS Fargate (Backend)
- Database: RDS PostgreSQL (db.t3.medium)
- Cache: ElastiCache Redis (cache.t3.micro)
- CDN: CloudFront
- Load Balancer: ALB
- Container Registry: ECR
- IaC: Terraform
- CI/CD:
  * Frontend: Vercel (자동 배포)
  * Backend: GitHub Actions → Docker → ECR → ECS
- Secrets: AWS Secrets Manager
```

### System Architecture Diagram
```
[Client - Next.js Web]
    ↓            ↓
[Vercel]    [CloudFront CDN]
(SSR/SSG)   ← 정적 파일 (이미지)
    ↓
[Application Load Balancer]
         ↓
[ECS Fargate Cluster]
  ├─ Task 1: Spring Boot
  └─ Task 2: Spring Boot
  (Auto Scaling)
    ↙       ↓       ↘
[ElastiCache] [RDS] [Claude API]
(Redis 캐싱) (PostgreSQL) (AI 추천)
         ↓
[AWS Secrets Manager]
(DB 자격증명, API 키)
         ↓
[Monitoring Layer]
  ├─ AWS CloudWatch (인프라 메트릭, 로그)
  ├─ Sentry (에러 추적, APM)
  └─ Langfuse (LLM Observability)
         ↓
[External Data Sources]
  ├─ Kakao Local API (장소 데이터, 일 300K건)
  └─ Naver Blog API (리뷰 데이터, 일 25K건)
```

### Database Schema (핵심 테이블)

```sql
-- 사용자
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    kakao_id VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) ENCRYPTED,  -- AES-256 암호화
    birth_date DATE NOT NULL,
    is_adult BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    last_login_at TIMESTAMP
);

-- 지역 메타데이터
CREATE TABLE region_metadata (
    id VARCHAR(50) PRIMARY KEY,        -- "hongdae", "gangnam"
    display_name VARCHAR(50) NOT NULL, -- "홍대", "강남"
    emoji VARCHAR(10),                 -- "🎨", "🏙️"
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    map_position_x INTEGER,            -- SVG 지도 상 X 좌표
    map_position_y INTEGER,            -- SVG 지도 상 Y 좌표
    svg_path TEXT,                     -- SVG Path 데이터
    popularity_score INTEGER DEFAULT 0,
    description TEXT,
    keywords JSONB,                    -- ["감성", "문화", "카페"]
    adjacent_regions JSONB,            -- ["yeonnam", "hapjeong"]
    tier INTEGER DEFAULT 3,            -- 1: 필수, 2: 중요, 3: 선택
    is_active BOOLEAN DEFAULT TRUE,
    display_order INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    INDEX idx_active_tier (is_active, tier, display_order)
);

-- 장소
CREATE TABLE places (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL,       -- region_metadata.id 참조
    category VARCHAR(50) NOT NULL,
    address TEXT NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    price_range INTEGER NOT NULL,      -- 1인당 평균 가격
    estimated_time VARCHAR(20),        -- "1~2시간"
    kakao_place_id VARCHAR(50),        -- 카카오 플레이스 ID
    kakao_place_url TEXT,              -- 카카오맵 URL
    blog_keywords VARCHAR(200),        -- 블로그 검색 키워드
    user_rating DECIMAL(2, 1),         -- 1.0~5.0
    data_source VARCHAR(20) NOT NULL,  -- "kakao_api", "crawling", "manual"
    editor_review TEXT,                -- 에디터 리뷰
    editor_photos JSONB,               -- 에디터 사진 URL 배열
    is_editor_pick BOOLEAN DEFAULT FALSE,  -- 에디터 추천
    is_active BOOLEAN DEFAULT TRUE,
    last_updated_at TIMESTAMP DEFAULT NOW(),

    INDEX idx_region_category (region, category),
    INDEX idx_active_rating (is_active, user_rating DESC),
    INDEX idx_kakao_place (kakao_place_id),
    INDEX idx_editor_pick (is_editor_pick, user_rating DESC)
);

-- 블로그 리뷰 (네이버 블로그)
CREATE TABLE blog_reviews (
    id BIGSERIAL PRIMARY KEY,
    place_id BIGINT REFERENCES places(id),
    blog_title VARCHAR(200) NOT NULL,
    blog_url TEXT NOT NULL,
    blogger_name VARCHAR(100),
    description TEXT,                  -- 블로그 본문 요약
    post_date DATE NOT NULL,
    ai_summary TEXT,                   -- Claude API 요약 (3줄)
    sentiment VARCHAR(20),             -- "positive", "neutral", "negative"
    trust_score INTEGER CHECK (trust_score BETWEEN 1 AND 5),
    fetched_at TIMESTAMP DEFAULT NOW(),

    INDEX idx_place_date (place_id, post_date DESC),
    INDEX idx_trust_score (trust_score DESC)
);

-- 사용자 리뷰 (UGC)
CREATE TABLE user_reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    place_id BIGINT REFERENCES places(id),
    saved_course_id BIGINT REFERENCES saved_courses(id),
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    photo_url TEXT,                    -- S3 URL (Phase 2)
    tags JSONB,                        -- ["분위기좋음", "가성비"]
    is_verified BOOLEAN DEFAULT FALSE, -- 실제 방문 인증
    created_at TIMESTAMP DEFAULT NOW(),

    INDEX idx_place_rating (place_id, rating DESC),
    INDEX idx_user_created (user_id, created_at DESC),
    INDEX idx_verified (is_verified, created_at DESC)
);

-- 코스 생성 요청
CREATE TABLE course_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id BIGINT REFERENCES users(id),  -- NULL 허용 (비로그인 사용자)
    session_id VARCHAR(100),               -- 비로그인 사용자 세션 추적
    region VARCHAR(50) NOT NULL,
    date_type VARCHAR(50) NOT NULL,
    budget INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    response_time_ms INTEGER,  -- 성능 측정용
    langfuse_trace_id VARCHAR(100),  -- Langfuse 추적 ID
    is_guest BOOLEAN DEFAULT FALSE,  -- 비로그인 요청 여부

    INDEX idx_user_created (user_id, created_at DESC),
    INDEX idx_session_created (session_id, created_at DESC),
    INDEX idx_langfuse_trace (langfuse_trace_id)
);

-- 생성된 코스
CREATE TABLE courses (
    id VARCHAR(50) PRIMARY KEY,
    request_id UUID REFERENCES course_requests(id),
    title VARCHAR(100) NOT NULL,
    total_cost INTEGER NOT NULL,
    total_time VARCHAR(20),
    places_json JSONB NOT NULL,  -- 장소 목록 (비정규화)
    created_at TIMESTAMP DEFAULT NOW()
);

-- 저장된 코스
CREATE TABLE saved_courses (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),  -- 저장은 로그인 필수
    course_id VARCHAR(50) REFERENCES courses(id),
    saved_at TIMESTAMP DEFAULT NOW(),
    visited_at TIMESTAMP,  -- 실제 방문 날짜
    is_deleted BOOLEAN DEFAULT FALSE,

    INDEX idx_user_saved (user_id, saved_at DESC)
);

-- 비로그인 사용자 코스 공유 추적
CREATE TABLE guest_course_shares (
    id BIGSERIAL PRIMARY KEY,
    course_id VARCHAR(50) REFERENCES courses(id),
    session_id VARCHAR(100) NOT NULL,     -- 비로그인 세션 ID
    share_method VARCHAR(20) NOT NULL,    -- "kakao", "link_copy"
    shared_at TIMESTAMP DEFAULT NOW(),

    INDEX idx_session_shared (session_id, shared_at DESC),
    INDEX idx_course_shares (course_id, shared_at DESC)
);

-- 피드백
CREATE TABLE feedbacks (
    id BIGSERIAL PRIMARY KEY,
    saved_course_id BIGINT REFERENCES saved_courses(id),
    overall_rating INTEGER CHECK (overall_rating BETWEEN 1 AND 5),
    visited BOOLEAN NOT NULL,
    comment TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);

-- 장소별 피드백
CREATE TABLE place_feedbacks (
    id BIGSERIAL PRIMARY KEY,
    feedback_id BIGINT REFERENCES feedbacks(id),
    place_id BIGINT REFERENCES places(id),
    still_open BOOLEAN NOT NULL,
    actual_cost INTEGER,
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    visit_date DATE NOT NULL,
    
    INDEX idx_place_visit (place_id, visit_date DESC)
);
```

---

## 📅 Release Plan (출시 계획)

### Phase 0: 준비 (D-30 ~ D-15)
```
Week 1-2 (10/14 ~ 10/27):
✅ 기술 스택 셋업 (개발 환경 구축)
✅ Terraform 인프라 코드 작성
   - VPC, Subnet, Security Group
   - ECS Fargate Cluster & Service
   - ALB, Target Group
   - RDS PostgreSQL, ElastiCache Redis
   - ECR Repository
   - AWS Secrets Manager
   - CloudWatch Logs, Alarms, Dashboards
   - SNS Topics (알람 발송용)
✅ 모니터링 셋업
   - AWS CloudWatch:
     * 커스텀 대시보드 생성 (ECS, RDS, ALB 메트릭)
     * 알람 규칙 설정 (CPU, Memory, Error Rate)
     * Log Groups 생성 및 Retention 정책 (30일)
     * SNS → Slack Webhook 연동
   - Sentry:
     * Backend: Sentry Java SDK 설정
     * Frontend: Sentry Next.js SDK 설정
     * Source Map 업로드 자동화 (CI/CD)
     * Slack Integration 설정
   - Langfuse:
     * Langfuse Cloud 계정 생성
     * Langfuse Java SDK 설정 (Spring Boot)
     * Claude API Wrapper 구현 (Langfuse Trace 자동 기록)
     * 프롬프트 템플릿 등록 (코스 생성 프롬프트)
     * Webhook 설정 (비용/품질 알람 → Slack)
✅ DB 스키마 설계 & 마이그레이션
✅ CI/CD 파이프라인 구축
   - GitHub Actions Workflow
   - Docker 빌드 & ECR 푸시
   - ECS 태스크 정의 업데이트 & 배포
   - Sentry Release Tracking 자동화
✅ 초기 장소 데이터 수집
   - Tier 1 지역 (홍대/강남/성수/연남/이태원): 각 100곳 = 500곳
   - 데이터 소스 전략:
     * 1순위: Kakao Local API (무료, 키워드 검색)
     * 2순위: 크롤링 (Selenium/Playwright, 법적 검토 필수)
     * 3순위: 수작업 큐레이션 (데이터 품질 보증)
   - 데이터 수집 항목:
     * 장소명, 주소, 카테고리, 위도/경도
     * 카카오 플레이스 ID, URL
     * 예상 가격대, 영업시간
✅ 네이버 블로그 리뷰 연동
   - Naver Open API 셋업
     * Naver Developers 계정 생성
     * 애플리케이션 등록 (Client ID, Secret)
     * Blog Search API 권한 획득
   - 데이터 수집 전략:
     * 장소별 키워드 매핑 (places.blog_keywords)
     * 최근 6개월 블로그 포스트 수집 (일 25,000건 제한)
     * 방문 후기 키워드 필터링
   - AI 요약 및 분석:
     * Claude API로 리뷰 요약 (3줄)
     * 긍정/부정 감성 분석
     * 신뢰도 스코어 부여 (1~5)
✅ 에디터 큐레이션 콘텐츠 작성
   - Tier 1 지역 500곳 중 100곳 (20%) 목표
   - PM/디자이너 직접 방문 및 리뷰 작성
   - 대표 사진 촬영 (자체 보유)
   - places 테이블에 editor_review, editor_photos 저장
   - 주 10곳씩 방문 → 10주 완료 계획
✅ 지역 메타데이터 준비
   - 15개 지역 SVG Path 디자인 (디자이너 협업)
   - region_metadata 테이블 데이터 삽입
   - 지역별 아이콘(이모지), 키워드, 설명 작성
✅ 법적 문서 준비 (이용약관, 개인정보처리방침)
```

### Phase 1: 코어 기능 개발 (D-14 ~ D-7)
```
Week 3 (10/28 ~ 11/3):
✅ 사용자 인증 (카카오 로그인, 본인인증)
✅ 코스 추천 API (Claude 연동 + 캐싱)
✅ 지역 메타데이터 API (GET /api/v1/regions/metadata)
✅ 코스 저장/조회 API
✅ 기본 UI/UX
   - 홈 화면
   - 지역 선택 지도 UI (인터랙티브 맵)
   - 데이트 유형 선택 (카드)
   - 예산 선택 (슬라이더)
   - 코스 결과 화면
   - 코스 상세 화면
   - 저장된 코스 목록

개발 담당:
- Backend: 김개발 (인증, API)
- Backend: 박서버 (AI 연동, 캐싱, 지역 메타데이터 API)
- Frontend: 최앱 (Next.js + TypeScript, 인터랙티브 지도 컴포넌트)
- DevOps: 이인프라 (Terraform 인프라 코드, ECS 배포 파이프라인)
- Designer: 이디자인 (지역 맵 SVG 디자인, 애니메이션)
```

### Phase 2: 테스트 & 최적화 (D-6 ~ D-1)
```
Week 4 (11/4 ~ 11/10):
✅ 통합 테스트 (Postman + Jest)
✅ 부하 테스트 (nGrinder, 목표: 500 concurrent users)
✅ 성능 최적화 (쿼리 튜닝, 인덱스 추가)
✅ 보안 점검 (OWASP Top 10)
✅ QA (버그 수정, 엣지 케이스)
```

### Phase 3: 소프트 런칭 (D-Day ~ D+7)
```
Week 5 (11/11 ~ 11/17):
✅ 베타 테스터 모집 (50명)
   - 페르소나 1: 20명 (계획형)
   - 페르소나 2: 30명 (즉흥형)
✅ 피드백 수집 (버그 리포트, 개선 제안)
✅ 긴급 패치 (Critical 버그 우선 수정)
✅ 지표 모니터링 (Onboarding Completion, Time-to-First-Course)

목표:
- Crash-Free Rate > 99%
- Onboarding Completion > 40%
```

### Phase 4: 공식 런칭 (D+8 ~)
```
Week 6+ (11/18 ~):
✅ 웹 정식 출시 (date-click.com)
✅ SEO 최적화 (메타태그, 사이트맵, robots.txt)
✅ 마케팅 캠페인 (인스타 광고, 인플루언서, 카카오톡 공유)
✅ 사용자 피드백 기반 개선
✅ PMF 검증 (D7 Retention 40%, NPS 40 달성 여부)

Week 8 (12/2):
→ PMF 회의: 피봇 vs 스케일업 결정
→ PMF 달성 시: 네이티브 앱 개발 착수 (Phase 2)
```

---

## 📊 Success Criteria (MVP 성공 기준)

### 런칭 후 30일 내 달성 목표
```
✅ MUST HAVE (필수):
- MAU: 10,000명
- D7 Retention: 40%+
- NPS: 40+
- Crash-Free Rate: 99%+
- Time-to-First-Course: <60초 (P95)

⚠️ SHOULD HAVE (중요):
- D30 Retention: 25%+
- Course Save Rate: 40%+
- Recommendation-to-Execution: 70%+
- Post-Date Satisfaction: 4.2/5.0

📊 NICE TO HAVE (추가):
- DAU/MAU: 25%+
- Organic Share Rate: 15%+
```

### PMF (Product-Market Fit) 판단 기준
```
✅ PMF 달성 = 다음 중 2가지 이상 충족:
1. D7 Retention 40%+ (3개월 연속)
2. NPS 40+ & Organic Growth 30%+
3. "Must Have" 설문: 40%+ 응답

→ PMF 달성 시: Series A 투자 유치 + 스케일업
→ PMF 미달 시: 피봇 or 개선 후 재도전
```

---

## 🔗 Appendix (부록)

### A. API 명세 전체 문서
```
Swagger/OpenAPI 문서: https://api.date-click.com/swagger-ui
Postman Collection: [첨부 파일 참조]

추가 API Endpoint:
GET /api/v1/regions/metadata
- 지역 선택 지도 UI를 위한 메타데이터 제공
- 각 지역의 SVG Path, 좌표, 인기도, 키워드 등 반환
- 응답 예시:
{
  "regions": [
    {
      "id": "hongdae",
      "display_name": "홍대",
      "emoji": "🎨",
      "coordinates": {"latitude": 37.5563, "longitude": 126.9241},
      "map_position": {"x": 100, "y": 150},
      "svg_path": "M50,80 L80,60...",
      "popularity_score": 95,
      "available_places_count": 127,
      "keywords": ["감성", "문화", "카페"],
      "tier": 1,
      "is_active": true
    }
  ]
}
```

### B. 디자인 시스템
```
Figma 링크: https://figma.com/date-click-design
컬러 팔레트:
- Primary: #FF6B6B (코랄 핑크)
- Secondary: #4ECDC4 (민트)
- Background: #FFFBF0 (아이보리)
- Text: #2D3436 (다크 그레이)

지역 선택 맵 디자인 가이드라인:
- 스타일: 일러스트 기반 감성 맵 (Seoul Date Pop 참고)
- 구성: 서울 주요 상권 영역을 블록 형태로 시각화
- 인터랙션:
  * 기본 상태: 투명도 40% + 얇은 테두리
  * 호버 상태: 투명도 80% + Ripple 효과
  * 선택 상태: 투명도 100% + 확대(1.05배) + 두꺼운 테두리
- 아이콘: 각 지역 특성을 대표하는 이모지 (예: 홍대 🎨, 강남 🏙️)
- 애니메이션:
  * 선택 시: Scale 1.0 → 1.05 (200ms ease-in-out)
  * 파동 효과: 선택 지역에서 확산 (선택 사항)
- 빠른 선택 버튼: 하단 가로 스크롤, 이모지 + 지역명
- 반응형: 작은 화면에서는 줌 인/아웃 기능 제공
- 지역별 색상 팔레트:
  * 홍대: #FF6B6B (코랄 레드)
  * 강남: #4ECDC4 (민트)
  * 성수: #95E1D3 (연한 민트)
  * 연남: #FFD93D (옐로우)
  * 이태원: #A29BFE (라벤더)
```

### C. 개발 환경 셋업 가이드
```
GitHub:
- Backend: https://github.com/date-click/backend
- Frontend: https://github.com/date-click/frontend
- Infrastructure: https://github.com/date-click/terraform

Terraform 구조:
terraform/
├── environments/
│   ├── dev/
│   ├── staging/
│   └── prod/
├── modules/
│   ├── vpc/
│   ├── ecs/
│   ├── rds/
│   ├── elasticache/
│   ├── alb/
│   └── secrets/
└── README.md

배포 순서:
1. terraform init
2. terraform plan -var-file=environments/dev/terraform.tfvars
3. terraform apply -var-file=environments/dev/terraform.tfvars
4. GitHub Actions로 Docker 빌드 & ECS 배포
```

### D. 리스크 레지스터
```
위험 요소 | 대응 방안 | 담당자 | 우선순위
-------|--------|-------|--------
AI API 비용 초과 | 캐싱 + Rate Limiting + Langfuse 비용 모니터링 | Backend 팀 | High
데이터 품질 낮음 | 에디터 큐레이션 + 블로그 리뷰 + 사용자 피드백 루프 | PM | High
법적 문제 (크롤링) | 법무법인 검토 + robots.txt 준수 + 면책 조항 | CEO | Critical
개발 지연 | Agile 스프린트 + 매일 스탠드업 | PM | Medium
SVG 맵 디자인 복잡도 | 초기 심플한 버전 → 점진적 개선 | Designer | Medium
지도 UI 성능 이슈 (웹) | D3.js 최적화 + React.memo + Sentry 성능 모니터링 | Frontend 팀 | Low
지역 데이터 부족 | Tier 시스템 + Kakao API + 크롤링 Fallback | Backend 팀 | Medium
웹 성능 최적화 | Next.js ISR + 이미지 최적화 + Sentry Web Vitals | Frontend 팀 | Medium
ECS 비용 초과 | Fargate Spot 사용 + Auto Scaling 최적화 + CloudWatch 비용 알람 | DevOps 팀 | Medium
Terraform State 관리 | S3 Backend + DynamoDB Lock | DevOps 팀 | High
모니터링 알람 피로 | 알람 임계값 튜닝 + Critical/Warning 분리 | DevOps 팀 | Medium
Sentry 할당량 초과 | 샘플링 레이트 조정 (Production: 10%) | Backend 팀 | Low
CloudWatch 로그 비용 | Log Retention 30일 + 불필요한 로그 필터링 | DevOps 팀 | Low
Kakao API Rate Limit | 300,000 req/day 모니터링 + 배치 작업 최적화 | Backend 팀 | Medium
Naver Blog API 할당량 | 25,000 req/day 준수 + Redis Rate Limiting + 캐싱 | Backend 팀 | Medium
크롤링 법적 리스크 | 법무팀 사전 검토 + robots.txt 준수 + 공개 정보만 수집 | Backend 팀 | High
블로그 리뷰 관련성 | Claude API로 AI 필터링 + 신뢰도 스코어링 | Backend 팀 | Low
에디터 큐레이션 지연 | 주 10곳 목표 + 외부 작가 고용 (필요시) | PM | Medium
초기 리뷰 데이터 부족 | 에디터 콘텐츠 우선 + 블로그 리뷰 보완 | PM | High
Langfuse 비용 초과 | 무료 플랜 한도 확인 + 샘플링 적용 | Backend 팀 | Low
```

---

## ✅ Approval (승인)

| 역할 | 이름 | 서명 | 날짜 |
|------|------|------|------|
| Product Manager | [PM 이름] | _______ | 2025-10-13 |
| Engineering Lead | [Tech Lead 이름] | _______ | 2025-10-13 |
| Design Lead | [Design Lead 이름] | _______ | 2025-10-13 |
| CEO | [CEO 이름] | _______ | 2025-10-13 |

---

**Document Version History**
```
v1.0 (2025-10-13): 초안 작성
v1.1 (2025-10-13): 지역 선택 UI 개선
   - 드롭다운 → 인터랙티브 지도 기반 비주얼 선택
   - 지역 Tier 시스템 도입 (필수/중요/선택)
   - region_metadata 테이블 추가
   - GET /api/v1/regions/metadata API 추가
   - 디자인 가이드라인 상세화
   - 개발 일정 및 리스크 레지스터 업데이트
v1.2 (2025-10-13): 플랫폼 전략 변경
   - React Native 앱 → Next.js + TypeScript 웹 우선
   - 모바일 웹 반응형 지원
   - 네이티브 앱은 PMF 검증 후 Phase 2로 연기
   - Tech Stack 업데이트: Vercel 배포, shadcn/ui, D3.js
   - SEO 최적화 추가
   - 리스크 레지스터에 웹 성능 최적화 항목 추가
v1.3 (2025-10-13): 인프라 전략 변경
   - EC2 → ECS Fargate 컨테이너 기반 배포
   - IaC: Terraform 도입
   - Container Registry: ECR
   - Secrets Management: AWS Secrets Manager
   - CI/CD: GitHub Actions → ECR → ECS
   - Auto Scaling 및 비용 최적화 전략 추가
   - Terraform State 관리 (S3 + DynamoDB)
   - DevOps 역할 추가 (팀 구성 변경)
   - 상세 인프라 비용 명세 추가
v1.4 (2025-10-13): 모니터링 시스템 추가
   - Monitoring: AWS CloudWatch + Sentry 도입
   - CloudWatch: 시스템 메트릭, 로그 집계, 알람 설정
   - Sentry: 에러 추적, APM, 트랜잭션 모니터링, Web Vitals
   - NFR-1 성능 요구사항에 상세 모니터링 스펙 추가
   - NFR-3 가용성에 알람 통합 전략 추가
   - Phase 0에 모니터링 셋업 작업 추가
   - CI/CD에 Sentry Release Tracking 자동화 추가
   - System Architecture Diagram에 Monitoring Layer 추가
   - 리스크 레지스터에 모니터링 관련 항목 추가 (알람 피로, 할당량, 로그 비용)
v1.5 (2025-10-13): 데이터 소스 전략 및 LLM Observability 추가
   - 데이터 소스 변경: 네이버 플레이스 → Kakao Local API (무료)
   - 데이터 수집 전략 3단계: Kakao API (1순위), 크롤링 (2순위), 수작업 (3순위)
   - Instagram Graph API 연동 추가 (Social Proof, 후기 데이터)
   - Database Schema 업데이트:
     * places 테이블: kakao_place_id, instagram_tag, data_source 컬럼 추가
     * instagram_posts 테이블 신규 추가
     * course_requests 테이블: langfuse_trace_id 컬럼 추가
   - Langfuse LLM Observability 도입:
     * AI 요청/응답 추적, 비용 분석, 프롬프트 버전 관리
     * NFR-1에 Langfuse 모니터링 스펙 추가
     * Phase 0에 Langfuse 셋업 작업 추가
   - PagerDuty 제거 (Slack 알람으로 통합)
   - System Architecture Diagram: External Data Sources 추가 (Kakao API, Instagram API)
   - 리스크 레지스터 업데이트:
     * Kakao API Rate Limit, Instagram API 할당량 관리
     * 크롤링 법적 리스크, Instagram 포스트 관련성
     * Langfuse 비용 관리
   - Phase 0에 Instagram 데이터 연동 작업 추가
v1.6 (2025-10-13): Instagram 제거 및 현실적 Social Proof 전략
   - Instagram Graph API 제거 (API 정책상 타인 콘텐츠 수집 불가):
     * Hashtag Search API Deprecated (2018년 이후)
     * 공개 게시물 크롤링 Meta 정책상 원천 차단
     * 본인 소유 비즈니스 계정만 접근 가능
     * Social Proof 전략 완전 재설계
   - 대안 1: 네이버 블로그 리뷰 API (자동 수집):
     * Naver Blog Search API 공식 연동 (무료, 일 25,000건)
     * 장소별 블로그 리뷰 수집 + Claude API 요약 (3줄)
     * 긍정/부정 감성 분석, 신뢰도 스코어링 (1~5)
     * blog_reviews 테이블 신규 추가
   - 대안 2: 사용자 생성 콘텐츠 (UGC, Phase 2):
     * 데이트 후 사진 업로드 + 한줄 리뷰
     * 별점 + 키워드 태그 (#분위기좋음, #가성비)
     * user_reviews 테이블 신규 추가
     * 리워드 시스템 (베스트 리뷰 선정)
   - 대안 3: 에디터 큐레이션 (수작업):
     * PM/디자이너 직접 방문 후 작성
     * Tier 1 지역 500곳 중 100곳 (20%) 목표
     * places 테이블: editor_review, editor_photos, is_editor_pick 컬럼 추가
   - Phase별 Social Proof 전략:
     * Phase 0-1 (MVP): 에디터 큐레이션 + 네이버 블로그 리뷰
     * Phase 2 (PMF 후): 사용자 리뷰 + 사진 업로드
     * Phase 3 (성장기): 완전한 UGC 플랫폼 구축
   - Database Schema 변경:
     * instagram_posts 테이블 삭제
     * blog_reviews 테이블 추가 (place_id, blog_url, ai_summary, trust_score)
     * user_reviews 테이블 추가 (user_id, place_id, rating, comment, photo_url)
     * places 테이블: blog_keywords, editor_review, editor_photos, is_editor_pick 추가
   - Phase 0 작업 업데이트:
     * Instagram 셋업 작업 제거
     * Naver Blog API 연동 추가
     * 에디터 큐레이션 콘텐츠 작성 추가 (주 10곳, 10주 완료)
   - 리스크 레지스터 업데이트:
     * Instagram 관련 리스크 제거
     * Naver Blog API 할당량 (25,000 req/day) 추가
     * 블로그 리뷰 관련성 (AI 필터링) 추가
     * 에디터 큐레이션 지연 리스크 추가
   - Tech Stack 업데이트:
     * Data Sources: Kakao Local API + Naver Blog Search API
     * UGC 플랫폼 준비 (Phase 2)
   - System Architecture Diagram 업데이트:
     * External Data Sources: Kakao Local API, Naver Blog API
     * Instagram API 제거
v1.7 (예정): 베타 테스트 피드백 반영
```

---

이 PRD는 실제 개발에 바로 투입 가능한 수준으로 작성되었습니다. 각 팀원이 이 문서만 보고도 "내가 뭘 만들어야 하는지" 명확하게 이해할 수 있도록 구체적인 API 스펙, DB 스키마, 기술 스택까지 포함했습니다.

**다음 액션:**
1. 이 PRD를 팀 전체가 리뷰 (30분)
2. 질문/이슈 사항 수집 후 최종 승인
3. Jira에 Epic/Story 생성 → 스프린트 시작! 🚀