package com.articfoxdevelopment.minigame.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class KitUI {

    public KitUI(Player player){
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_BLUE + "Kit Selection");

        for (KitType type : KitType.values()){
            ItemStack itemStack = new ItemStack(type.getMaterial());
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(type.getDisplay());
            meta.setLore(Arrays.asList(type.getDescription()));
            meta.setLocalizedName(type.name());
            itemStack.setItemMeta(meta);
            gui.addItem(itemStack);
        }

        player.openInventory(gui);
    }


}
