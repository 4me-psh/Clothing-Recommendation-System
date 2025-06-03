package org.example.clothingrecommendationsystem.orchestrators.weather.dto;

import lombok.Data;

@Data
public class WeatherDto {
    private String city;
    private float temperature;
    private float feelsLikeTemperature;
    private float speedOfWind;
    private float humidity;
    private String weatherCondition;
    private String conditionIcon;
}
