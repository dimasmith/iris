package net.anatolich.iris.subscription.domain;

import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;
import org.javamoney.moneta.Money;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A periodic subscription to a certain online service.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "subscription")
@TypeDef(typeClass = MonetaryAmountType.class, defaultForType = Money.class)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "id_sequence", allocationSize = 1)
    private Long id;
    private ServiceProvider serviceProvider;

    @Columns(columns = {
        @Column(name = "rate_amount", precision = 2),
        @Column(name = "rate_currency")
    })
    private Money rate;

    private Subscription(ServiceProvider serviceProvider, Money rate) {
        checkArgument(serviceProvider != null, "service must not be null");
        checkArgument(rate != null, "subscription rate must not be null");
        this.serviceProvider = serviceProvider;
        this.rate = rate;
    }

    public static Subscription forNewService(ServiceProvider serviceProvider, Money rate) {
        return new Subscription(serviceProvider, rate);
    }
}
