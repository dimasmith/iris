package net.anatolich.iris.infra.todoist;

import java.time.LocalDate;

public class Todoist {

    private final TodoistClient client;
    private final TodoistProperties properties;

    public Todoist(TodoistClient client, TodoistProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    public void reopenSettleTask() {
        client.reopenTask(properties.getTaskId());
        client.updateTask(LocalDate.now(), properties.getTaskId());
    }

    public void competeSettleTask() {
        client.closeTask(properties.getTaskId());
    }
}
