package com.example.weatherapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

@Service
public class IpService {

    private final HttpServletRequest request;
    private final RestTemplate restTemplate = new RestTemplate();

    public IpService(HttpServletRequest request) {
        this.request = request;
    }

    public String getClientIp() {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) ip = "127.0.0.1"; // IPv4 localhost
        return ip;
    }

    public String getLocationFromIp(String ip) {
        try {
            String url = String.format("http://ip-api.com/json/%s", ip);
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(response);

            if ("success".equalsIgnoreCase(json.getString("status"))) {
                String city = json.getString("city");
                String countryCode = json.getString("countryCode"); // ex: RO
                return city + "," + countryCode;
            } else {
                return "Bucharest,RO"; // fallback
            }
        } catch (Exception e) {
            return "Bucharest,RO"; // fallback în caz de eroare
        }
    }

}
