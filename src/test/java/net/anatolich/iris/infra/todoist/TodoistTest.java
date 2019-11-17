package net.anatolich.iris.infra.todoist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoistTest {

    @Mock
    private TodoistClient apiClient;
    private TodoistProperties properties;
    private Todoist todoist;

    @BeforeEach
    void createTodoist() {
        properties = new TodoistProperties();
        properties.setTaskId(UUID.randomUUID().toString());
        todoist = new Todoist(apiClient, properties);
    }

    @Test
    void passConfiguredTaskIdToClient() {
        todoist.competeSettleTask();
        todoist.reopenSettleTask();

        verify(apiClient).updateTask(any(LocalDate.class), eq(properties.getTaskId()));
        verify(apiClient).reopenTask(eq(properties.getTaskId()));
        verify(apiClient).closeTask(eq(properties.getTaskId()));
    }

    @Test
    void passCurrentDateToClientWhenUpdatingTask() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        todoist.setClock(clock);

        todoist.reopenSettleTask();

        verify(apiClient).updateTask(eq(LocalDate.now(clock)), eq(properties.getTaskId()));
    }
}