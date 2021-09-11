package com.steg.tencrypt.utilities;

/**
 * The viewstate does three work
 * whether or not the image isloaded
 * the current uri(filepath) if we have it
 * the text data if available
 * the error if we have
 */
public class EncryptViewState {
    public final boolean isLoading;
    public final String uri;
    public final Throwable error;

    public EncryptViewState(boolean isLoading, String uri, Throwable error) {
        this.isLoading = isLoading;
        this.uri = uri;
        this.error = error;
    }
}
