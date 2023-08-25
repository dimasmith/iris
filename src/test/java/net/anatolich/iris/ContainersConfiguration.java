package net.anatolich.iris;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MariaDBContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfiguration {

    @Bean
    @ServiceConnection
    public MariaDBContainer<?> mariaDBContainer() {
        return new MariaDBContainer<>("mariadb:10.8-focal");
    }
}
