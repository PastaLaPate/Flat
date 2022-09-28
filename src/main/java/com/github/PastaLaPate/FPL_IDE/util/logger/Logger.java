package com.github.PastaLaPate.FPL_IDE.util.logger;

import java.io.IOException;

public class Logger {

    private static final String PREFIX = "[FPL_IDE] ";

    private static final LoggerFileManager loggerFileManager = new LoggerFileManager();

    public static void log(String message) {
        print(PREFIX + message);
    }

    public static void log(String message, Class<?> from, Level level) {
        print(PREFIX + getClassString(from) + level.getLevel() + message);
    }

    public static void log(Exception e) {
        System.out.println(PREFIX + e.getClass().getName());
        e.printStackTrace();
/*        StringBuilder message = new StringBuilder(e.getMessage());
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append("\n").append(stackTraceElement.toString());
        }
        log(message.toString(), Logger.class, Level.ERROR);*/
    }

    public static void log(Object o) {
        log(String.valueOf(o));
    }

    private static void print(String message) {
        System.out.println(message);
        try {
            loggerFileManager.log(message);
        } catch (IOException e) {
            log(e);
        }
    }

    private static String getClassString(Class<?> from) {
        return "[" + from.getSimpleName() + "] ";
    }
}
