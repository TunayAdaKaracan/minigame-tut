package com.articfoxdevelopment.minigame.instances;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Minigame main;
    private Arena arena;
    private int CountdownSeconds;

    public Countdown(Minigame main, Arena arena){
        this.main = main;
        this.arena = arena;
        this.CountdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start(){
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(main, 0, 20);
    }

    @Override
    public void run() {
        if(CountdownSeconds == 0){
            cancel();
            arena.start();
            return;
        }

        if(CountdownSeconds <= 10 || CountdownSeconds % 15 == 0){
            arena.sendMessage(ChatColor.GREEN + "Game is starting after" + CountdownSeconds + "second" + (CountdownSeconds == 1? "": "s"));
        }

        arena.sendTitle(ChatColor.AQUA.toString() + CountdownSeconds + "Seconds left", ChatColor.GRAY + "until game start");

        CountdownSeconds--;
    }
}
