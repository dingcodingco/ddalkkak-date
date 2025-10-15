import type { RegionMetadataResponse } from '@/types/region';

// Use Next.js API route for testing, fallback to backend API
const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || '/api/v1';

/**
 * 지역 메타데이터 조회 API
 * @returns 지역 정보 배열
 */
export async function fetchRegionMetadata(): Promise<RegionMetadataResponse> {
  try {
    const response = await fetch(`${API_BASE_URL}/regions/metadata`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      cache: 'force-cache', // 지역 데이터는 변경이 적으므로 캐싱
    });

    if (!response.ok) {
      throw new Error(`API 요청 실패: ${response.status} ${response.statusText}`);
    }

    const data: RegionMetadataResponse = await response.json();
    return data;
  } catch (error) {
    console.error('지역 메타데이터 조회 실패:', error);
    throw new Error(error instanceof Error ? error.message : '지역 정보를 불러올 수 없습니다.');
  }
}
