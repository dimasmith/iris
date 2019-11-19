package net.anatolich.iris.infra.scheduler;

import net.anatolich.iris.domain.settlement.SettlementService;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
public class ScheduledTasksConfiguration {

    @Bean
    public CheckSettlementJob checkSettlementJob(SettlementService settlements) {
        return new CheckSettlementJob(settlements);
    }

    @Bean
    public JobDetail checkSettlementJobDetail() {
        return JobBuilder.newJob(CheckSettlementJob.class)
            .storeDurably()
            .withIdentity("check-settlement", "settlement")
            .withDescription("Periodically check configured settlements")
            .build();
    }

    @Bean
    public Trigger checkSettlementsTrigger() {
        return TriggerBuilder.newTrigger()
            .forJob(checkSettlementJobDetail())
            .withIdentity("check-settlement-trigger", "settlement")
            .withDescription("Trigger for settlements check")
            .withSchedule(cronSchedule("0 0 * * * ?"))
            .build();
    }
}
