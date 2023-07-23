package com.mizuledevelopment.task;


import com.mizuledevelopment.queue.ServerQueue;
import lombok.Getter;

@Getter
public class QueueTask {

    private final ServerQueue serverQueue;

    public QueueTask(ServerQueue serverQueue) {
        this.serverQueue = serverQueue;
    }
}
