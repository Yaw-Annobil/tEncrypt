package com.steg.tencrypt.utilities;

public class Event<T> {
    interface Handler<T>{
        void handle(T content);
    }

    private final T content;

    private boolean hasBeenHandled = false;

    Event(T content) {
        this.content = content;
    }

    void handle(Event.Handler<T> handler){
        if(!hasBeenHandled){
            hasBeenHandled = true;
            handler.handle(content);
        }
    }
}
