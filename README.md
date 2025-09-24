[WeatherApp.pdf](https://github.com/user-attachments/files/22523270/WeatherApp.pdf)


# 🌦️ WeatherApp

## General Overview
WeatherApp is a **Java-based web application** that provides **real-time weather information**.  
It can automatically detect the user's location using **IP geolocation** or allow users to manually search for any location.

The app displays:

- Current weather (temperature, humidity, wind speed, weather description, and icon)
- Hourly forecast (next 24 hours, 3-hour intervals)
- Daily forecast (min/max temperature, probability of rain)
- Weather icons for visual representation
- Search bar for manual location input

The application is built with **Spring Boot**, **Thymeleaf**, and **Maven**, and deployed using **Docker** on **Render**.

---

## Accessing the App

### Local
After running the app locally, you can access it at:

http://localhost:8080

### Online (Deployed via Render)
Once deployed with Docker on Render, the app can be accessed at (replace with your actual Render URL):
https://weatherapp-by-ecaterina.onrender.com


---

## Technologies Used

### Backend: Spring Boot
- Handles REST requests and MVC controllers.
- Provides structured architecture for services and controllers, JSON parsing, and API integration.
- **Why Spring Boot**: Fast development, embedded server, REST support, integration with Thymeleaf.

### Frontend: Thymeleaf
- Dynamically binds backend data to HTML templates.
- Allows loops, conditions, and dynamic attributes for rendering weather and icons.

### External APIs
- **OpenWeatherMap**: Provides current weather, hourly, and daily forecasts, including icons.  
  Site: [https://openweathermap.org](https://openweathermap.org)
- **ip-api.com**: Converts client IP to city/country for geolocation.  
  Site: [http://ip-api.com](http://ip-api.com)

### Build & Deployment
- **Maven**: Dependency management and build tool.
- **Docker + Render**: Provides containerized deployment for consistent environments.
- Local development: Runs on `localhost:8080`.
- Render deployment: Cloud-ready, scalable, environment variables for API keys.

---

## Key Features
- Automatic IP-based geolocation
- Manual location search
- Real-time weather data
- Hourly and daily forecasts
- Weather icons
- Fallbacks for robust performance
- JSON API available at `/api/weather`

