package com.mizuledevelopment.queue.manager;

import com.mizuledevelopment.Queue;
import com.mizuledevelopment.queue.ServerQueue;
import org.bukkit.entity.Player;

import java.util.*;

public class QueueManager {

    private final Queue plugin;
    public List<ServerQueue> serverQueues = new ArrayList<>();

    public QueueManager(Queue plugin) {
        this.plugin = plugin;
    }

    public void loadQueues(){
        if (this.plugin.getQueueConfig().getConfiguration().getConfigurationSection("queues") == null) return;
        for (final String string : Objects.requireNonNull(this.plugin.getQueueConfig().getConfiguration().getConfigurationSection("queues")).getKeys(false)) {
            serverQueues.add(new ServerQueue(new LinkedList<>(), this.plugin.getQueueConfig().getConfiguration().getString("queues." + string + ".name"),
                    this.plugin.getQueueConfig().getConfiguration().getInt("queues." + string + ".delay"),
                    this.plugin.getQueueConfig().getConfiguration().getBoolean("queues."  +string + ".force-frozen"),
                    this.plugin.getQueueConfig().getConfiguration().getString("queues." + string + ".server")));
        }
    }

    public ServerQueue getQueueByName(String queueName) {
        for (ServerQueue serverQueue : serverQueues) {
            if (serverQueue.getQueueName().equals(queueName)) {
                return serverQueue;
            }
        }
        return null;
    }

    public List<ServerQueue> getServerQueues() {
        return serverQueues;
    }

    public int getQueueSize(Player player, String queueName) {
        for (ServerQueue serverQueue : serverQueues) {
            if (serverQueue.getQueueName().equals(queueName) && serverQueue.getPlayers().contains(player.getUniqueId())) {
                return serverQueue.getPlayers().size();
            }
        }
        return 0;
    }

    public int getPlayerPosition(Player player, String queueName) {
        for (ServerQueue serverQueue : serverQueues) {
            if (serverQueue.getQueueName().equals(queueName)) {
                return serverQueue.getPlayers().indexOf(player.getUniqueId());
            }
        }
        return 0;
    }
}
