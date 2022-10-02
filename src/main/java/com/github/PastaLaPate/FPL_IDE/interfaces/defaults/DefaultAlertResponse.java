package com.github.PastaLaPate.FPL_IDE.interfaces.defaults;

import com.github.PastaLaPate.FPL_IDE.interfaces.IAlertResponse;

public class DefaultAlertResponse implements IAlertResponse {
    private final boolean response;

    public DefaultAlertResponse(boolean response) {
        this.response = response;
    }

    @Override
    public boolean getConfirmed() {
        return response;
    }
}
