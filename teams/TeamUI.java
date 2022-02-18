package com.articfoxdevelopment.minigame.teams;

import com.articfoxdevelopment.minigame.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TeamUI {

    public TeamUI(Arena arena, Player player){
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_BLUE + "Team Selection");

        for(Team team : Team.values()){
            ItemStack item = new ItemStack(team.getMaterial());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(team.getDisplay());
            meta.setLore(Arrays.asList(ChatColor.GRAY.toString() + arena.getTeamCount(team) + "/" + "10"));
            if(arena.getTeam(player) == team){
                meta.setUnbreakable(true);
            }
            meta.setLocalizedName(team.name());
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);

    }

}
