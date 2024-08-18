# Используем официальный образ OpenJDK как базовый
FROM openjdk:17-jdk-slim

# Устанавливаем рабочий каталог
WORKDIR /app

# Копируем JAR-файл приложения в рабочий каталог
COPY target/Theme-service-0.0.1-SNAPSHOT.jar app.jar

# Определяем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
