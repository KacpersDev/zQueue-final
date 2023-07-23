package com.mizuledevelopment.task.manager;

import com.mizuledevelopment.Queue;
import com.mizuledevelopment.queue.ServerQueue;
import com.mizuledevelopment.task.QueueTask;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QueueTaskManager {

    private final Queue plugin;
    public List<QueueTask> tasks = new ArrayList<>();

    public QueueTaskManager(Queue plugin) {
        this.plugin = plugin;
    }

    public void loadTasks(){
        for (ServerQueue serverQueue : this.plugin.getQueueManager().getServerQueues()) {
            tasks.add(new QueueTask(serverQueue));
        }
    }
}
