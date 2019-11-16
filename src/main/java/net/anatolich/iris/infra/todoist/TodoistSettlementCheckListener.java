package net.anatolich.iris.infra.todoist;

import net.anatolich.iris.domain.settlement.SettlementCheck;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

class TodoistSettlementCheckListener {

    private final Todoist todoist;

    public TodoistSettlementCheckListener(Todoist todoist) {
        this.todoist = todoist;
    }

    @EventListener(classes = SettlementCheck.class, condition = "#check.settled")
    @Async
    public void onSettledBalances(SettlementCheck check) {
        todoist.competeSettleTask();
    }

    @EventListener(classes = SettlementCheck.class, condition = "!#check.settled")
    @Async
    public void onUnsettledBalances(SettlementCheck check) {
        todoist.reopenSettleTask();
    }
}
