package net.anatolich.iris.infra.todoist;

import lombok.Data;

@Data
class TodoistProperties {
    private String token;
    private String taskId;
}
