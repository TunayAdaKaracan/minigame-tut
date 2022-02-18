package com.articfoxdevelopment.minigame.listeners;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.instances.Arena;
import com.articfoxdevelopment.minigame.instances.GameState;
import com.articfoxdevelopment.minigame.kit.KitType;
import com.articfoxdevelopment.minigame.teams.Team;
import com.articfoxdevelopment.minigame.teams.TeamUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GameListener implements Listener {

    private Minigame main;

    public GameListener(Minigame main){
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Arena arena = main.getArenaManager().getArena(e.getPlayer());
        if(arena != null && arena.getState() == GameState.LIVE){
            arena.getGame().addPoint(e.getPlayer());
        }

    }

    @EventHandler
    public void onInventoryInterraction(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        Arena arena = main.getArenaManager().getArena(player);

        if(e.getView().getTitle().equals(ChatColor.DARK_BLUE + "Kit Selection") && e.getCurrentItem() != null && e.getInventory() == e.getClickedInventory()){
            KitType type = KitType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());


            if(arena != null){
                KitType activated = arena.getKitType(player);
                if(activated != null && activated.equals(type)){
                    player.sendMessage(ChatColor.RED + "Hey, you already selected that kit");
                } else {
                    if(activated != null){
                        player.sendMessage(ChatColor.GREEN + "You deselected " + ChatColor.GOLD + activated.getDisplay());
                    }
                    player.sendMessage(ChatColor.GREEN + "You selected " + ChatColor.GOLD + type.getDisplay());
                    arena.setKit(player.getUniqueId(), type);
                }
            } else {
                player.closeInventory();
            }

            e.setCancelled(true);
        } else if(e.getView().getTitle().equals(ChatColor.DARK_BLUE + "Team Selection") && e.getCurrentItem() != null && e.getInventory() == e.getClickedInventory()){
            Team team = Team.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
            if(arena != null){
                Team activeTeam = arena.getTeam(player);
                if(activeTeam != null && activeTeam == team){
                    player.sendMessage(ChatColor.RED + "You are already in this team");
                } else {
                    if(activeTeam != null){
                        player.sendMessage(ChatColor.GREEN + "You deselected: " + activeTeam.getDisplay());
                    }
                    player.sendMessage(ChatColor.GREEN + "You selected: " + team.getDisplay());
                    arena.setTeam(player, team);
                    new TeamUI(arena, player);
                }
            }
            e.setCancelled(true);
        }

    }

}
