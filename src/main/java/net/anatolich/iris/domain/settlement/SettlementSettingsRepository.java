package net.anatolich.iris.domain.settlement;

public interface SettlementSettingsRepository {
    SettlementSettings getSettings();

    void save(SettlementSettings settings);
}
