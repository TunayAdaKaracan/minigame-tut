package com.articfoxdevelopment.minigame.managers;

import com.articfoxdevelopment.minigame.instances.Arena;
import com.articfoxdevelopment.minigame.Minigame;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(Minigame minigame){
        FileConfiguration config = minigame.getConfig();

        for(String str : config.getConfigurationSection("arenas").getKeys(false)){
            arenas.add(new Arena(minigame, Integer.parseInt(str), ConfigManager.createLocation("arenas." + str)));
        }

    }

    public List<Arena> getArenas(){return arenas;}

    public Arena getArena(Player player){
        for(Arena arena : arenas){
            if(arena.getPlayers().contains(player.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for(Arena arena : arenas){
            if(arena.getId() == id){
                return arena;
            }
        }
        return null;
    }

}
