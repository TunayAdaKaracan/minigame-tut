package com.articfoxdevelopment.minigame;

import com.articfoxdevelopment.minigame.commands.ArenaCommand;
import com.articfoxdevelopment.minigame.listeners.ConnectListener;
import com.articfoxdevelopment.minigame.listeners.GameListener;
import com.articfoxdevelopment.minigame.managers.ArenaManager;
import com.articfoxdevelopment.minigame.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigame extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);

        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    public ArenaManager getArenaManager(){return arenaManager;}
}
