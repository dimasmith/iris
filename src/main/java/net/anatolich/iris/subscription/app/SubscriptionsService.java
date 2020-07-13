package net.anatolich.iris.subscription.app;

import java.util.List;
import java.util.stream.Collectors;
import net.anatolich.iris.subscription.domain.ServiceProvider;
import net.anatolich.iris.subscription.domain.Subscription;
import net.anatolich.iris.subscription.domain.SubscriptionRepository;
import net.anatolich.iris.subscription.infra.rest.SubscriptionDto;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service to manage subscription use-cases.
 */
@Service
public class SubscriptionsService {

    private final SubscriptionRepository repository;

    public SubscriptionsService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void subscribe(ServiceProvider service, Money rate) {
        final Subscription newSubscription = Subscription.forNewService(service, rate);
        repository.save(newSubscription);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDto> listSubscriptions() {
        final List<Subscription> subscriptions = repository.findAll();
        return subscriptions.stream()
            .map(SubscriptionDto::from)
            .collect(Collectors.toList());
    }
}
