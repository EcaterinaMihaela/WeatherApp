package com.example.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDto {
    private String client_ip;
    private String location;
    private String greeting;
    private double temperature;
    private String description;
    private int humidity;
    private double windSpeed;
    private String icon;

    // Prognoza pe ore
    private List<HourlyForecast> hourlyForecast;
    @Data
    public static class HourlyForecast {
        private String time; // sau long timestamp
        private double temp;
        private double pop; // % șanse ploaie
        private String description;
        private String icon;
    }

    // Prognoza pe zile
    private List<DailyForecast> dailyForecast;
    @Data
    public static class DailyForecast {
        private String date; // sau long timestamp
        private double tempMin;
        private double tempMax;
        private double pop;
        private String description;
        private String icon;
    }

    public void setClientIp(String client_ip) {
        this.client_ip = client_ip;
    }
}
