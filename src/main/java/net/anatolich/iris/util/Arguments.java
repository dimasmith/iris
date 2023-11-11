package net.anatolich.iris.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Arguments {

    public static <T> T assertArgument(T argument, boolean correct, String message) {
        if (!correct) {
            throw new IllegalArgumentException(message);
        }
        return argument;
    }

    public static <T> T assertArgument(T argument, Predicate<? super T> correct, String message) {
        if (!correct.test(argument)) {
            throw new IllegalArgumentException(message);
        }
        return argument;
    }

    public static <T> T rejectNull(T argument, String message) {
        return assertArgument(argument, Objects::nonNull, message);
    }

    @SuppressWarnings("javabugs:S6416")
    public static String rejectEmptyString(String argument, String message) {
        return assertArgument(argument, argument != null && !argument.isBlank(), message);
    }
}
