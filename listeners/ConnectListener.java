package com.articfoxdevelopment.minigame.listeners;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.instances.Arena;
import com.articfoxdevelopment.minigame.managers.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private Minigame main;

    public ConnectListener(Minigame main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(ConfigManager.getLobbySpawn());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Arena arena = main.getArenaManager().getArena(e.getPlayer());

        if(arena != null){
            arena.removePlayer(e.getPlayer());
        }
    }

}
