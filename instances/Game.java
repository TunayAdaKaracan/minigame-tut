package com.articfoxdevelopment.minigame.instances;

import com.articfoxdevelopment.minigame.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena){
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start(){
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "Game is started!");
        arena.sendTitle(" ", " ");

        for(UUID target : arena.getKits().keySet()){
            arena.getKits().get(target).onStart(Bukkit.getPlayer(target));
        }

        for(UUID target : arena.getPlayers()){
            points.put(target, 0);
            Bukkit.getPlayer(target).closeInventory();
        }
    }

    public void addPoint(Player p){
        int playerPoint = points.get(p.getUniqueId()) + 1;
        if(playerPoint == 20){
            arena.sendMessage(ChatColor.GOLD + p.getName() + " won the game!");
            arena.reset(true);
            return;
        }
        p.sendMessage(ChatColor.GRAY + "+1 Point");
        points.replace(p.getUniqueId(), playerPoint);

    }

}
