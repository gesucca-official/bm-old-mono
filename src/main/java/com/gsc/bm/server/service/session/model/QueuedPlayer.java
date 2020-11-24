package com.gsc.bm.server.service.session.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class QueuedPlayer {
    private final String playerId;
    private final boolean isHuman;
}
