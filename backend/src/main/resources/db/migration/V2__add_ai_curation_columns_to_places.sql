-- Migration: Add AI curation columns to places table
-- Description: Add columns for Claude API analysis results (date_score, mood_tags, price_range, best_time, recommendation)
-- Author: AI Native Developer
-- Date: 2025-10-15

-- Add AI curation columns
ALTER TABLE places ADD COLUMN IF NOT EXISTS date_score INTEGER;
ALTER TABLE places ADD COLUMN IF NOT EXISTS mood_tags TEXT[];
ALTER TABLE places ADD COLUMN IF NOT EXISTS price_range VARCHAR(10);
ALTER TABLE places ADD COLUMN IF NOT EXISTS best_time VARCHAR(20);
ALTER TABLE places ADD COLUMN IF NOT EXISTS recommendation TEXT;
ALTER TABLE places ADD COLUMN IF NOT EXISTS curated_at TIMESTAMP;

-- Add comments for documentation
COMMENT ON COLUMN places.date_score IS 'AI-generated date suitability score (1-10)';
COMMENT ON COLUMN places.mood_tags IS 'AI-generated mood hashtags (max 3)';
COMMENT ON COLUMN places.price_range IS 'AI-estimated price range (₩, ₩₩, ₩₩₩)';
COMMENT ON COLUMN places.best_time IS 'AI-recommended time slot (아침/점심/저녁/야간)';
COMMENT ON COLUMN places.recommendation IS 'AI-generated one-line recommendation (max 50 chars)';
COMMENT ON COLUMN places.curated_at IS 'Timestamp when AI curation was performed';

-- Add index for filtering by date_score
CREATE INDEX IF NOT EXISTS idx_places_date_score ON places(date_score);

-- Add index for filtering by mood_tags (GIN index for array)
CREATE INDEX IF NOT EXISTS idx_places_mood_tags ON places USING GIN(mood_tags);
