package com.whitefirex.microjump;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryListener implements Listener {

    private List<UUID> playerList = new ArrayList<UUID>();

    public void addPlayer(UUID uuid){
        playerList.add(uuid);
    }

    public void removePlayer(UUID uuid){
        playerList.remove(uuid);
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        if (playerList.contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if (playerList.contains(event.getWhoClicked().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        if (playerList.contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        if (playerList.contains(event.getPlayer().getUniqueId()))
            playerList.remove(event.getPlayer().getUniqueId());
    }


}
