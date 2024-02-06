package net.anatolich.iris.subscription.domain;

import java.util.List;

public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    List<Subscription> findAll();

    void deleteAll();
}
