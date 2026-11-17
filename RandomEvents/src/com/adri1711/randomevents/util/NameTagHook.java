package com.adri1711.randomevents.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;

/** Optional NametagEdit bridge. */
public class NameTagHook {
    private final RandomEvents plugin;
    private final Object api;

    public NameTagHook(RandomEvents plugin) {
        this.plugin = plugin;
        this.api = createApi(plugin);
    }

    private static Object createApi(RandomEvents plugin) {
        try {
            Object edit = plugin.getServer().getPluginManager().getPlugin("NametagEdit");
            if (edit == null) return null;
            Object handler = edit.getClass().getMethod("getHandler").invoke(edit);
            Object manager = edit.getClass().getMethod("getManager").invoke(edit);
            Class<?> apiClass = Class.forName("com.nametagedit.plugin.api.NametagAPI", true,
                    plugin.getClass().getClassLoader());
            for (Constructor<?> constructor : apiClass.getConstructors()) {
                if (constructor.getParameterCount() == 2
                        && constructor.getParameterTypes()[0].isInstance(handler)
                        && constructor.getParameterTypes()[1].isInstance(manager)) {
                    return constructor.newInstance(handler, manager);
                }
            }
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().warning("NametagEdit is installed, but its API could not be connected.");
        }
        return null;
    }

    public RandomEvents getPlugin() { return plugin; }
    public NameTagHook getApi() { return api == null ? null : this; }

    public void setPrefix(Player player, String prefix) {
        invoke("setPrefix", player, prefix);
    }

    public Tag getNametag(Player player) {
        Object result = invoke("getNametag", player);
        return new Tag(result);
    }

    private Object invoke(String name, Object... args) {
        if (api == null) return null;
        try {
            for (Method method : api.getClass().getMethods()) {
                if (!method.getName().equals(name) || method.getParameterCount() != args.length) continue;
                boolean compatible = true;
                for (int i = 0; i < args.length; i++)
                    compatible &= args[i] == null || method.getParameterTypes()[i].isInstance(args[i]);
                if (compatible) return method.invoke(api, args);
            }
        } catch (ReflectiveOperationException | LinkageError e) {
            plugin.getLogger().warning("NametagEdit API call failed: " + name);
        }
        return null;
    }

    public static final class Tag {
        private final Object tag;
        private Tag(Object tag) { this.tag = tag; }
        public String getPrefix() {
            if (tag == null) return "";
            try {
                Object prefix = tag.getClass().getMethod("getPrefix").invoke(tag);
                return prefix == null ? "" : prefix.toString();
            } catch (ReflectiveOperationException e) { return ""; }
        }
    }
}
