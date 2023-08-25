package net.anatolich.iris.infra.todoist;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

class TodoistClient {

    private static final String API_HOST = "https://api.todoist.com/rest/v1/";
    private final RestTemplate restTemplate;
    private final HttpHeaders authorizationHeaders;

    public TodoistClient(RestTemplate restTemplate, TodoistProperties properties) {
        this.restTemplate = restTemplate;
        authorizationHeaders = new HttpHeaders();
        authorizationHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getToken());
    }

    void updateTask(String message, LocalDate newDate, String taskId) {
        final URI updateTaskUri = UriComponentsBuilder.fromUriString(API_HOST)
            .path("tasks/{taskId}")
            .buildAndExpand(Collections.singletonMap("taskId", taskId))
            .toUri();
        final RequestEntity<RescheduleTaskPayload> request = new RequestEntity<>(
            new RescheduleTaskPayload(newDate, message),
            authorizationHeaders,
            HttpMethod.POST,
            updateTaskUri);
        restTemplate.exchange(request, Void.class);
    }

    void closeTask(String taskId) {
        final URI closeTaskUri = UriComponentsBuilder.fromUriString(API_HOST)
            .pathSegment("tasks/{taskId}/close")
            .buildAndExpand(taskId)
            .toUri();
        final RequestEntity<Void> closeTaskRequest = new RequestEntity<>(
            authorizationHeaders,
            HttpMethod.POST,
            closeTaskUri);
        restTemplate.exchange(closeTaskRequest, Void.class);
    }

    void reopenTask(String taskId) {
        final URI reopenTaskUri = UriComponentsBuilder.fromUriString(API_HOST)
            .pathSegment("tasks/{taskId}/reopen")
            .buildAndExpand(taskId)
            .toUri();
        final RequestEntity<Void> reopedTaskRequest = new RequestEntity<>(
            authorizationHeaders,
            HttpMethod.POST,
            reopenTaskUri);
        restTemplate.exchange(reopedTaskRequest, Void.class);
    }

    static class RescheduleTaskPayload {
        private final LocalDate dueDate;
        private final String content;

        RescheduleTaskPayload(LocalDate dueDate, String content) {
            this.dueDate = dueDate;
            this.content = content;
        }

        @JsonProperty(value = "due_date")
        public String getDueDate() {
            return dueDate.format(DateTimeFormatter.ISO_DATE);
        }

        public String getContent() {
            return content;
        }
    }
}
