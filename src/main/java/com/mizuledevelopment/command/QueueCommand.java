package com.mizuledevelopment.command;

import com.mizuledevelopment.Queue;
import com.mizuledevelopment.queue.ServerQueue;
import com.mizuledevelopment.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class QueueCommand implements CommandExecutor {

    private final Queue plugin;

    public QueueCommand(Queue plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            wrongUsage(sender);
        } else if (args[0].equalsIgnoreCase("join")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;

            if (args.length == 1) {
                wrongUsage(sender);
            } else {
                String queueName = args[1];

                if (this.plugin.getQueueManager().getQueueByName(queueName) == null) {
                    player.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-not-exists"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                if (this.plugin.getQueueManager().getQueueByName(queueName).getPlayers().contains(player.getUniqueId())) {
                    player.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-already-in"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                this.plugin.getPlayerQueueManager().joinQueue(player, queueName);
                player.sendMessage(Color.translate(this.plugin.getMessagesConfig().getConfiguration().getString("queue-joined")
                        .replace("%queue_name%", queueName)));
            }

        } else if (args[0].equalsIgnoreCase("leave")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;

            if (args.length == 1) {
                wrongUsage(sender);
            } else {
                String queueName = args[1];

                if (this.plugin.getQueueManager().getQueueByName(queueName) == null) {
                    player.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-not-exists"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                if (!this.plugin.getQueueManager().getQueueByName(queueName).getPlayers().contains(player.getUniqueId())) {
                    player.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-not-in"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                this.plugin.getPlayerQueueManager().leaveQueue(player, queueName);
                player.sendMessage(Color.translate(this.plugin.getMessagesConfig().getConfiguration().getString("queue-left")
                        .replace("%queue_name%", queueName)));
            }
        } else if (args[0].equalsIgnoreCase("pause")) {
            if (!(sender.hasPermission("zqueue.command.admin"))) {
                sender.sendMessage(Color.translate(this.plugin.getMessagesConfig().getConfiguration().getString("queue-no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String queueName = args[1];

                if (this.plugin.getQueueManager().getQueueByName(queueName) == null) {
                    sender.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-not-exists"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                ServerQueue serverQueue = this.plugin.getQueueManager().getQueueByName(queueName);

                if (serverQueue.isFrozen()) {
                    sender.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-already-frozen"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                serverQueue.setFrozen(true);
                sender.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-frozen"))
                        .replace("%queue_name%", queueName)));
            }
        } else if (args[0].equalsIgnoreCase("unpause")) {
            if (!(sender.hasPermission("zqueue.command.admin"))) {
                sender.sendMessage(Color.translate(this.plugin.getMessagesConfig().getConfiguration().getString("queue-no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String queueName = args[1];

                if (this.plugin.getQueueManager().getQueueByName(queueName) == null) {
                    sender.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-not-exists"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                ServerQueue serverQueue = this.plugin.getQueueManager().getQueueByName(queueName);

                if (!serverQueue.isFrozen()) {
                    sender.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-not-frozen"))
                            .replace("%queue_name%", queueName)));
                    return false;
                }

                serverQueue.setFrozen(false);
                sender.sendMessage(Color.translate(Objects.requireNonNull(this.plugin.getMessagesConfig().getConfiguration().getString("queue-unfrozen"))
                        .replace("%queue_name%", queueName)));
            }
        }

        return true;
    }

    private void wrongUsage(CommandSender sender) {
        if (sender.hasPermission("zqueue.command.admin")) {
            for (final String s : this.plugin.getMessagesConfig().getConfiguration().getStringList("queue.wrong-usage-admin")) {
                sender.sendMessage(Color.translate(s));
            }
        } else {
            for (final String s : this.plugin.getMessagesConfig().getConfiguration().getStringList("queue.wrong-usage")) {
                sender.sendMessage(Color.translate(s));
            }
        }
    }
}
