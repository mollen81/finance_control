# Этап 1: Сборка артефакта через Gradle
FROM gradle:8.8-jdk21-alpine AS builder
WORKDIR /app

# Копируем конфигурационные файлы сборки для кэширования слоев
COPY build.gradle settings.gradle ./
# Если в проекте есть proto-файлы или gradle-папка
COPY src ./src

# Собираем исполняемый jar без запуска тестов
RUN gradle bootJar --no-daemon -x test

# Этап 2: Минимальный образ для рантайма
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Создаем непривилегированного пользователя для безопасности
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Копируем скомпилированный jar из этапа сборки
COPY --from=builder /app/build/libs/*.jar app.jar

# Порт REST-контроллера
EXPOSE 8080

# Оптимизированные параметры JVM для контейнеров
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-jar", "app.jar"]