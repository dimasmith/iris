package net.anatolich.iris.infra.todoist;

import net.anatolich.iris.domain.settlement.SettlementCheck;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SettlementEventHandlerTest {

    @Autowired
    private ApplicationEventPublisher publisher;
    @SpyBean
    private TodoistSettlementCheckListener listener;
    @MockBean
    private Todoist todoist;

    @Test
    void handleSettledSettlementCheck() {
        SettlementCheck settledCheck = new SettlementCheck(Money.of(1, "UAH"), Money.of(1, "UAH"));
        publisher.publishEvent(settledCheck);

        Mockito.verify(listener).onSettledBalances(settledCheck);
    }

    @Test
    void handleUnsettledSettlementCheck() {
        SettlementCheck settledCheck = new SettlementCheck(Money.of(1, "UAH"), Money.of(10, "UAH"));
        publisher.publishEvent(settledCheck);

        Mockito.verify(listener).onUnsettledBalances(settledCheck);
    }
}
