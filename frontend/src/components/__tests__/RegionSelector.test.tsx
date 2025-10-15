import { render, screen, waitFor } from '@testing-library/react';
import { RegionSelector } from '../RegionSelector';
import { fetchRegionMetadata } from '@/lib/api/regions';
import type { Region } from '@/types/region';

// Mock API
jest.mock('@/lib/api/regions');
const mockFetchRegionMetadata = fetchRegionMetadata as jest.MockedFunction<
  typeof fetchRegionMetadata
>;

// Mock 데이터
const mockRegions: Region[] = [
  {
    id: 'hongdae',
    display_name: '홍대',
    emoji: '🎨',
    coordinates: { latitude: 37.5563, longitude: 126.9239 },
    map_position: { x: 100, y: 100 },
    svg_path: 'M100,100 L200,100 L200,200 L100,200 Z',
    popularity_score: 95,
    available_places_count: 150,
    keywords: ['힙한', '예술', '클럽'],
    tier: 1,
    is_active: true,
  },
  {
    id: 'gangnam',
    display_name: '강남',
    emoji: '🏙️',
    coordinates: { latitude: 37.4979, longitude: 127.0276 },
    map_position: { x: 300, y: 300 },
    svg_path: 'M300,300 L400,300 L400,400 L300,400 Z',
    popularity_score: 90,
    available_places_count: 200,
    keywords: ['럭셔리', '쇼핑', '맛집'],
    tier: 1,
    is_active: true,
  },
];

describe('RegionSelector', () => {
  beforeEach(() => {
    mockFetchRegionMetadata.mockResolvedValue({ regions: mockRegions });
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('제목과 설명이 표시되어야 한다', () => {
    render(<RegionSelector />);

    expect(screen.getByText(/어디로 데이트 갈까요\?/)).toBeInTheDocument();
    expect(
      screen.getByText(/지도에서 원하는 지역을 클릭하거나 아래 버튼을 눌러보세요/)
    ).toBeInTheDocument();
  });

  it('지역 데이터를 로드해야 한다', async () => {
    render(<RegionSelector />);

    await waitFor(() => {
      expect(mockFetchRegionMetadata).toHaveBeenCalledTimes(1);
    });
  });

  it('로딩 중일 때 로딩 메시지를 표시해야 한다', () => {
    mockFetchRegionMetadata.mockImplementation(
      () => new Promise(resolve => setTimeout(() => resolve({ regions: mockRegions }), 1000))
    );

    render(<RegionSelector />);

    expect(screen.getByText(/지역 정보를 불러오는 중\.\.\./)).toBeInTheDocument();
  });

  it('에러 발생 시 에러 메시지를 표시해야 한다', async () => {
    mockFetchRegionMetadata.mockRejectedValue(new Error('지역 정보를 불러올 수 없습니다.'));

    render(<RegionSelector />);

    await waitFor(() => {
      expect(screen.getByText(/지역 정보를 불러올 수 없습니다\./)).toBeInTheDocument();
    });
  });
});
