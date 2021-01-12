package com.gsc.bm.server.service.factories;

public class ValueNotFoundException extends RuntimeException {

    public ValueNotFoundException(String what) {
        super(what);
    }
}
