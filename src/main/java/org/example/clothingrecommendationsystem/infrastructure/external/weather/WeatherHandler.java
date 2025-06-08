package org.example.clothingrecommendationsystem.infrastructure.external.weather;

import org.example.clothingrecommendationsystem.model.user.IUserOrchestrator;
import org.example.clothingrecommendationsystem.model.user.IUserRepository;
import org.example.clothingrecommendationsystem.model.user.User;
import org.example.clothingrecommendationsystem.model.weather.IWeatherHandler;
import org.example.clothingrecommendationsystem.model.weather.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherHandler implements IWeatherHandler {

    private final WeatherCacheService weatherCacheService;
    private final IUserOrchestrator userOrchestrator;

    private final RestTemplate restTemplate;

    @Value("${external.weather-api-key}")
    private String weatherApiKey;

    @Value("${external.weather-api-url}")
    private String weatherApiUrl;

    public WeatherHandler(WeatherCacheService weatherCacheService, IUserOrchestrator userOrchestrator, RestTemplate restTemplate) {
        this.weatherCacheService = weatherCacheService;
        this.userOrchestrator = userOrchestrator;
        this.restTemplate = restTemplate;
    }

    @Override
    public Weather getByCity(String city) {
        String url = String.format("%s/current.json?key=%s&q=%s", weatherApiUrl, weatherApiKey, city);

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map body = response.getBody();

        if (body == null || !body.containsKey("current")) {
            throw new RuntimeException("No weather data found for city: " + city);
        }

        return getWeather(body);
    }

    private Weather getWeather(Map body) {
        Map current = (Map) body.get("current");
        Map condition = (Map) current.get("condition");
        Map location = (Map) body.get("location");

        Weather weather = new Weather();
        weather.setCity(location.get("name").toString());
        weather.setTemperature(Float.parseFloat(current.get("temp_c").toString()));
        weather.setFeelsLikeTemperature(Float.parseFloat(current.get("feelslike_c").toString()));
        weather.setSpeedOfWind(Float.parseFloat(current.get("wind_kph").toString()));
        weather.setHumidity(Float.parseFloat(current.get("humidity").toString()));
        weather.setWeatherCondition(condition != null ? condition.get("text").toString() : "Невідомо");
        assert condition != null;
        weather.setConditionIcon(condition.get("icon").toString());

        return weather;
    }

    @Override
    public Weather getByCoordinates(String coordinates) {

        if (coordinates == null || !coordinates.matches("-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?")) {
            throw new IllegalArgumentException("Invalid coordinates format. Expected format: 'latitude,longitude'");
        }

        String url = String.format("%s/current.json?key=%s&q=%s", weatherApiUrl, weatherApiKey, coordinates);

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map body = response.getBody();

        if (body == null || !body.containsKey("current")) {
            throw new RuntimeException("No weather data found for coordinates: " + coordinates);
        }

        System.out.println(body);

        Weather weather = getWeather(body);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        String email = "";

        if (principal instanceof User user) {
            email = user.getEmail();
        }

        User user = userOrchestrator.getByEmail(email);

        Long userId = user.getId();

        weatherCacheService.put(userId, weather);

        return weather;
    }

}
