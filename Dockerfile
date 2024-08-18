# Этап сборки
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Установка Maven
RUN apt-get update && \
    apt-get install -y maven

# Копируем pom.xml и исходный код
COPY pom.xml .
COPY src ./src

# Сборка проекта
RUN mvn clean package -DskipTests

# Этап выполнения
FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем JAR-файл из этапа сборки
COPY --from=build /app/target/Theme-service-0.0.1-SNAPSHOT.jar app.jar

# Копируем конфигурационный файл
COPY src/main/resources/application.yml /app/application.yml

# Определяем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
