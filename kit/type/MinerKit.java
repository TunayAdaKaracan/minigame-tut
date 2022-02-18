package com.articfoxdevelopment.minigame.kit.type;

import com.articfoxdevelopment.minigame.Minigame;
import com.articfoxdevelopment.minigame.kit.Kit;
import com.articfoxdevelopment.minigame.kit.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class MinerKit extends Kit {

    public MinerKit(Minigame minigame, UUID uuid){
        super(minigame, KitType.MINER, uuid);
    }


    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 255));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(uuid.equals(e.getPlayer().getUniqueId())){
            System.out.println(e.getPlayer().getName() + " is miner");
        }
    }

}
