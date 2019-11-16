package net.anatolich.iris.infra.scheduler;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class CheckSettlementJob implements Job {

    private final SettlementService settlements;

    public CheckSettlementJob(SettlementService settlements) {
        this.settlements = settlements;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("invoking settlements check");
        settlements.compareAccountingAndBankBalances();
    }
}
