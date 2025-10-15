'use client';

import { useRegionStore } from '@/stores/regionStore';

interface RegionInfoCardProps {
  className?: string;
}

export function RegionInfoCard({ className = '' }: RegionInfoCardProps) {
  const { selectedRegion } = useRegionStore();

  if (!selectedRegion) {
    return null;
  }

  return (
    <div
      className={`region-info-card bg-white rounded-2xl shadow-lg p-6 border-2 border-[#FF6B6B] ${className}`}
      role="region"
      aria-live="polite"
      aria-label="선택된 지역 정보"
    >
      {/* 헤더 */}
      <div className="flex items-center gap-3 mb-4">
        <span className="text-5xl">{selectedRegion.emoji}</span>
        <div>
          <h2 className="text-2xl font-bold text-gray-900">{selectedRegion.display_name}</h2>
          <p className="text-sm text-gray-500">인기도: {selectedRegion.popularity_score}점</p>
        </div>
      </div>

      {/* 지역 통계 */}
      <div className="grid grid-cols-2 gap-4 mb-4">
        <div className="bg-[#FFF5F5] rounded-lg p-3">
          <p className="text-xs text-gray-600 mb-1">이용 가능한 장소</p>
          <p className="text-lg font-semibold text-[#FF6B6B]">
            {selectedRegion.available_places_count}곳
          </p>
        </div>
        <div className="bg-[#FFF5F5] rounded-lg p-3">
          <p className="text-xs text-gray-600 mb-1">지역 등급</p>
          <p className="text-lg font-semibold text-[#FF6B6B]">Tier {selectedRegion.tier}</p>
        </div>
      </div>

      {/* 키워드 */}
      <div className="mb-4">
        <h3 className="text-sm font-semibold text-gray-700 mb-2">이 지역의 특징</h3>
        <div className="flex flex-wrap gap-2">
          {selectedRegion.keywords.map((keyword, index) => (
            <span
              key={index}
              className="px-3 py-1 bg-[#FFE5E5] text-[#FF6B6B] rounded-full text-sm font-medium"
            >
              #{keyword}
            </span>
          ))}
        </div>
      </div>

      {/* 위치 정보 */}
      <div className="text-xs text-gray-500">
        <p>
          좌표: {selectedRegion.coordinates.latitude.toFixed(4)},{' '}
          {selectedRegion.coordinates.longitude.toFixed(4)}
        </p>
      </div>
    </div>
  );
}
