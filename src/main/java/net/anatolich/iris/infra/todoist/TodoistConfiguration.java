package net.anatolich.iris.infra.todoist;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnProperty(name = "iris.todoist.enabled", havingValue = "true")
public class TodoistConfiguration {

    private final RestTemplateBuilder templateBuilder;

    public TodoistConfiguration(RestTemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Bean
    @ConfigurationProperties(prefix = "iris.todoist")
    public TodoistProperties todoistProperties() {
        return new TodoistProperties();
    }

    @Bean
    public TodoistSettlementCheckListener todoistSettlementCheckListener() {
        return new TodoistSettlementCheckListener(todoist());
    }

    @Bean
    public Todoist todoist() {
        return new Todoist(todoistClient(), todoistProperties());
    }

    @Bean
    public TodoistClient todoistClient() {
        return new TodoistClient(restTemplate(), todoistProperties());
    }

    private RestTemplate restTemplate() {
        return templateBuilder.build();
    }
}
