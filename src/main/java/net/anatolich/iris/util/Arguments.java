package net.anatolich.iris.util;

public class Arguments {

    public static void checkArgument(boolean correct, String message) {
        if (!correct) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> T rejectNull(T argument, String message) {
        checkArgument(argument != null, message);
        return argument;
    }

    public static String rejectEmptyString(String argument, String message) {
        checkArgument(argument != null && !argument.isBlank(), message);
        return argument;
    }
}
