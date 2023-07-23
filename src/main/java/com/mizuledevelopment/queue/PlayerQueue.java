package com.mizuledevelopment.queue;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlayerQueue {

    private UUID uuid;
    private List<ServerQueue> playerQueues;
    private boolean isOffline;

    public PlayerQueue(UUID uuid, List<ServerQueue> playerQueues, boolean isOffline) {
        this.uuid = uuid;
        this.playerQueues = playerQueues;
        this.isOffline = isOffline;
    }
}
