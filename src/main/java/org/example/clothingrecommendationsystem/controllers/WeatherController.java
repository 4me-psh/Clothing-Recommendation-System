package org.example.clothingrecommendationsystem.controllers;

import org.example.clothingrecommendationsystem.model.weather.IWeatherOrchestrator;
import org.example.clothingrecommendationsystem.orchestrators.weather.dto.WeatherDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final IWeatherOrchestrator weatherOrchestrator;
    private final ModelMapper modelMapper;

    public WeatherController(IWeatherOrchestrator weatherOrchestrator,
                             @Qualifier("orchestratorModelMapper") ModelMapper modelMapper) {
        this.weatherOrchestrator = weatherOrchestrator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<WeatherDto> getWeatherByCity(@PathVariable String city) {
        WeatherDto response = modelMapper.map(weatherOrchestrator.getByCity(city), WeatherDto.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/coordinates/{coordinates}")
    public ResponseEntity<WeatherDto> getWeatherByCoordinates(@PathVariable String coordinates) {
        WeatherDto response = modelMapper.map(weatherOrchestrator.getByCoordinates(coordinates), WeatherDto.class);
        return ResponseEntity.ok(response);
    }

}


