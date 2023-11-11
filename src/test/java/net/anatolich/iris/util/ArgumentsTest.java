package net.anatolich.iris.util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ArgumentsTest {

    public static final String ERROR_MESSAGE = "error message";

    @Test
    @DisplayName("check assertion")
    void baseAssertionCheck() {
        Object argument = "argument";
        assertThatNoException()
                .isThrownBy(() -> Arguments.assertArgument(argument, true, ERROR_MESSAGE));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Arguments.assertArgument(argument, false, ERROR_MESSAGE));
    }

    @Test
    @DisplayName("reject null values")
    void rejectNullValues() {
        Object passing = "passing";
        Object failing = null;

        assertThatNoException().isThrownBy(() -> Arguments.rejectNull(passing, ERROR_MESSAGE));
        assertThat(Arguments.rejectNull(passing, ERROR_MESSAGE))
                .isEqualTo(passing);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .as("null values must be rejected")
                .isThrownBy(() -> Arguments.rejectNull(failing, ERROR_MESSAGE));
    }

    @Test
    @DisplayName("reject null strings")
    void rejectNullStrings() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .as("null strings must be rejected")
                .isThrownBy(() -> Arguments.rejectEmptyString(null, ERROR_MESSAGE));
    }

    @Test
    @DisplayName("reject empty strings")
    void rejectEmptyStrings() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .as("empty strings must be rejected")
                .isThrownBy(() -> Arguments.rejectEmptyString("", ERROR_MESSAGE));
    }

    @Test
    @DisplayName("reject blank strings")
    void rejectBlankStrings() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .as("blank strings must be rejected")
                .isThrownBy(() -> Arguments.rejectEmptyString("  ", ERROR_MESSAGE));
    }

    @Test
    @DisplayName("accept correct strings")
    void acceptCorrectStrings() {
        assertThatNoException()
                .as("correct strings must be accepted")
                .isThrownBy(() -> Arguments.rejectEmptyString("name", ERROR_MESSAGE));
    }
}