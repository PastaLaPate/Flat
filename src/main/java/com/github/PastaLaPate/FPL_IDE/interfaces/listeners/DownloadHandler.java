package com.github.PastaLaPate.FPL_IDE.interfaces.listeners;

import com.github.PastaLaPate.FPL_IDE.util.downloader.DownloadEvent;

public interface DownloadHandler {
    void fileDownloaded(DownloadEvent e);
}
