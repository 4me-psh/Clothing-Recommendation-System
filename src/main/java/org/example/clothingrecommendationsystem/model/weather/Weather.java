package org.example.clothingrecommendationsystem.model.weather;

import lombok.Data;

@Data
public class Weather {
    private Long id;
    private float temperature;
    private float feelsLikeTemperature;
    private float speedOfWind;
    private float humidity;
    private String weatherCondition;
}
