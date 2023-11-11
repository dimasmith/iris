package net.anatolich.iris.subscription.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static net.anatolich.iris.util.Arguments.rejectEmptyString;

/**
 * Value object to hold details on subscribed online service
 */
@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceProvider {

    @Column(nullable = false)
    private String name;
    private String url;
    private String description;

    public ServiceProvider(String name, String url, String description) {
        this.name = rejectEmptyString(name, "service name must not be empty");
        this.url = url;
        this.description = description;
    }
}
