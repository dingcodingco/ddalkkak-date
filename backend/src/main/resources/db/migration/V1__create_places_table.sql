-- V1: Create places table with basic Kakao API fields
-- Migration for initial place data structure

CREATE TABLE IF NOT EXISTS places (
    id BIGSERIAL PRIMARY KEY,

    -- Kakao API Basic Info
    name VARCHAR(255) NOT NULL,
    kakao_place_id VARCHAR(255) UNIQUE NOT NULL,
    address_name VARCHAR(500),
    road_address_name VARCHAR(500),
    category_name VARCHAR(255),
    category_group_code VARCHAR(10),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    place_url VARCHAR(500),
    phone VARCHAR(50),

    -- Region Classification
    region VARCHAR(50) NOT NULL,

    -- Metadata
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_places_region ON places(region);
CREATE INDEX IF NOT EXISTS idx_places_category_group ON places(category_group_code);
CREATE INDEX IF NOT EXISTS idx_places_location ON places(latitude, longitude);
CREATE INDEX IF NOT EXISTS idx_places_kakao_id ON places(kakao_place_id);

-- Comments for documentation
COMMENT ON TABLE places IS 'Places collected from Kakao Local API';
COMMENT ON COLUMN places.kakao_place_id IS 'Unique identifier from Kakao Local API';
COMMENT ON COLUMN places.region IS 'Tier 1 region: 홍대, 강남, 성수, 연남, 이태원';
