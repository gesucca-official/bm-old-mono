package com.gsc.bm.server.service.session.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class ActionLog<T> {

    private final String when = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private final String what;
    private final T data;

    public ActionLog(String what, T data) {
        this.what = what;
        this.data = data;
    }
}