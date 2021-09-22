package com.steg.tencrypt.utilities;

public class Event<T> {
    public interface Handler<T> {
        void handle(T content);
    }

    private final T content;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    public void handle(Event.Handler<T> handler) {
        if (!hasBeenHandled) {
            hasBeenHandled = true;
            handler.handle(content);
        }
    }
}
