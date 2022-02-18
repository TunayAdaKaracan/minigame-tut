package com.articfoxdevelopment.minigame.kit.type;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.kit.Kit;
import com.articfoxdevelopment.minigame.kit.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class FighterKit extends Kit {

    public FighterKit(Minigame minigame, UUID uuid){
        super(minigame, KitType.FIGHTER, uuid);
    }

    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 3));
    }



}
