/**
 * 지역 정보 타입 정의
 */
export interface Region {
  id: string;
  display_name: string;
  emoji: string;
  coordinates: {
    latitude: number;
    longitude: number;
  };
  map_position: {
    x: number;
    y: number;
  };
  svg_path: string;
  popularity_score: number;
  available_places_count: number;
  keywords: string[];
  tier: 1 | 2 | 3; // 1=필수, 2=중요, 3=선택
  is_active: boolean;
}

/**
 * 지역 메타데이터 API 응답
 */
export interface RegionMetadataResponse {
  regions: Region[];
}

/**
 * 지역 선택 상태
 */
export interface RegionSelectionState {
  selectedRegion: Region | null;
  hoveredRegion: Region | null;
  regions: Region[];
  isLoading: boolean;
  error: string | null;
}
