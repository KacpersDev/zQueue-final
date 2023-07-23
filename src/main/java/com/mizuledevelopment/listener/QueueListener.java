package com.mizuledevelopment.listener;

import com.mizuledevelopment.Queue;
import com.mizuledevelopment.queue.PlayerQueue;
import com.mizuledevelopment.queue.ServerQueue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class QueueListener implements Listener {

    private final Queue plugin;

    public QueueListener(Queue plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.plugin.getQueueManager().getServerQueues() != null) {
            for (ServerQueue serverQueue : this.plugin.getQueueManager().getServerQueues()) {
                this.plugin.getPlayerQueueManager().leaveQueue(event.getPlayer(), serverQueue.getQueueName());
                serverQueue.getPlayers().remove(event.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerQueue playerQueue = new PlayerQueue(event.getPlayer().getUniqueId(), new ArrayList<>(), false);
        this.plugin.getPlayerQueueManager().getPlayerData().add(playerQueue);
    }
}
