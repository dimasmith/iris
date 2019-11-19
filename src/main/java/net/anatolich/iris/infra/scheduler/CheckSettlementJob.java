package net.anatolich.iris.infra.scheduler;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Slf4j
public class CheckSettlementJob implements Job {

    private final SettlementService settlements;

    public CheckSettlementJob(SettlementService settlements) {
        this.settlements = settlements;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("invoking settlements check");
        settlements.checkSettlementOnSchedule();
    }
}
