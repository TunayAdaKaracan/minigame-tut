package com.articfoxdevelopment.minigame.commands;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.instances.Arena;
import com.articfoxdevelopment.minigame.instances.GameState;
import com.articfoxdevelopment.minigame.kit.KitUI;
import com.articfoxdevelopment.minigame.managers.ArenaManager;
import com.articfoxdevelopment.minigame.teams.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ArenaCommand implements CommandExecutor {

    private Minigame main;

    public ArenaCommand(Minigame main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            ArenaManager arenaManager = main.getArenaManager();
            if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
                p.sendMessage(ChatColor.GREEN + "Available Arenas");
                for (Arena arena : arenaManager.getArenas()) {
                    p.sendMessage(ChatColor.GREEN + "- " + arena.getId() + " (" + arena.getState().name() + ")");
                }
            } else if(args.length == 1 && args[0].equalsIgnoreCase("team")) {
                Arena arena = arenaManager.getArena(p);
                if(arena == null){
                    p.sendMessage(ChatColor.RED + "You are not in any team");
                    return false;
                }

                if(arena.getState() == GameState.LIVE){
                    p.sendMessage(ChatColor.RED + "You cant select a team while game is running");
                    return false;
                }

                new TeamUI(arena, p);

            } else if (args.length == 1 && args[0].equalsIgnoreCase("kit")){

                Arena arena = arenaManager.getArena(p);
                if(arena == null){
                    p.sendMessage(ChatColor.RED + "You are not in any arena");
                    return false;
                }

                if(!arena.getState().equals(GameState.LIVE)){

                    new KitUI(p);

                } else {
                    p.sendMessage(ChatColor.RED + "You cant select kit while game is playing");
                }

            } else if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
                Arena arena = arenaManager.getArena(p);

                if(arena != null){
                    p.sendMessage(ChatColor.GRAY + "You left arena");
                    arena.removePlayer(p);
                } else {
                    p.sendMessage(ChatColor.RED + "You are not in any arena");
                }

            } else if(args.length == 2 && args[0].equalsIgnoreCase("join")){
                if(arenaManager.getArena(p) != null){
                    p.sendMessage("You are already in a arena.");
                    return false;
                }

                int id;
                try{
                    id = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    p.sendMessage(ChatColor.RED + "Please enter a valid id");
                    return false;
                }

                Arena arena = arenaManager.getArena(id);
                if(arena == null){
                    p.sendMessage(ChatColor.RED + "Please enter a valid arena id");
                    return false;
                }
                if(arena.getState().equals(GameState.LIVE)){
                    p.sendMessage(ChatColor.RED + " You cant join now!");
                } else {
                    p.sendMessage(ChatColor.GREEN + "You entered arena " + arena.getId());
                    arena.addPlayer(p);
                }

            } else {
                p.sendMessage(ChatColor.RED + "Wrong Usage");
                p.sendMessage(ChatColor.RED + "-/arena list");
                p.sendMessage(ChatColor.RED + "-/arena join <id>");
                p.sendMessage(ChatColor.RED + "-/arena leave");
                p.sendMessage(ChatColor.RED + "-/arena kit");
            }

        }

        return true;
    }
}
