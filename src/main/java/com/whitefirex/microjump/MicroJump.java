package com.whitefirex.microjump;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MicroJump extends JavaPlugin {

    private InventoryListener inventoryListener = new InventoryListener();

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(inventoryListener, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player)) {
            if (args.length == 0) {
                return false;
            }

            final Player p = (Player)sender;
            inventoryListener.addPlayer(p.getUniqueId());

            getServer().getScheduler().runTaskLater(this, new Runnable() {
                @Override
                public void run() {
                    inventoryListener.removePlayer(p.getUniqueId());
                }
            }, 4000);

            p.saveData();
            p.sendMessage(ChatColor.RED + "Teleporting you to " + args[0] + " please don't move!");

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                out.writeUTF(args[0]);
            }
            catch (IOException localIOException)
            {
            }
            p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
            return true;
        }

        return false;
    }
}