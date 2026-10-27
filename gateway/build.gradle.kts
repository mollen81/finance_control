dependencies {
    // Spring Boot & GraphQL
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-web") // Для HTTP эндпоинта /graphql

    // gRPC Client
    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")

    // Зависимость на сгенерированные proto-классы (или на сам модуль t-api, если они собираются там)
    implementation(project(":t-api"))
}