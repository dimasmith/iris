package net.anatolich.iris.infra.rest;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.SettlementCheck;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/v1/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @GetMapping
    public SettlementCheck settlementForAccounts() {
        log.info("retrieving settlement");
        return settlementService.compareAccountingAndBankBalances();
    }
}
