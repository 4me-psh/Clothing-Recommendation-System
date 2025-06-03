package org.example.clothingrecommendationsystem.infrastructure.external.weather;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.example.clothingrecommendationsystem.model.weather.Weather;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WeatherCacheService {

    private final Cache<Long, Weather> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(15))
            .maximumSize(5000)
            .build();

    public Weather get(Long userId) {
        return cache.getIfPresent(userId);
    }

    public void put(Long userId, Weather weather) {
        cache.put(userId, weather);
        System.out.println(cache.asMap());
    }

    public void clearUser(Long userId) {
        cache.invalidate(userId);
    }

    public void clearAll() {
        cache.invalidateAll();
    }
}
