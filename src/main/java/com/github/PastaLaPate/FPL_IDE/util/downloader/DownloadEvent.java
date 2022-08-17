package com.github.PastaLaPate.FPL_IDE.util.downloader;

public class DownloadEvent {
    private final String fileName;
    private final int fileNumber;
    private final int maxfiles;

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
