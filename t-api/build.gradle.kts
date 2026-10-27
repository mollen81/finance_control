group = "org.mollen"
version = "0.1"

plugins {
    id("io.qameta.allure") version "4.0.0"
    id("io.qameta.allure-adapter") version "4.0.0"
}

dependencies {
    // T-Invest API
    implementation("ru.tinkoff.piapi:java-sdk-core:1.31")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    // Source: https://mvnrepository.com/artifact/org.springframework/spring-web
    implementation("org.springframework:spring-web:4.3.11.RELEASE")

    /// gRPC
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
    implementation("io.grpc:grpc-stub:1.71.0")
    implementation("io.grpc:grpc-protobuf:1.71.0")

    // Protobuf
    implementation("com.google.protobuf:protobuf-java:4.30.2")
    implementation("com.google.protobuf:protobuf-java-util:4.30.2")

    // JavaX
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    //dotenv
    // Source: https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java
    implementation("io.github.cdimascio:dotenv-java:3.2.0")

    // Allure
    testImplementation("io.qameta.allure:allure-junit5:2.34.0") {
        exclude(group = "org.junit.jupiter")
    }
    testImplementation("io.qameta.allure:allure-grpc:2.34.0")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.30.2"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}