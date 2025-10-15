import { create } from 'zustand';
import type { Region } from '@/types/region';

interface RegionStore {
  // State
  selectedRegion: Region | null;
  hoveredRegion: Region | null;
  regions: Region[];
  isLoading: boolean;
  error: string | null;

  // Actions
  setSelectedRegion: (region: Region | null) => void;
  setHoveredRegion: (region: Region | null) => void;
  setRegions: (regions: Region[]) => void;
  setIsLoading: (isLoading: boolean) => void;
  setError: (error: string | null) => void;
  resetSelection: () => void;
}

export const useRegionStore = create<RegionStore>(set => ({
  // Initial state
  selectedRegion: null,
  hoveredRegion: null,
  regions: [],
  isLoading: false,
  error: null,

  // Actions
  setSelectedRegion: region => set({ selectedRegion: region }),
  setHoveredRegion: region => set({ hoveredRegion: region }),
  setRegions: regions => set({ regions }),
  setIsLoading: isLoading => set({ isLoading }),
  setError: error => set({ error }),
  resetSelection: () => set({ selectedRegion: null, hoveredRegion: null, error: null }),
}));
