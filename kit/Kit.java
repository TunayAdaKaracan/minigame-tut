package com.articfoxdevelopment.minigame.kit;

import com.articfoxdevelopment.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.UUID;

public abstract class Kit implements Listener{

    private KitType type;
    protected UUID uuid;

    public Kit(Minigame minigame, KitType type, UUID uuid){
        this.type = type;
        this.uuid = uuid;


        Bukkit.getPluginManager().registerEvents(this, minigame);
    }

    public UUID getUUID(){return uuid;}
    public KitType getType(){return type;}

    public abstract void onStart(Player player);

    public void remove(){
        HandlerList.unregisterAll(this);
    }

}
