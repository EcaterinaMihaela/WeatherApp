
# Imagine de bază cu JDK 17
FROM openjdk:17-jdk-slim

# Directorul de lucru în container
WORKDIR /app

# Copiem toate fișierele din proiect în container
COPY . .

# Rulăm comanda Maven pentru a construi aplicația
RUN ./mvnw clean package

# Comanda de start a aplicației
CMD ["java", "-jar", "target/weather-app-0.0.1-SNAPSHOT.jar"]
