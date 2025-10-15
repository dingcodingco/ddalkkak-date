package com.ddalkkak.repository;

import com.ddalkkak.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Place Repository
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    /**
     * 카카오 플레이스 ID로 장소 조회
     */
    Optional<Place> findByKakaoPlaceId(String kakaoPlaceId);

    /**
     * 지역별 장소 조회
     */
    List<Place> findByRegion(String region);

    /**
     * AI 큐레이션이 완료되지 않은 장소 조회
     */
    @Query("SELECT p FROM Place p WHERE p.curatedAt IS NULL")
    List<Place> findUncuratedPlaces();

    /**
     * 데이트 점수 범위로 장소 조회
     */
    @Query("SELECT p FROM Place p WHERE p.dateScore >= :minScore AND p.region = :region ORDER BY p.dateScore DESC")
    List<Place> findByRegionAndMinDateScore(@Param("region") String region, @Param("minScore") Integer minScore);

    /**
     * 특정 지역의 장소 수 조회
     */
    @Query("SELECT COUNT(p) FROM Place p WHERE p.region = :region")
    Long countByRegion(@Param("region") String region);
}
