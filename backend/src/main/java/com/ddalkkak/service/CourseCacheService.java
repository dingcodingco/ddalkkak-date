package com.ddalkkak.service;

import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;

@Slf4j
@Service
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true", matchIfMissing = false)
public class CourseCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public CourseCacheService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
    private static final String CACHE_PREFIX = "course:";
    private static final Duration CACHE_TTL = Duration.ofHours(24);

    public CourseGenerationResponse getFromCache(CourseGenerationRequest request) {
        String cacheKey = generateCacheKey(request);
        Object cached = redisTemplate.opsForValue().get(cacheKey);

        if (cached != null) {
            log.info("Cache hit for key: {}", cacheKey);
            // Convert LinkedHashMap to CourseGenerationResponse
            try {
                return objectMapper.convertValue(cached, CourseGenerationResponse.class);
            } catch (Exception e) {
                log.error("Failed to convert cached value", e);
                return null;
            }
        }

        log.info("Cache miss for key: {}", cacheKey);
        return null;
    }

    public void saveToCache(CourseGenerationRequest request, CourseGenerationResponse response) {
        String cacheKey = generateCacheKey(request);
        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL);
        log.info("Saved to cache with key: {}, TTL: {}", cacheKey, CACHE_TTL);
    }

    private String generateCacheKey(CourseGenerationRequest request) {
        String data = request.getRegion() + request.getDateType() + request.getBudget();
        String hash = md5Hash(data);
        return CACHE_PREFIX + hash;
    }

    private String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 algorithm not found", e);
            throw new RuntimeException("MD5 해싱 실패", e);
        }
    }

    public void evictCache(CourseGenerationRequest request) {
        String cacheKey = generateCacheKey(request);
        redisTemplate.delete(cacheKey);
        log.info("Evicted cache for key: {}", cacheKey);
    }
}
