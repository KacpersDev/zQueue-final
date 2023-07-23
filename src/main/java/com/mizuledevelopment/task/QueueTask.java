package com.mizuledevelopment.task;


import com.mizuledevelopment.queue.ServerQueue;

public class QueueTask {

    private final ServerQueue serverQueue;

    public QueueTask(ServerQueue serverQueue) {
        this.serverQueue = serverQueue;
    }

    public ServerQueue getServerQueue() {
        return serverQueue;
    }
}
