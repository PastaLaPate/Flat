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

    public static void log(Exception e) {
        String message = e.getMessage() + "\n" + e.getCause();
        log(message, Logger.class.getClass(), Level.ERROR);
    }

    private static void print(String message) {
        System.out.println(message);
        try {
            LoggerFileManager.log(message);
        } catch (IOException e) {
            log(e);
        }
    }

    private static String getClassString(Class<?> from) {
        return "[" + from.getSimpleName() + "] ";
    }
}
