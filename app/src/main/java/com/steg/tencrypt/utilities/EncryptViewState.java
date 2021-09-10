package com.steg.tencrypt.utilities;

import android.net.Uri;

/**
 * The viewstate does three work
 * whether or not the image isloaded
 * the current uri(filepath) if we have it
 * the text data if available
 * the error if we have
 */
public class EncryptViewState {
    public final boolean isLoading;
    public final Uri uri;
    public final Throwable error;

    public EncryptViewState(boolean isLoading, Uri uri,Throwable error) {
        this.isLoading = isLoading;
        this.uri = uri;
        this.error = error;
    }
}
