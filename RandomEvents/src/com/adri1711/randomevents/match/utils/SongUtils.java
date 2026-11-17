package com.adri1711.randomevents.match.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;

/** Optional NoteBlockAPI bridge. No NoteBlockAPI classes are linked at plugin load time. */
public class SongUtils {
    public static void playRecord(List<Player> players, Boolean playing, RandomEvents plugin) {
        if (players == null) return;
        for (Player player : players) playRecord(player, playing, plugin);
    }

    public static void playRecord(Player player, Boolean playing, RandomEvents plugin) {
        if (player == null || !plugin.getServer().getPluginManager().isPluginEnabled("NoteBlockAPI")) return;
        try {
            ClassLoader loader = plugin.getClass().getClassLoader();
            Class<?> decoder = Class.forName("com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder", true, loader);
            Object song = decoder.getMethod("parse", java.io.InputStream.class).invoke(null,
                    plugin.getResource("Luzverde2.nbs"));
            Class<?> songType = Class.forName("com.xxmicloxx.NoteBlockAPI.model.Song", true, loader);
            Class<?> radioType = Class.forName("com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer", true, loader);
            Constructor<?> constructor = radioType.getConstructor(songType);
            Object radio = constructor.newInstance(song);
            radioType.getMethod("addPlayer", Player.class).invoke(radio, player);
            if (!Boolean.TRUE.equals(playing)) {
                Class<?> api = Class.forName("com.xxmicloxx.NoteBlockAPI.NoteBlockAPI", true, loader);
                api.getMethod("stopPlaying", Player.class).invoke(null, player);
            }
            radioType.getMethod("setPlaying", boolean.class).invoke(radio, Boolean.TRUE.equals(playing));
            Class<?> repeat = Class.forName("com.xxmicloxx.NoteBlockAPI.model.RepeatMode", true, loader);
            @SuppressWarnings({"rawtypes", "unchecked"}) Object all = Enum.valueOf((Class<? extends Enum>) repeat, "ALL");
            radioType.getMethod("setRepeatMode", repeat).invoke(radio, all);
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().warning("NoteBlockAPI is installed, but its API could not play Luzverde2.nbs.");
        }
    }
}
