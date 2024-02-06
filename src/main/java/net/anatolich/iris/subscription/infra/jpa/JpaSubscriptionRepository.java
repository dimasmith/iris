package net.anatolich.iris.subscription.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import net.anatolich.iris.subscription.domain.Subscription;
import net.anatolich.iris.subscription.domain.SubscriptionRepository;

public interface JpaSubscriptionRepository extends SubscriptionRepository, JpaRepository<Subscription, Long> {

}
