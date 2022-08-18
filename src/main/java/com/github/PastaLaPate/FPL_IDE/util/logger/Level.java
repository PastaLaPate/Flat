package com.github.PastaLaPate.FPL_IDE.util.logger;

public class Level {
    public static final Level INFO = new Level("[INFO] ");
    public static final Level ERROR = new Level("[ERROR] ");

    private final String level;

    protected Level(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
