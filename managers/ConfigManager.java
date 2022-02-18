package com.articfoxdevelopment.minigame.managers;

import com.articfoxdevelopment.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(Minigame minigame){
        ConfigManager.config = minigame.getConfig();
        minigame.saveDefaultConfig();

    }

    public static int getRequiredPlayers(){ return config.getInt("required-players");}

    public static int getCountdownSeconds(){return config.getInt("countdown-seconds");}

    public static Location getLobbySpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("lobby-spawn.world")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch")
        );
    }


    public static Location createLocation(String path){
        return new Location(
                Bukkit.getWorld(config.getString(path+".world")),
                config.getDouble(path+".x"),
                config.getDouble(path+".y"),
                config.getDouble(path+".z"),
                (float) config.getDouble(path+".yaw"),
                (float) config.getDouble(path+".pitch")
        );
    }



}
