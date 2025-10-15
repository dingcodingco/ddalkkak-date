'use client';

import { RegionMap } from './RegionMap';
import { RegionQuickSelect } from './RegionQuickSelect';
import { RegionInfoCard } from './RegionInfoCard';

interface RegionSelectorProps {
  className?: string;
}

/**
 * 지역 선택 인터랙티브 맵 메인 컴포넌트
 * - SVG 기반 지역 맵
 * - 빠른 선택 버튼
 * - 선택된 지역 정보 카드
 */
export function RegionSelector({ className = '' }: RegionSelectorProps) {
  return (
    <div className={`region-selector ${className}`}>
      <div className="container mx-auto px-4 py-8">
        {/* 제목 */}
        <div className="text-center mb-8">
          <h1 className="text-3xl md:text-4xl font-bold text-gray-900 mb-2">
            어디로 데이트 갈까요? 🗺️
          </h1>
          <p className="text-gray-600">지도에서 원하는 지역을 클릭하거나 아래 버튼을 눌러보세요</p>
        </div>

        {/* 지역 맵 */}
        <div className="mb-8">
          <RegionMap className="bg-white rounded-2xl shadow-lg p-6" />
        </div>

        {/* 빠른 선택 */}
        <div className="mb-8">
          <RegionQuickSelect />
        </div>

        {/* 선택된 지역 정보 */}
        <div className="flex justify-center">
          <RegionInfoCard className="w-full md:w-2/3 lg:w-1/2" />
        </div>
      </div>
    </div>
  );
}
