'use client';

import { useRegionStore } from '@/stores/regionStore';
import type { Region } from '@/types/region';

interface RegionQuickSelectProps {
  className?: string;
}

export function RegionQuickSelect({ className = '' }: RegionQuickSelectProps) {
  const { regions, selectedRegion, setSelectedRegion } = useRegionStore();

  // Tier 1 지역만 빠른 선택에 표시
  const tier1Regions = regions.filter(region => region.tier === 1 && region.is_active);

  const handleQuickSelect = (region: Region) => {
    if (selectedRegion?.id === region.id) {
      setSelectedRegion(null);
    } else {
      setSelectedRegion(region);
    }
  };

  return (
    <div className={`region-quick-select ${className}`}>
      <h3 className="text-lg font-semibold text-gray-800 mb-3">인기 지역 빠른 선택</h3>
      <div className="grid grid-cols-3 sm:grid-cols-4 md:grid-cols-5 gap-3">
        {tier1Regions.map(region => (
          <button
            key={region.id}
            onClick={() => handleQuickSelect(region)}
            className={`
              flex flex-col items-center justify-center
              p-4 rounded-xl border-2 transition-all
              hover:scale-105 active:scale-95
              ${
                selectedRegion?.id === region.id
                  ? 'border-[#FF6B6B] bg-[#FFE5E5] shadow-lg'
                  : 'border-gray-200 bg-white hover:border-[#FF6B6B] hover:bg-[#FFF5F5]'
              }
            `}
            aria-label={`${region.display_name} 선택`}
          >
            <span className="text-3xl mb-1">{region.emoji}</span>
            <span className="text-sm font-medium text-gray-800">{region.display_name}</span>
          </button>
        ))}
      </div>
    </div>
  );
}
