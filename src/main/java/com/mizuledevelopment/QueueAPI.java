package com.mizuledevelopment;

import com.mizuledevelopment.queue.PlayerQueue;
import com.mizuledevelopment.queue.ServerQueue;
import org.bukkit.entity.Player;

import java.util.List;

public class QueueAPI {

    private final Queue plugin;

    public QueueAPI(Queue plugin) {
        this.plugin = plugin;
    }

    public int getPlayerSizeQueue(Player player, String queueName){
        return this.plugin.getQueueManager().getQueueSize(player, queueName);
    }

    public int getPlayerQueuePosition(Player player, String queueName) {
        return this.plugin.getQueueManager().getPlayerPosition(player, queueName);
    }

    public PlayerQueue getQueuePlayer(Player player){
        return this.plugin.getPlayerQueueManager().getPlayerQueue(player);
    }

    public ServerQueue getServerQueue(String queueName) {
        return this.plugin.getQueueManager().getQueueByName(queueName);
    }

    public List<ServerQueue> getPlayerQueues(Player player){
        return this.plugin.getPlayerQueueManager().getPlayerQueue(player).getPlayerQueues();
    }
}