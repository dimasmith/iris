package net.anatolich.iris.infra.todoist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class TodoistClientTest {

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private TodoistClient todoistClient;
    private TodoistProperties properties;

    @BeforeEach
    void setUp() {
        restTemplate = restTemplateBuilder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        properties = new TodoistProperties();
        properties.setToken(UUID.randomUUID().toString());
        todoistClient = new TodoistClient(restTemplate, properties);
    }

    @Test
    void updateTask() {
        final String taskId = "12345";
        final LocalDate dueDate = LocalDate.of(2019, Month.OCTOBER, 27);
        mockServer.expect(requestTo("https://api.todoist.com/rest/v1/tasks/12345"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(jsonPath("due_date", equalTo("2019-10-27")))
                .andExpect(header(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + properties.getToken())))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        todoistClient.updateTask(dueDate, taskId);
    }

    @Test
    void reopenTask() {
        final String taskId = "12345";
        mockServer.expect(requestTo("https://api.todoist.com/rest/v1/tasks/12345/reopen"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + properties.getToken())))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        todoistClient.reopenTask(taskId);
    }

    @Test
    void closeTask() {
        final String taskId = "12345";
        mockServer.expect(requestTo("https://api.todoist.com/rest/v1/tasks/12345/close"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + properties.getToken())))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        todoistClient.closeTask(taskId);
    }
}