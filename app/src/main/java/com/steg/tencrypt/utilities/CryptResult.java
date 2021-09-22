package com.steg.tencrypt.utilities;

public class CryptResult {
    public final CryptModel content;
    public final Throwable throwable;

    public CryptResult(CryptModel content, Throwable throwable) {
        this.content = content;
        this.throwable = throwable;
    }
}
