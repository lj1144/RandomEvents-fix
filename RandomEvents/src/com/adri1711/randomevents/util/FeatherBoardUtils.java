package com.adri1711.randomevents.util;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;

/** Best-effort bridge for the optional FeatherBoard plugin. */
public class FeatherBoardUtils {
    public static void recoverFeatherBoard(RandomEvents plugin, Player player) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("FeatherBoard")) return;
        try {
            Class<?> api = Class.forName("be.maximvdw.featherboard.api.FeatherBoardAPI");
            api.getMethod("toggle", Player.class).invoke(null, player);
            api.getMethod("toggle", Player.class).invoke(null, player);
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().warning("FeatherBoard is installed, but its API is incompatible; scoreboard recovery was skipped.");
        }
    }
}
