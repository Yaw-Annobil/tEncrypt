package com.steg.tencrypt.utilities;

public class CryptResult {
    final CryptModel content;
    final Throwable throwable;

    public CryptResult(CryptModel content, Throwable throwable) {
        this.content = content;
        this.throwable = throwable;
    }
}
