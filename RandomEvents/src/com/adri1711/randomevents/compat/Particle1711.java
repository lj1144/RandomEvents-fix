package com.adri1711.randomevents.compat;

import java.util.Locale;
import org.bukkit.Particle;

/** Legacy particle names from saved configs, mapped onto current Bukkit particles. */
public enum Particle1711 {
    EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE,
    WATER_SPLASH, WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH, CRIT,
    CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT,
    SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA,
    VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL,
    ENCHANTMENT_TABLE, FLAME, LAVA, CLOUD, REDSTONE,
    SNOWBALL, SNOW_SHOVEL, SLIME, HEART, BARRIER,
    ITEM_CRACK, BLOCK_CRACK, BLOCK_DUST, WATER_DROP, MOB_APPEARANCE,
    DRAGON_BREATH, END_ROD, DAMAGE_INDICATOR, SWEEP_ATTACK, FALLING_DUST, FOOTSTEP, ITEM_TAKE;
    public Particle resolve() {
        String current;
        switch (this) {
            case EXPLOSION_NORMAL: current = "POOF"; break;
            case EXPLOSION_LARGE: current = "EXPLOSION"; break;
            case EXPLOSION_HUGE: current = "EXPLOSION_EMITTER"; break;
            case FIREWORKS_SPARK: current = "FIREWORK"; break;
            case WATER_BUBBLE: current = "BUBBLE"; break;
            case WATER_SPLASH: current = "SPLASH"; break;
            case WATER_WAKE: current = "FISHING"; break;
            case SUSPENDED: case SUSPENDED_DEPTH: current = "UNDERWATER"; break;
            case CRIT_MAGIC: current = "ENCHANTED_HIT"; break;
            case SMOKE_NORMAL: current = "SMOKE"; break;
            case SMOKE_LARGE: current = "LARGE_SMOKE"; break;
            case SPELL: current = "EFFECT"; break;
            case SPELL_INSTANT: current = "INSTANT_EFFECT"; break;
            case SPELL_MOB: case SPELL_MOB_AMBIENT: current = "ENTITY_EFFECT"; break;
            case SPELL_WITCH: current = "WITCH"; break;
            case DRIP_WATER: current = "DRIPPING_WATER"; break;
            case DRIP_LAVA: current = "DRIPPING_LAVA"; break;
            case VILLAGER_ANGRY: current = "ANGRY_VILLAGER"; break;
            case VILLAGER_HAPPY: current = "HAPPY_VILLAGER"; break;
            case TOWN_AURA: current = "MYCELIUM"; break;
            case ENCHANTMENT_TABLE: current = "ENCHANT"; break;
            case REDSTONE: current = "DUST"; break;
            case SNOWBALL: current = "ITEM_SNOWBALL"; break;
            case SLIME: current = "ITEM_SLIME"; break;
            case ITEM_CRACK: current = "ITEM"; break;
            case BLOCK_CRACK: case BLOCK_DUST: current = "BLOCK"; break;
            case MOB_APPEARANCE: current = "ELDER_GUARDIAN"; break;
            case FOOTSTEP: current = "CLOUD"; break;
            case ITEM_TAKE: current = "ITEM"; break;
            default: current = name();
        }
        try {
            return Particle.valueOf(current);
        } catch (IllegalArgumentException missing) {
            try {
                return Particle.valueOf(name());
            } catch (IllegalArgumentException unavailable) {
                return Particle.CLOUD;
            }
        }
    }

    public static Particle1711 fromConfig(String name) {
        if (name == null || name.isBlank()) return CLOUD;
        String normalized = name.trim().toUpperCase(Locale.ROOT);
        try {
            return valueOf(normalized);
        } catch (IllegalArgumentException ignored) {
            try {
                return valueOf(Particle.valueOf(normalized).name());
            } catch (IllegalArgumentException alsoIgnored) {
                return CLOUD;
            }
        }
    }
}
