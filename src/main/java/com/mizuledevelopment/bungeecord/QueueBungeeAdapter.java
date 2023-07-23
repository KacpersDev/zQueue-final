package com.mizuledevelopment.bungeecord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mizuledevelopment.Queue;
import org.bukkit.entity.Player;

public class QueueBungeeAdapter {

    private final Queue plugin;

    public QueueBungeeAdapter(Queue plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("ALL")
    public void sendPlayer(Player player, String server) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("Connect");
        dataOutput.writeUTF(server);
        player.sendPluginMessage(this.plugin, "BungeeCord", dataOutput.toByteArray());
        System.out.println("send player");
    }
}
