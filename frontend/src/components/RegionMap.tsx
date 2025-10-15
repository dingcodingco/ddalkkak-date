'use client';

import { useEffect, useRef } from 'react';
import * as d3 from 'd3';
import { useRegionStore } from '@/stores/regionStore';
import { fetchRegionMetadata } from '@/lib/api/regions';
import type { Region } from '@/types/region';

interface RegionMapProps {
  className?: string;
}

export function RegionMap({ className = '' }: RegionMapProps) {
  const svgRef = useRef<SVGSVGElement>(null);
  const {
    regions,
    selectedRegion,
    hoveredRegion,
    isLoading,
    error,
    setRegions,
    setSelectedRegion,
    setHoveredRegion,
    setIsLoading,
    setError,
  } = useRegionStore();

  // 지역 데이터 로드
  useEffect(() => {
    const loadRegions = async () => {
      setIsLoading(true);
      setError(null);

      try {
        const data = await fetchRegionMetadata();
        setRegions(data.regions);
      } catch (err) {
        setError(err instanceof Error ? err.message : '지역 정보를 불러올 수 없습니다.');
      } finally {
        setIsLoading(false);
      }
    };

    loadRegions();
  }, [setRegions, setIsLoading, setError]);

  // SVG 맵 렌더링 및 인터랙션
  useEffect(() => {
    if (!svgRef.current || regions.length === 0) return;

    const svg = d3.select(svgRef.current);
    svg.selectAll('*').remove(); // 기존 요소 제거

    // SVG 그룹 생성
    const g = svg.append('g').attr('class', 'regions-group');

    // 각 지역 경로 생성
    regions.forEach(region => {
      const path = g
        .append('path')
        .attr('d', region.svg_path)
        .attr('class', 'region-path')
        .attr('data-region-id', region.id)
        .style('fill', '#FFFBF0') // 기본 배경색
        .style('stroke', '#2D3436')
        .style('stroke-width', '2')
        .style('cursor', 'pointer')
        .style('transition', 'all 0.3s ease')
        .style('opacity', selectedRegion && selectedRegion.id !== region.id ? '0.3' : '1');

      // 호버 효과
      path
        .on('mouseenter', function (event) {
          if (window.innerWidth >= 768) {
            // 데스크톱만 호버 지원
            setHoveredRegion(region);
            d3.select(this)
              .style('fill', '#FFE5E5') // 호버 색상
              .style('opacity', '1');
          }
        })
        .on('mouseleave', function () {
          if (window.innerWidth >= 768) {
            setHoveredRegion(null);
            d3.select(this)
              .style('fill', '#FFFBF0')
              .style('opacity', selectedRegion && selectedRegion.id !== region.id ? '0.3' : '1');
          }
        })
        .on('click', function (event) {
          event.stopPropagation();
          handleRegionSelect(region);
        });

      // 지역 라벨 추가 (이모지 + 이름)
      g.append('text')
        .attr('x', region.map_position.x)
        .attr('y', region.map_position.y - 10)
        .attr('text-anchor', 'middle')
        .attr('class', 'region-emoji')
        .text(region.emoji)
        .style('font-size', '24px')
        .style('pointer-events', 'none')
        .style('user-select', 'none');

      g.append('text')
        .attr('x', region.map_position.x)
        .attr('y', region.map_position.y + 20)
        .attr('text-anchor', 'middle')
        .attr('class', 'region-label')
        .text(region.display_name)
        .style('font-size', '12px')
        .style('font-weight', '600')
        .style('fill', '#2D3436')
        .style('pointer-events', 'none')
        .style('user-select', 'none');
    });

    // 선택 상태 업데이트
    if (selectedRegion) {
      g.selectAll(`path[data-region-id="${selectedRegion.id}"]`)
        .style('fill', '#FF6B6B') // 선택 색상
        .style('opacity', '1')
        .style('stroke-width', '3');
    }
  }, [regions, selectedRegion, hoveredRegion, setHoveredRegion]);

  // 지역 선택 핸들러
  const handleRegionSelect = (region: Region) => {
    if (selectedRegion?.id === region.id) {
      setSelectedRegion(null); // 같은 지역 재클릭 시 선택 해제
    } else {
      setSelectedRegion(region);
    }
  };

  // 로딩 상태
  if (isLoading) {
    return (
      <div className={`flex items-center justify-center min-h-[400px] ${className}`}>
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-[#FF6B6B] mx-auto mb-4"></div>
          <p className="text-gray-600">지역 정보를 불러오는 중...</p>
        </div>
      </div>
    );
  }

  // 에러 상태
  if (error) {
    return (
      <div className={`flex items-center justify-center min-h-[400px] ${className}`}>
        <div className="text-center">
          <p className="text-red-600 mb-4">{error}</p>
          <button
            onClick={() => window.location.reload()}
            className="px-4 py-2 bg-[#FF6B6B] text-white rounded-lg hover:bg-[#ff5252] transition"
          >
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={`region-map-container ${className}`}>
      <svg
        ref={svgRef}
        className="w-full h-full"
        viewBox="0 0 800 600"
        preserveAspectRatio="xMidYMid meet"
        style={{ maxHeight: '600px' }}
      >
        {/* D3가 여기에 지역 맵을 렌더링합니다 */}
      </svg>
    </div>
  );
}
