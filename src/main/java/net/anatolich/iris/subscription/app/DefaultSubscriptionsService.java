package net.anatolich.iris.subscription.app;

import net.anatolich.iris.subscription.MonthlyChargesDto;
import net.anatolich.iris.subscription.ServiceData;
import net.anatolich.iris.subscription.SubscriptionData;
import net.anatolich.iris.subscription.SubscriptionsService;
import net.anatolich.iris.subscription.domain.ServiceProvider;
import net.anatolich.iris.subscription.domain.Subscription;
import net.anatolich.iris.subscription.domain.SubscriptionRepository;
import net.anatolich.iris.subscription.infra.rest.SubscriptionPayload;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application service to manage subscription use-cases.
 */
@Service
public class DefaultSubscriptionsService implements SubscriptionsService {

    private final SubscriptionRepository repository;

    public DefaultSubscriptionsService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void subscribe(ServiceData service, Money rate) {
        final Subscription newSubscription = Subscription.forNewService(service, rate);
        repository.save(newSubscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionData> listSubscriptions() {
        final List<Subscription> subscriptions = repository.findAll();
        return subscriptions.stream()
            .map(Subscription::asData)
            .toList();
    }

    /**
     * Calculate charges for subscribed services.
     */
    @Override
    @Transactional(readOnly = true)
    public MonthlyChargesDto calculateCharges() {
        final List<Subscription> subscriptions = repository.findAll();
        final Money totalRate = subscriptions.stream()
            .map(Subscription::getRate)
            .reduce(Money::add)
            .orElse(Money.of(0.0, "UAH"));
        return new MonthlyChargesDto(totalRate);
    }
}
