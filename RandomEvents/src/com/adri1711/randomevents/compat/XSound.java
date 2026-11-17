package com.adri1711.randomevents.compat;

import org.bukkit.Sound;

/** Sound aliases used by RandomEvents, resolved against the running server API. */
public enum XSound {
    BLOCK_ANVIL_BREAK,
    BLOCK_NOTE_BLOCK_BANJO,
    BLOCK_GLASS_BREAK,
    BLOCK_WOODEN_DOOR_CLOSE,
    ENTITY_BAT_HURT,
    ENTITY_ENDERMAN_TELEPORT,
    ENTITY_ENDER_DRAGON_DEATH,
    ENTITY_ENDER_DRAGON_GROWL,
    ENTITY_GENERIC_EXPLODE,
    ENTITY_PLAYER_LEVELUP,
    ENTITY_VILLAGER_DEATH,
    ENTITY_VILLAGER_HURT,
    ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,
    ITEM_TOTEM_USE;

    public Sound parseSound() {
        try {
            return Sound.valueOf(name());
        } catch (IllegalArgumentException missing) {
            return null;
        }
    }
}
