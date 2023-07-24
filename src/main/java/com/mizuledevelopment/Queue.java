package com.mizuledevelopment;

import com.mizuledevelopment.command.QueueCommand;
import com.mizuledevelopment.command.zQueueCommand;
import com.mizuledevelopment.listener.QueueListener;
import com.mizuledevelopment.queue.manager.PlayerQueueManager;
import com.mizuledevelopment.queue.manager.QueueManager;
import com.mizuledevelopment.task.QueueTask;
import com.mizuledevelopment.task.manager.QueueTaskManager;
import com.mizuledevelopment.task.runnable.QueueRunnable;
import com.mizuledevelopment.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Queue extends JavaPlugin {

    private static QueueAPI queueAPI;
    private Config licenseConfig;
    private Config queueConfig;
    private Config messagesConfig;
    private Config positionsConfig;
    private QueueManager queueManager;
    private PlayerQueueManager playerQueueManager;
    private final List<QueueRunnable> queueRunnables = new ArrayList<>();

    @Override
    public void onEnable() {
        this.configuration();
        this.command();
        this.listener(Bukkit.getPluginManager());

        this.queueManager = new QueueManager(this);
        this.queueManager.loadQueues();
        this.playerQueueManager = new PlayerQueueManager(this);

        QueueTaskManager queueTaskManager = new QueueTaskManager(this);
        queueTaskManager.loadTasks();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        for (QueueTask queueTask : queueTaskManager.getTasks()) {
            QueueRunnable queueRunnable = new QueueRunnable(this);
            queueRunnable.run(queueTask);
            queueRunnables.add(queueRunnable);
        }

        queueAPI = new QueueAPI(this);
    }

    private void command(){
        Objects.requireNonNull(this.getCommand("queue")).setExecutor(new QueueCommand(this));
        Objects.requireNonNull(this.getCommand("zqueue")).setExecutor(new zQueueCommand());
    }

    private void listener(PluginManager pluginManager){
        Arrays.asList(
                new QueueListener(this)
        ).forEach(l -> pluginManager.registerEvents(l, this));
    }

    private void configuration(){
        queueConfig = new Config(this, new File(getDataFolder(), "queues.yml"), new YamlConfiguration(), "queues.yml");
        queueConfig.create();

        messagesConfig = new Config(this, new File(getDataFolder(), "messages.yml"), new YamlConfiguration(), "messages.yml");
        messagesConfig.create();

        positionsConfig = new Config(this, new File(getDataFolder(), "positions.yml"), new YamlConfiguration(), "positions.yml");
        positionsConfig.create();

        licenseConfig = new Config(this, new File(getDataFolder(), "license.yml"), new YamlConfiguration(), "license.yml");
        licenseConfig.create();
    }

    public Config getQueueConfig() {
        return queueConfig;
    }

    public Config getMessagesConfig() {
        return messagesConfig;
    }

    public Config getPositionsConfig() {
        return positionsConfig;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public PlayerQueueManager getPlayerQueueManager() {
        return playerQueueManager;
    }

    public Config getLicenseConfig() {
        return licenseConfig;
    }

    public static QueueAPI getQueueAPI() {
        return queueAPI;
    }
}
