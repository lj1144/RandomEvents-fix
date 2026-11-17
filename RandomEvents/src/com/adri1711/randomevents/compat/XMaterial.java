package com.adri1711.randomevents.compat;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/** Material-name compatibility for saved configs and old minigame definitions. */
public final class XMaterial {
    private static final Map<String, XMaterial> VALUES = new LinkedHashMap<>();
    private final String name;

    private XMaterial(String name) { this.name = name; }

    private static XMaterial register(String name) {
        XMaterial value = new XMaterial(name);
        VALUES.put(name, value);
        return value;
    }

    public static final XMaterial AIR = register("AIR");
    public static final XMaterial ANVIL = register("ANVIL");
    public static final XMaterial ARROW = register("ARROW");
    public static final XMaterial BEDROCK = register("BEDROCK");
    public static final XMaterial BEEF = register("BEEF");
    public static final XMaterial BLACK_TERRACOTTA = register("BLACK_TERRACOTTA");
    public static final XMaterial BLACK_WOOL = register("BLACK_WOOL");
    public static final XMaterial BLAZE_ROD = register("BLAZE_ROD");
    public static final XMaterial BLUE_TERRACOTTA = register("BLUE_TERRACOTTA");
    public static final XMaterial BLUE_WOOL = register("BLUE_WOOL");
    public static final XMaterial BOW = register("BOW");
    public static final XMaterial BROWN_TERRACOTTA = register("BROWN_TERRACOTTA");
    public static final XMaterial BROWN_WOOL = register("BROWN_WOOL");
    public static final XMaterial CHEST = register("CHEST");
    public static final XMaterial CHIPPED_ANVIL = register("CHIPPED_ANVIL");
    public static final XMaterial COAL_BLOCK = register("COAL_BLOCK");
    public static final XMaterial COOKED_BEEF = register("COOKED_BEEF");
    public static final XMaterial COOKED_PORKCHOP = register("COOKED_PORKCHOP");
    public static final XMaterial DAMAGED_ANVIL = register("DAMAGED_ANVIL");
    public static final XMaterial DIAMOND_HOE = register("DIAMOND_HOE");
    public static final XMaterial DIAMOND_SHOVEL = register("DIAMOND_SHOVEL");
    public static final XMaterial DIRT = register("DIRT");
    public static final XMaterial EGG = register("EGG");
    public static final XMaterial EMERALD = register("EMERALD");
    public static final XMaterial EMERALD_BLOCK = register("EMERALD_BLOCK");
    public static final XMaterial ENDER_EYE = register("ENDER_EYE");
    public static final XMaterial GLASS = register("GLASS");
    public static final XMaterial GOLDEN_HELMET = register("GOLDEN_HELMET");
    public static final XMaterial GOLDEN_HOE = register("GOLDEN_HOE");
    public static final XMaterial GOLDEN_SHOVEL = register("GOLDEN_SHOVEL");
    public static final XMaterial GOLDEN_SWORD = register("GOLDEN_SWORD");
    public static final XMaterial GRAY_TERRACOTTA = register("GRAY_TERRACOTTA");
    public static final XMaterial GRAY_WOOL = register("GRAY_WOOL");
    public static final XMaterial GREEN_TERRACOTTA = register("GREEN_TERRACOTTA");
    public static final XMaterial GREEN_WOOL = register("GREEN_WOOL");
    public static final XMaterial IRON_BOOTS = register("IRON_BOOTS");
    public static final XMaterial IRON_HOE = register("IRON_HOE");
    public static final XMaterial IRON_HORSE_ARMOR = register("IRON_HORSE_ARMOR");
    public static final XMaterial IRON_SHOVEL = register("IRON_SHOVEL");
    public static final XMaterial IRON_SWORD = register("IRON_SWORD");
    public static final XMaterial LEATHER_CHESTPLATE = register("LEATHER_CHESTPLATE");
    public static final XMaterial MAP = register("MAP");
    public static final XMaterial NETHER_STAR = register("NETHER_STAR");
    public static final XMaterial OAK_BOAT = register("OAK_BOAT");
    public static final XMaterial OAK_SIGN = register("OAK_SIGN");
    public static final XMaterial ORANGE_DYE = register("ORANGE_DYE");
    public static final XMaterial PURPLE_TERRACOTTA = register("PURPLE_TERRACOTTA");
    public static final XMaterial PURPLE_WOOL = register("PURPLE_WOOL");
    public static final XMaterial REDSTONE_BLOCK = register("REDSTONE_BLOCK");
    public static final XMaterial RED_DYE = register("RED_DYE");
    public static final XMaterial RED_TERRACOTTA = register("RED_TERRACOTTA");
    public static final XMaterial RED_WOOL = register("RED_WOOL");
    public static final XMaterial SADDLE = register("SADDLE");
    public static final XMaterial SKELETON_SKULL = register("SKELETON_SKULL");
    public static final XMaterial SNOW = register("SNOW");
    public static final XMaterial SNOWBALL = register("SNOWBALL");
    public static final XMaterial STONE = register("STONE");
    public static final XMaterial STONE_HOE = register("STONE_HOE");
    public static final XMaterial STONE_PRESSURE_PLATE = register("STONE_PRESSURE_PLATE");
    public static final XMaterial STONE_SHOVEL = register("STONE_SHOVEL");
    public static final XMaterial STONE_SWORD = register("STONE_SWORD");
    public static final XMaterial TNT = register("TNT");
    public static final XMaterial TOTEM_OF_UNDYING = register("TOTEM_OF_UNDYING");
    public static final XMaterial TROPICAL_FISH = register("TROPICAL_FISH");
    public static final XMaterial WATER = register("WATER");
    public static final XMaterial WATER_BUCKET = register("WATER_BUCKET");
    public static final XMaterial WHITE_STAINED_GLASS = register("WHITE_STAINED_GLASS");
    public static final XMaterial WHITE_STAINED_GLASS_PANE = register("WHITE_STAINED_GLASS_PANE");
    public static final XMaterial WHITE_TERRACOTTA = register("WHITE_TERRACOTTA");
    public static final XMaterial WOODEN_HOE = register("WOODEN_HOE");
    public static final XMaterial WOODEN_SHOVEL = register("WOODEN_SHOVEL");
    public static final XMaterial WOODEN_SWORD = register("WOODEN_SWORD");
    public static final XMaterial YELLOW_TERRACOTTA = register("YELLOW_TERRACOTTA");
    public static final XMaterial YELLOW_WOOL = register("YELLOW_WOOL");

    public static XMaterial valueOf(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Material name is empty");
        String name = value.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
        XMaterial cached = VALUES.get(name);
        if (cached != null) return cached;
        Material material = Material.matchMaterial(name);
        if (material == null) throw new IllegalArgumentException("Unknown material: " + value);
        return register(material.name());
    }

    public static XMaterial matchXMaterial(Material material) {
        if (material == null) throw new IllegalArgumentException("Material is null");
        return valueOf(material.name());
    }

    public static XMaterial[] values() { return VALUES.values().toArray(new XMaterial[0]); }
    public String name() { return name; }
    public String getName() { return name; }

    public Material parseMaterial() {
        Material material = Material.matchMaterial(name);
        if (material == null) throw new IllegalArgumentException("Material is unavailable on this server: " + name);
        return material;
    }

    public ItemStack parseItem() { return new ItemStack(parseMaterial()); }

    @Override public boolean equals(Object other) {
        return other instanceof XMaterial && name.equals(((XMaterial) other).name);
    }

    @Override public int hashCode() { return name.hashCode(); }
    @Override public String toString() { return name; }
    /** Legacy durability/data value; modern materials encode variants in their names. */
    public byte getData() { return 0; }

}
