package com.gsc.bm.server.service.session.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserSessionInfo {

    public enum Activity {
        FREE, QUEUED, PLAYING
    }

    private final String userLogin;
    private final String sessionId;
    @Setter
    private Activity activity;

}
