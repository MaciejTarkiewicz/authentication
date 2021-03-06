package com.tarkiewicz.configuration

import io.micronaut.test.support.TestPropertyProvider
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
abstract class TestContainerFixture extends Specification implements TestPropertyProvider {

    private static final String MONGO__HOST = "MONGO_HOST"
    private static final String MONGO__PORT = "MONGO_PORT"
    private static final String KAFKA__HOST = "KAFKA_HOST"
    private static final String KAFKA__PORT = "KAFKA_PORT"
    private static final int MONGO_PORT = 27017
    private static final int KAFKA_PORT = 9093

    private static final String MONGO_IMAGE_NAME = "mongo:4.0.10"
    private static final String KAFKA_IMAGE_NAME = "confluentinc/cp-kafka"

    @Shared
    MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(MONGO_IMAGE_NAME))

    @Shared
    KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse(KAFKA_IMAGE_NAME))

    @Override
    Map<String, String> getProperties() {
        Map.of(MONGO__HOST, mongoDBContainer.containerIpAddress as String,
                MONGO__PORT, "${mongoDBContainer.getMappedPort(MONGO_PORT)}" as String,
                KAFKA__HOST, kafkaContainer.containerIpAddress as String,
                KAFKA__PORT, "${kafkaContainer.getMappedPort(KAFKA_PORT)}" as String,
        )
    }
}
