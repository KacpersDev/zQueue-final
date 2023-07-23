package com.mizuledevelopment.task.runnable;

import com.mizuledevelopment.Queue;
import com.mizuledevelopment.bungeecord.QueueBungeeAdapter;
import com.mizuledevelopment.task.QueueTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class QueueRunnable {

    private final Queue plugin;
    private BukkitTask bukkitTask;
    private final QueueBungeeAdapter bungeeAdapter;

    public QueueRunnable(Queue plugin) {
        this.plugin = plugin;
        this.bungeeAdapter = new QueueBungeeAdapter(this.plugin);
    }

    public void run(QueueTask queueTask){
        bukkitTask = Bukkit.getScheduler().runTaskTimer(this.plugin, new Runnable() {
            int delay = queueTask.getServerQueue().getDelay();

            @Override
            public void run() {
                if (!queueTask.getServerQueue().isFrozen() && queueTask.getServerQueue().getPlayers().size() > 0) {
                    delay--;
                }

                if (delay <= 0) {
                    if (queueTask.getServerQueue().getPlayers().size() > 0) {
                        Player player = Bukkit.getPlayer(queueTask.getServerQueue().getPlayers().get(0));
                        if (player != null) {
                            bungeeAdapter.sendPlayer(player, queueTask.getServerQueue().getServer());
                            queueTask.getServerQueue().getPlayers().remove(player.getUniqueId());
                            delay = queueTask.getServerQueue().getDelay();
                        }
                    }
                }
            }
        }, 0L ,20L);
    }
}
