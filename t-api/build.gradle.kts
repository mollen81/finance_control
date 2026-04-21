group = "org.mollen"
version = "0.1"

dependencies {
    // T-Invest API
    implementation("ru.tinkoff.piapi:java-sdk-core:1.31")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")

    /// gRPC
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
    implementation("io.grpc:grpc-stub:1.71.0")
    implementation("io.grpc:grpc-protobuf:1.71.0")

    // Protobuf
    implementation("com.google.protobuf:protobuf-java:4.30.2")
    implementation("com.google.protobuf:protobuf-java-util:4.30.2")

}