FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем файл JAR приложения в рабочую директорию контейнера
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Открываем порт 8080 для доступа к приложению
EXPOSE 8080

# Запускаем приложение при запуске контейнера
ENTRYPOINT ["java", "-jar", "demo.jar"]
