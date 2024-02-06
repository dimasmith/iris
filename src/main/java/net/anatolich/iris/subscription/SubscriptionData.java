package net.anatolich.iris.subscription;

import org.javamoney.moneta.Money;

public record SubscriptionData(ServiceData service, Money rate) {
    
}
