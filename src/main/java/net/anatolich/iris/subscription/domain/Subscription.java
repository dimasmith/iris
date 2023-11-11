package net.anatolich.iris.subscription.domain;

import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CompositeType;
import org.javamoney.moneta.Money;

import static net.anatolich.iris.util.Arguments.rejectNull;

/**
 * A periodic subscription to a certain online service.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "subscription")
public final class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "id_sequence", allocationSize = 1)
    private Long id;
    private ServiceProvider serviceProvider;

    @AttributeOverride(name = "amount", column = @Column(name = "rate_amount", precision = 2))
    @AttributeOverride(name = "currency", column = @Column(name = "rate_currency"))
    @CompositeType(MonetaryAmountType.class)
    private Money rate;

    private Subscription(ServiceProvider serviceProvider, Money rate) {
        this.serviceProvider = rejectNull(serviceProvider, "service must not be null");
        this.rate = rejectNull(rate, "subscription rate must not be null");
    }

    public static Subscription forNewService(ServiceProvider serviceProvider, Money rate) {
        return new Subscription(serviceProvider, rate);
    }
}
