package com.github.PastaLaPate.FPL_IDE.util.logger;

import java.io.IOException;

public class Logger {

    private static final String PREFIX = "[FPL_IDE] ";

    public static void log(String message) {
        print(PREFIX + message);
    }

    public static void log(String message, Class<?> from, Level level) {
        print(PREFIX + getClassString(from) + level.getLevel() + message);
    }

    public static void log(String message, Class<?> from) {
        print(PREFIX + getClassString(from) + message);
    }

    private static void print(String message) {
        System.out.println(message);
        try {
            LoggerFileManager.log(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getClassString(Class<?> from) {
        return "[" + from.getSimpleName() + "] ";
    }
}
