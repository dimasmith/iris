package net.anatolich.iris.infra.todoist;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoistTest {

    @Mock
    private TodoistClient apiClient;
    @Captor
    private ArgumentCaptor<String> messageCaptor;
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
        final Money balance = Money.of(42, "UAH");
        todoist.competeSettleTask();
        todoist.reopenSettleTask(balance);

        verify(apiClient).updateTask(messageCaptor.capture(), any(LocalDate.class), eq(properties.getTaskId()));
        verify(apiClient).reopenTask(properties.getTaskId());
        verify(apiClient).closeTask(properties.getTaskId());

        Assertions.assertThat(messageCaptor.getValue())
            .as("message must describe the task and contain an amount")
            .contains("Settle my balance")
            .contains(balance.getNumber().toString())
            .contains(balance.getCurrency().getCurrencyCode());
    }

    @Test
    void passCurrentDateToClientWhenUpdatingTask() {
        final Money balance = Money.of(42, "UAH");
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        todoist.setClock(clock);

        todoist.reopenSettleTask(balance);

        verify(apiClient).updateTask(anyString(), eq(LocalDate.now(clock)), eq(properties.getTaskId()));
    }
}
