package net.anatolich.iris;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> mariaDBContainer() {
        return new PostgreSQLContainer<>("postgres:15-alpine")
                .withUsername("iris")
                .withPassword("s3cr3t")
                .withDatabaseName("iris")
                .withReuse(true);
    }
}
