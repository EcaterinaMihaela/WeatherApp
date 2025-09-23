package com.example.weatherapp.service;

import com.example.weatherapp.dto.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    // cheia va fi injectată din application.properties
    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherDto getWeather(String location) {
        String url = String.format(
                "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                location, apiKey
        );

        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("Response from OpenWeatherMap: {}", response);

            JSONObject json = new JSONObject(response);

            WeatherDto dto = new WeatherDto();
            dto.setLocation(location);
            dto.setTemperature(json.getJSONObject("main").getDouble("temp"));
            dto.setDescription(json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description"));
            dto.setHumidity(json.getJSONObject("main").getInt("humidity"));
            dto.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));
            dto.setIcon(json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon"));

            return dto;

        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage());
            WeatherDto dto = new WeatherDto();
            dto.setLocation(location);
            dto.setDescription("Weather data not available");
            dto.setTemperature(0.0);
            dto.setHumidity(0);
            dto.setWindSpeed(0.0);
            dto.setIcon(null);
            return dto;
        }
    }


    public void enrichWeatherWithForecast(WeatherDto dto) {
        // Folosim OpenWeatherMap 5-day/3-hour forecast API
        String forecastUrl = String.format(
                "http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                dto.getLocation(), apiKey
        );

        try {
            JSONObject response = new JSONObject(restTemplate.getForObject(forecastUrl, String.class));
            JSONArray list = response.getJSONArray("list");

            List<WeatherDto.HourlyForecast> hourly = new ArrayList<>();
            for (int i = 0; i < 8; i++) { // primele 8 intervale de 3 ore (aprox 24h)
                JSONObject item = list.getJSONObject(i);
                WeatherDto.HourlyForecast hf = new WeatherDto.HourlyForecast();
                hf.setTime(item.getString("dt_txt"));
                hf.setTemp(item.getJSONObject("main").getDouble("temp"));
                hf.setPop(item.has("pop") ? item.getDouble("pop") * 100 : 0);
                hf.setDescription(item.getJSONArray("weather").getJSONObject(0).getString("description"));
                hf.setIcon(item.getJSONArray("weather").getJSONObject(0).getString("icon")); // ✅ aici adaugi icon
                hourly.add(hf);
            }
            dto.setHourlyForecast(hourly);

            // Prognoza pe zile (min/max) poate fi extrasă prin gruparea elementelor după zi
            Map<String, WeatherDto.DailyForecast> dailyMap = new HashMap<>();
            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                String date = item.getString("dt_txt").substring(0, 10); // yyyy-MM-dd
                double temp = item.getJSONObject("main").getDouble("temp");
                double pop = item.has("pop") ? item.getDouble("pop") * 100 : 0;
                String desc = item.getJSONArray("weather").getJSONObject(0).getString("description");


                String icon = item.getJSONArray("weather").getJSONObject(0).getString("icon");

                dailyMap.compute(date, (k, v) -> {
                    if (v == null) {
                        WeatherDto.DailyForecast df = new WeatherDto.DailyForecast();
                        df.setDate(k);
                        df.setTempMin(temp);
                        df.setTempMax(temp);
                        df.setPop(pop);
                        df.setDescription(desc);
                        df.setIcon(icon);
                        return df;
                    } else {
                        v.setTempMin(Math.min(v.getTempMin(), temp));
                        v.setTempMax(Math.max(v.getTempMax(), temp));
                        v.setIcon(icon);
                        return v;
                    }
                });
            }
            dto.setDailyForecast(new ArrayList<>(dailyMap.values()));

        } catch (Exception e) {
            log.error("Could not fetch forecast data: {}", e.getMessage());
        }
    }

}
