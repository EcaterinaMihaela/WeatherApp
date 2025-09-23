package com.example.weatherapp.controller;

import com.example.weatherapp.dto.WeatherDto;
import com.example.weatherapp.service.IpService;
import com.example.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

    private final IpService ipService;
    private final WeatherService weatherService;

    @GetMapping("/api/weather")
    public WeatherDto getWeather() {
        log.info("Received request for /api/weather");

        String clientIp = ipService.getClientIp();
        String location = ipService.getLocationFromIp(clientIp);

        // aici apelăm serviciul de weather cu locația
        WeatherDto weather = weatherService.getWeather(location);
        weatherService.enrichWeatherWithForecast(weather); // adaugă prognoza

        // setăm și IP-ul + mesajul
        weather.setClientIp(clientIp);
        weather.setGreeting("Hello from Spring Boot!");

        log.info("Returning weather data for {}", location);

        return weather;
    }


}
