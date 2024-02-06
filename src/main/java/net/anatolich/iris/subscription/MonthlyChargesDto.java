package net.anatolich.iris.subscription;

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
