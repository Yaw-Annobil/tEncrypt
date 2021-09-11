package com.steg.tencrypt.utilities;

public class DecryptViewState {

    public final String textData;

    public final Throwable throwable;

    public final boolean isLoading;

    public DecryptViewState(String textData, Throwable throwable, boolean isLoading) {
        this.textData = textData;
        this.throwable = throwable;
        this.isLoading = isLoading;
    }
}
