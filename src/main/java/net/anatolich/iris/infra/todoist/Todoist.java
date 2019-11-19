package net.anatolich.iris.infra.todoist;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import org.javamoney.moneta.Money;

public class Todoist {

    private final TodoistClient client;
    private final TodoistProperties properties;
    private Clock clock;

    public Todoist(TodoistClient client, TodoistProperties properties) {
        this.client = client;
        this.properties = properties;
        this.clock = Clock.systemDefaultZone();
    }

    public void reopenSettleTask(Money balance) {
        client.reopenTask(properties.getTaskId());
        final String taskContent = String.format("Settle my balance. Difference is %s", balance.toString());
        client.updateTask(taskContent, LocalDate.now(clock), properties.getTaskId());
    }

    public void competeSettleTask() {
        client.closeTask(properties.getTaskId());
    }

    /**
     * Set a different clock to derive today date.
     * For use in tests.
     */
    void setClock(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "provide non-null clock");
    }
}
