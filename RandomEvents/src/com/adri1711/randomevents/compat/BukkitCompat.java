package com.adri1711.randomevents.compat;

import java.lang.reflect.Field;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

/** Resolves Bukkit names that changed across Minecraft server generations. */
public final class BukkitCompat {
    private BukkitCompat() {}

    public static PotionEffectType potion(String modernName, String legacyName) {
        PotionEffectType type = PotionEffectType.getByName(modernName);
        if (type == null && legacyName != null) type = PotionEffectType.getByName(legacyName);
        if (type == null) throw new IllegalStateException("Potion effect is unavailable: " + modernName);
        return type;
    }

    public static EntityType entityType(String modernName, String legacyName) {
        Object type = fieldValue(EntityType.class, modernName);
        if (type == null && legacyName != null) type = fieldValue(EntityType.class, legacyName);
        if (type == null) throw new IllegalStateException("Entity type is unavailable: " + modernName);
        return (EntityType) type;
    }

    public static Attribute attribute(String modernName, String legacyName) {
        Object attribute = fieldValue(Attribute.class, modernName);
        if (attribute == null && legacyName != null) attribute = fieldValue(Attribute.class, legacyName);
        if (attribute == null) throw new IllegalStateException("Attribute is unavailable: " + modernName);
        return (Attribute) attribute;
    }

    public static ItemFlag hidePotionDetailsFlag() {
        Object flag = fieldValue(ItemFlag.class, "HIDE_ADDITIONAL_TOOLTIP");
        if (flag == null) flag = fieldValue(ItemFlag.class, "HIDE_POTION_EFFECTS");
        if (flag == null) throw new IllegalStateException("No supported item tooltip flag is available");
        return (ItemFlag) flag;
    }

    private static Object fieldValue(Class<?> type, String name) {
        try {
            Field field = type.getField(name);
            return field.get(null);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return null;
        }
    }
}
