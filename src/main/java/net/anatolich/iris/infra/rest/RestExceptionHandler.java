package net.anatolich.iris.infra.rest;

import net.anatolich.iris.domain.settlement.IncorrectAccountingAccountException;
import net.anatolich.iris.domain.settlement.IncorrectBankAccountException;
import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            IncorrectBankAccountException.class,
            IncorrectAccountingAccountException.class,
            InvalidCurrencyCodeException.class
    })
    public ResponseEntity<ApiError> handleErrors(RuntimeException exception, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }
}
