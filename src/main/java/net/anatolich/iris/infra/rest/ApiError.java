package net.anatolich.iris.infra.rest;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Value
@Builder
class ApiError {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
}
