package com.articfoxdevelopment.minigame.kit;

import org.bukkit.Material;

public enum KitType {

    MINER("Miner Kit", Material.DIAMOND_PICKAXE, "Best Miner Kit"),
    FIGHTER("Fighter Kit", Material.DIAMOND_SWORD, "Best Fighter Kit");

    private String display, description;
    private Material material;

    KitType(String display, Material material, String description){
        this.display = display;
        this.description = description;
        this.material = material;
    }


    public String getDisplay(){return display;}
    public Material getMaterial(){return material;}
    public String getDescription(){return description;}

}
// MINER = new MINER()