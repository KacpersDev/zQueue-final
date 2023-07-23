package com.mizuledevelopment.queue;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.UUID;

@Getter
@Setter
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
}
