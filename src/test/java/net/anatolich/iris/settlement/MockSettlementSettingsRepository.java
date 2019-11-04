package net.anatolich.iris.settlement;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.SettlementSettings;
import net.anatolich.iris.domain.settlement.SettlementSettingsRepository;

@Slf4j
public class MockSettlementSettingsRepository implements SettlementSettingsRepository {

    private final SettlementSettings settlementSettings = new SettlementSettings();

    @Override
    public SettlementSettings getSettings() {
        return settlementSettings;
    }

    @Override
    public void save(SettlementSettings settings) {
        log.info("saving settlement settings {}", settings);
    }
}
