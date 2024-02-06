package net.anatolich.iris.subscription;

import java.util.List;

import org.javamoney.moneta.Money;

public interface SubscriptionsService {

    void subscribe(ServiceData service, Money rate);

    List<SubscriptionData> listSubscriptions();

    /**
     * Calculate charges for subscribed services.
     */
    MonthlyChargesDto calculateCharges();

}