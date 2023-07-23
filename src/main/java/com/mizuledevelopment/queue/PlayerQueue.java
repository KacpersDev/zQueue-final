package com.mizuledevelopment.queue;


import java.util.List;
import java.util.UUID;

public class PlayerQueue {

    private UUID uuid;
    private List<ServerQueue> playerQueues;
    private boolean isOffline;

    public PlayerQueue(UUID uuid, List<ServerQueue> playerQueues, boolean isOffline) {
        this.uuid = uuid;
        this.playerQueues = playerQueues;
        this.isOffline = isOffline;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<ServerQueue> getPlayerQueues() {
        return playerQueues;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setPlayerQueues(List<ServerQueue> playerQueues) {
        this.playerQueues = playerQueues;
    }
}
