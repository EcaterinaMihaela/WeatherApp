package com.example.weatherapp.controller;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.service.IpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.weatherapp.dto.WeatherDto;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final WeatherService weatherService;
    private final IpService ipService;

    @GetMapping("/")
    public String home(Model model) {
        // obținem IP-ul clientului
        String clientIp = ipService.getClientIp(); // dacă modifici metoda să primească HttpServletRequest
        // obținem locația pe baza IP-ului
        String location = ipService.getLocationFromIp(clientIp);

        // preluăm datele meteo
        WeatherDto weather = weatherService.getWeather(location);

        // adaugăm prognoza pe ore și zile
        weatherService.enrichWeatherWithForecast(weather);

        // adăugăm la model pentru Thymeleaf
        model.addAttribute("weather", weather);

        return "index"; // templates/index.html
    }



    @PostMapping("/search")
    public String search(@RequestParam("location") String location, Model model) {
        // preluăm datele meteo pentru locația introdusă de client
        WeatherDto weather = weatherService.getWeather(location);

        // adaugăm prognoza pe ore și zile
        weatherService.enrichWeatherWithForecast(weather);

        model.addAttribute("weather", weather);
        return "index";
    }
}
