package net.anatolich.iris;

import org.springframework.boot.SpringApplication;

public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(IrisApplication::main)
            .with(ContainersConfiguration.class)
            .run(args);
    }
}
