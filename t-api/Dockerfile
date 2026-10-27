# Этап 1: Сборка в окружении с glibc и apt
FROM gradle:8.8-jdk21 AS builder
WORKDIR /app

# Копируем конфигурацию сборки
COPY build.gradle settings.gradle ./
COPY src ./src

# Запускаем сборку со stacktrace
RUN gradle bootJar --no-daemon -x test --stacktrace

# Этап 2: Минимальный образ для рантайма
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-jar", "app.jar"]