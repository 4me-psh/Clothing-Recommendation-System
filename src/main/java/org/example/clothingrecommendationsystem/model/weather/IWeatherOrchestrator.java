package org.example.clothingrecommendationsystem.model.weather;

public interface IWeatherOrchestrator {
    Weather getByCity(String city);
}
