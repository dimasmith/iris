package net.anatolich.iris.infra.demo;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.SettlementSettings;
import net.anatolich.iris.domain.settlement.SettlementSettingsRepository;

@Slf4j
public class EnvironmentSettlementSettingsRepository implements SettlementSettingsRepository {
    private final SettlementSettings settlementSettings;
    EnvironmentSettlementSettingsRepository(DemoSettlementSettings demoSettlementSettings) {
        this.settlementSettings = demoSettlementSettings.toSettings();
    }

    @Override
    public SettlementSettings getSettings() {
        return settlementSettings;
    }

    @Override
    public void save(SettlementSettings settings) {
        log.info("saving settlement settings {}", settings);
    }

}
