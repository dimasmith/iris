package net.anatolich.iris.domain.settlement;

public class InvalidCurrencyCodeException extends RuntimeException {
    public InvalidCurrencyCodeException(int currencyCode) {
        super(String.format("invalid currency code: %d", currencyCode));
    }
}
