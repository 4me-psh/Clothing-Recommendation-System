package org.example.clothingrecommendationsystem.orchestrators.weather;

import org.example.clothingrecommendationsystem.model.weather.IWeatherHandler;
import org.example.clothingrecommendationsystem.model.weather.IWeatherOrchestrator;
import org.example.clothingrecommendationsystem.model.weather.Weather;
import org.springframework.stereotype.Service;

@Service
public class WeatherOrchestrator implements IWeatherOrchestrator {
    private final IWeatherHandler weatherHandler;

    public WeatherOrchestrator(IWeatherHandler weatherHandler) {
        this.weatherHandler = weatherHandler;
    }

    @Override
    public Weather getByCity(String city) {
        return weatherHandler.getByCity(city);
    }
}
