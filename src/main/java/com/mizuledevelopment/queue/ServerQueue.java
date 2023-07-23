package com.mizuledevelopment.queue;

import java.util.LinkedList;
import java.util.UUID;

public class ServerQueue {

    private LinkedList<UUID> players;
    private String queueName;
    private int delay;
    private boolean isFrozen;
    private String server;

    public ServerQueue(LinkedList<UUID> players, String queueName, int delay, boolean isFrozen, String server) {
        this.players = players;
        this.queueName = queueName;
        this.delay = delay;
        this.isFrozen = isFrozen;
        this.server = server;
    }

    public void setPlayers(LinkedList<UUID> players) {
        this.players = players;
    }

    public LinkedList<UUID> getPlayers() {
        return players;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public String getQueueName() {
        return queueName;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public String getServer() {
        return server;
    }

    public int getDelay() {
        return delay;
    }
}
