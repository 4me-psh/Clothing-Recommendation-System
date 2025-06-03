package org.example.clothingrecommendationsystem.model.weather;

public interface IWeatherHandler {
    Weather getByCity(String city);
    Weather getByCoordinates(String coordinates);
}
