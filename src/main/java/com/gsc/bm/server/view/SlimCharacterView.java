package com.gsc.bm.server.view;

import com.gsc.bm.server.model.Resource;
import com.gsc.bm.server.model.game.status.Status;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder
@Getter
public class SlimCharacterView implements Serializable {
    private final String name;
    protected final Map<Resource, Integer> resources;
    private final List<Status> statuses;
    private final Set<Resource> immunities;
}
