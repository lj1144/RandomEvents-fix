package com.adri1711.randomevents.util;

import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.adri1711.randomevents.RandomEvents;

/** Optional Citizens integration loaded reflectively so Citizens is not a hard dependency. */
public class UtilsCitizen {
    public static volatile boolean check = true;

    public static boolean compruebaCitizensId(Integer number, RandomEvents plugin) {
        return isEnabled(plugin) && number != null && npc(number) != null;
    }

    public static void turnAroundNPC(Integer number, RandomEvents plugin) {
        if (!isEnabled(plugin) || number == null || plugin.getMatchActive() == null
                || !plugin.getMatchActive().getPlaying()) return;
        Object npc = npc(number);
        if (npc != null) face(npc);
    }

    public static void forceLastTurnAroundNPC(Integer number, RandomEvents plugin) {
        if (check || !isEnabled(plugin) || number == null) return;
        Object npc = npc(number);
        if (npc != null) face(npc);
    }

    private static boolean isEnabled(RandomEvents plugin) {
        return plugin.getReventConfig().getIsCitizens()
                && plugin.getServer().getPluginManager().isPluginEnabled("Citizens");
    }

    private static Object npc(int id) {
        try {
            Class<?> api = Class.forName("net.citizensnpcs.api.CitizensAPI");
            Object registry = api.getMethod("getNPCRegistry").invoke(null);
            return registry.getClass().getMethod("getById", int.class).invoke(registry, id);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return null;
        }
    }

    private static void face(Object npc) {
        try {
            Entity entity = (Entity) npc.getClass().getMethod("getEntity").invoke(npc);
            if (!(entity instanceof LivingEntity)) return;
            Location origin = entity.getLocation().clone();
            check = !check;
            Location eye = ((LivingEntity) entity).getEyeLocation().multiply(check ? -0.2 : 0.2);
            Location target = origin.subtract(eye);
            target.setPitch(0.0F);
            target.setY(entity.getLocation().getY());
            for (Method method : npc.getClass().getMethods()) {
                if (method.getName().equals("faceLocation") && method.getParameterCount() == 1
                        && method.getParameterTypes()[0].isAssignableFrom(Location.class)) {
                    method.invoke(npc, target);
                    return;
                }
            }
        } catch (ReflectiveOperationException | LinkageError ignored) {
            // The optional NPC was removed or Citizens changed its API.
        }
    }
}
