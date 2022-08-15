package com.github.PastaLaPate.FPL_IDE;

public class DownloadEvent {
    private String fileName;
    private int fileNumber;
    private int maxfiles;

    public DownloadEvent(String fileName, int fileNumber, int maxfiles) {
        this.fileName = fileName;
        this.fileNumber = fileNumber;
        this.maxfiles = maxfiles;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public int getMaxfiles() {
        return maxfiles;
    }
}
