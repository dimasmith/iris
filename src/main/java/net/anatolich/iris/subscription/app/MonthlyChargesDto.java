package net.anatolich.iris.subscription.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javamoney.moneta.Money;

/**
 * Total charges for all subscribed services.
 */
@Data
@AllArgsConstructor
public class MonthlyChargesDto {

    private Money total;
}
