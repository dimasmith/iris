package net.anatolich.iris.domain.settlement;

public class InvalidCurrencyCodeException extends RuntimeException {
    public InvalidCurrencyCodeException(int currencyCode) {
        super("invalid currency code: %d".formatted(currencyCode));
    }

    public InvalidCurrencyCodeException(String currencyCode) {
        super("invalid currency code: %s".formatted(currencyCode));
    }
}
