package com.adri1711.randomevents.util;

import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.adri1711.randomevents.RandomEvents;

/** Optional LibsDisguises integration loaded only when enabled on the server. */
public class UtilsDisguises {
    public static void disguisePlayer(Player player, RandomEvents plugin) {
        if (!enabled(plugin)) return;
        try {
            ClassLoader loader = plugin.getClass().getClassLoader();
            Class<?> type = Class.forName("me.libraryaddict.disguise.disguisetypes.PlayerDisguise", true, loader);
            Object disguise = type.getConstructor(String.class).newInstance(plugin.getReventConfig().getSkinDisguisePlayers());
            type.getMethod("setEntity", org.bukkit.entity.Entity.class).invoke(disguise, player);
            type.getMethod("setHearSelfDisguise", boolean.class).invoke(disguise, false);
            type.getMethod("setSelfDisguiseVisible", boolean.class).invoke(disguise, false);
            type.getMethod("startDisguise").invoke(disguise);
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().warning("LibsDisguises is installed, but its player disguise API is incompatible.");
        }
    }

    public static void undisguisePlayer(Player player, RandomEvents plugin) {
        if (!enabled(plugin)) return;
        try {
            ClassLoader loader = plugin.getClass().getClassLoader();
            Class<?> api = Class.forName("me.libraryaddict.disguise.DisguiseAPI", true, loader);
            Object disguise = api.getMethod("getDisguise", org.bukkit.entity.Entity.class).invoke(null, player);
            if (disguise == null) return;
            Class<?> playerType = Class.forName("me.libraryaddict.disguise.disguisetypes.PlayerDisguise", true, loader);
            if (playerType.isInstance(disguise)) playerType.getMethod("stopDisguise").invoke(disguise);
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().warning("LibsDisguises API could not remove the player disguise.");
        }
    }

    private static boolean enabled(RandomEvents plugin) {
        return plugin.getReventConfig().getIsLibsDisguises()
                && plugin.getReventConfig().isRandomDisguisePlayers()
                && plugin.getServer().getPluginManager().isPluginEnabled("LibsDisguises");
    }

    public static void addGlow(Player player, RandomEvents plugin) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 3000, 0));
    }

    public static void removeGlow(Player player, RandomEvents plugin) {
        if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
    }
}
