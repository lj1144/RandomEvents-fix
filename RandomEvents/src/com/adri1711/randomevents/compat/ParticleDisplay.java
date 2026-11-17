package com.adri1711.randomevents.compat;

import java.util.Objects;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/** Small API-only particle emitter used by RandomEvents' minigame effects. */
public class ParticleDisplay {
    private final Plugin plugin;
    private final Particle particle;
    private final Location origin;

    private ParticleDisplay(Plugin plugin, Location origin, Particle1711 particle) {
        this(plugin, origin, Objects.requireNonNull(particle, "particle").resolve());
    }

    protected ParticleDisplay(Plugin plugin, Location origin, Particle particle) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.origin = Objects.requireNonNull(origin, "origin").clone();
        this.particle = Objects.requireNonNull(particle, "particle");
    }

    public static ParticleDisplay display(Plugin plugin, Location location, Particle1711 particle) {
        return new ParticleDisplay(plugin, location, particle);
    }

    public Location getOrigin() { return origin.clone(); }
    public Plugin getPlugin() { return plugin; }
    public Particle getParticle() { return particle; }

    public void spawn(double x, double y, double z) {
        Location point = origin.clone().add(x, y, z);
        if (point.getWorld() == null) return;
        Particle type = particle;
        if (type.name().equals("DUST") || type.name().equals("REDSTONE")) {
            point.getWorld().spawnParticle(type, point, 1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.RED, 1.0F));
        } else {
            point.getWorld().spawnParticle(type, point, 1, 0, 0, 0, 0);
        }
    }

    public void spawn(Vector offset) { spawn(offset.getX(), offset.getY(), offset.getZ()); }
}
