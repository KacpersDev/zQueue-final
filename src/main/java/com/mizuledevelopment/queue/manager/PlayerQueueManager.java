package com.mizuledevelopment.queue.manager;

import com.mizuledevelopment.Queue;
import com.mizuledevelopment.queue.PlayerQueue;
import com.mizuledevelopment.queue.ServerQueue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerQueueManager {

    private final Queue plugin;
    public List<PlayerQueue> playerData = new ArrayList<>();

    public PlayerQueueManager(Queue plugin) {
        this.plugin = plugin;
    }

    public PlayerQueue getPlayerQueue(Player player) {
        for (PlayerQueue playerQueue : playerData) {
            if (playerQueue.getUuid().equals(player.getUniqueId())) {
                return playerQueue;
            }
        }
        return null;
    }

    public int getPermissionValue(Player player){
        List<Integer> positions = new ArrayList<>();
        for (final String position : Objects.requireNonNull(this.plugin.getPositionsConfig().getConfiguration().getConfigurationSection("positions")).getKeys(false)) {
            if (player.hasPermission(Objects.requireNonNull(this.plugin.getPositionsConfig().getConfiguration().getString("positions." + position + ".permission")))) {
                positions.add(this.plugin.getPositionsConfig().getConfiguration().getInt("positions." + position + ".value"));
            }
        }

        if (positions.size() == 0) return 0;
        int value = positions.size() - 1;
        return positions.get(value);
    }

    public void updateQueue(Player player, ServerQueue serverQueue, int permission) {
        boolean applied = false;
        int current = 0;
        if (serverQueue.getPlayers().size() > 0) {
            for (UUID uuid : serverQueue.getPlayers()) {
                int perm = this.getPermissionValue(Bukkit.getPlayer(uuid));

                if (perm < permission) {
                    if (!applied) {
                        current = serverQueue.getPlayers().indexOf(uuid);
                        serverQueue.getPlayers().set(current, player.getUniqueId());
                        applied = true;
                    }

                    current++;
                    serverQueue.getPlayers().set(current, uuid);
                }
            }
        } else {
            serverQueue.getPlayers().add(player.getUniqueId());
        }
    }

    public void joinQueue(Player player, String queueName) {

        PlayerQueue playerQueue = this.getPlayerQueue(player);

        ServerQueue serverQueue = this.plugin.getQueueManager().getQueueByName(queueName);
        if (playerQueue.getPlayerQueues() == null) {
            if (serverQueue != null) {
                int permission = this.getPermissionValue(player);
                List<ServerQueue> serverQueues = new ArrayList<>();
                serverQueues.add(serverQueue);
                playerQueue.setPlayerQueues(serverQueues);
                if (permission == 0) {
                    serverQueue.getPlayers().add(player.getUniqueId());
                } else {
                    updateQueue(player, serverQueue, permission);
                }
            }
        } else {
            if (serverQueue != null) {
                int permission = this.getPermissionValue(player);
                List<ServerQueue> serverQueues = playerQueue.getPlayerQueues();
                serverQueues.add(serverQueue);
                playerQueue.setPlayerQueues(serverQueues);
                if (permission == 0) {
                    serverQueue.getPlayers().add(player.getUniqueId());
                } else {
                    updateQueue(player, serverQueue, permission);
                }
            }
        }
    }

    public Player getFirstPlayer(ServerQueue serverQueue){
        return Bukkit.getPlayer(serverQueue.getPlayers().get(0));
    }

    public void leaveQueue(Player player, String queueName){
        this.getPlayerQueue(player).getPlayerQueues().remove(this.plugin.getQueueManager().getQueueByName(queueName));
        this.plugin.getQueueManager().getQueueByName(queueName).getPlayers().remove(player.getUniqueId());
    }

    public List<PlayerQueue> getPlayerData() {
        return playerData;
    }
}
