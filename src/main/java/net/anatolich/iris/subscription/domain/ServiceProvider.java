package net.anatolich.iris.subscription.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        checkArgument(!isNullOrEmpty(name), "service name must not be empty");
        this.name = name;
        this.url = url;
        this.description = description;
    }
}
