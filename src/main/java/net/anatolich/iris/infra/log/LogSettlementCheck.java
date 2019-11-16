package net.anatolich.iris.infra.log;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.SettlementCheck;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogSettlementCheck {

    @EventListener(classes = SettlementCheck.class)
    public void logSettlementCheck(SettlementCheck settlementCheck) {
        log.info("settlement check: {}", settlementCheck);
    }

}
