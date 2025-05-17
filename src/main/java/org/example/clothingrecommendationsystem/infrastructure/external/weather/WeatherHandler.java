package org.example.clothingrecommendationsystem.infrastructure.external.weather;

import org.example.clothingrecommendationsystem.model.weather.IWeatherHandler;
import org.example.clothingrecommendationsystem.model.weather.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherHandler implements IWeatherHandler {

    @Value("${external.weather-api-key}")
    private String weatherApiKey;

    @Value("${external.weather-api-url}")
    private String weatherApiUrl;

    @Override
    public Weather getByCity(String city) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> result = new HashMap<>();

        String url = weatherApiUrl + "/current.json";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of("key", weatherApiKey, "q", city);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return null;
    }
}
