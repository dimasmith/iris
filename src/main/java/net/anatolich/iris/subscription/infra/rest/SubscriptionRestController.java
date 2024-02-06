package net.anatolich.iris.subscription.infra.rest;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.subscription.MonthlyChargesDto;
import net.anatolich.iris.subscription.SubscriptionsService;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "v1/subscriptions")
@Slf4j
public class SubscriptionRestController {

    private final SubscriptionsService service;

    public SubscriptionRestController(SubscriptionsService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@Validated @RequestBody SubscriptionPayload subscribeCommand) {
        log.debug("got request to subscribe to service {}", subscribeCommand);
        service.subscribe(
                subscribeCommand.getService().toServiceData(),
                subscribeCommand.getRate());
    }

    @GetMapping
    public List<SubscriptionPayload> listSubscriptions() {
        return service.listSubscriptions().stream()
                .map(SubscriptionPayload::from)
                .toList();
    }

    @GetMapping(path = "/charges")
    public MonthlyChargesDto calculateCharges() {
        return service.calculateCharges();
    }
}
