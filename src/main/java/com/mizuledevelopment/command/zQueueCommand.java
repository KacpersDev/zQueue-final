package com.mizuledevelopment.command;

import com.mizuledevelopment.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class zQueueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        sender.sendMessage(Color.translate("&b&m------&7&m------&b&m------&7&m------&b&m------"));
        sender.sendMessage(" ");
        sender.sendMessage(Color.translate("&7Plugin Name&8: &bzQueue"));
        sender.sendMessage(Color.translate("&7Author&8: &bMizuleDevelopment"));
        sender.sendMessage(" ");
        sender.sendMessage(Color.translate("&b&m------&7&m------&b&m------&7&m------&b&m------"));

        return true;
    }
}
