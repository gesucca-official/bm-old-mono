package com.gsc.bm.server.service.view.model;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.cards.Card;
import com.gsc.bm.server.model.game.status.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@Builder
@Getter
public class ClientCharacterView {
    private final String name;
    private final int itemsSize;
    private final Queue<Card> items;
    private final Map<Resource, Integer> resources;
    private final List<Status> statuses;
    private final Set<Resource> immunities;

    private final String sprite;
}
