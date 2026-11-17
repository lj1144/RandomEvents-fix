package com.adri1711.randomevents.compat;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/** Shape helpers using only Bukkit's stable particle API. */
public final class XParticle {
    private XParticle() {}

    public static void line(Location start, Location end, double spacing, ParticleDisplay display) {
        if (start == null || end == null || start.getWorld() == null || !start.getWorld().equals(end.getWorld())) return;
        Vector3 delta = new Vector3(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ());
        double length = Math.sqrt(delta.x * delta.x + delta.y * delta.y + delta.z * delta.z);
        int steps = clamp((int) Math.ceil(length / Math.max(0.1, spacing)), 1, 700);
        Location origin = display.getOrigin();
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            display.spawn(start.getX() + delta.x * t - origin.getX(),
                    start.getY() + delta.y * t - origin.getY(),
                    start.getZ() + delta.z * t - origin.getZ());
        }
    }

    public static void circle(double radius, double rate, ParticleDisplay d) {
        ellipse(radius, radius, rate, d);
    }

    public static void ellipse(double rx, double rz, double rate, ParticleDisplay d) {
        int points = clamp((int) Math.round(Math.abs(rate)), 12, 240);
        for (int i = 0; i < points; i++) {
            double angle = Math.PI * 2 * i / points;
            d.spawn(Math.cos(angle) * rx, 0, Math.sin(angle) * rz);
        }
    }

    public static void crescent(double radius, double rate, ParticleDisplay d) {
        int points = clamp((int) Math.round(Math.abs(rate)), 12, 180);
        for (int i = 0; i <= points; i++) {
            double angle = Math.PI * (0.15 + 1.7 * i / points);
            d.spawn(Math.cos(angle) * radius, 0, Math.sin(angle) * radius);
        }
    }

    public static void cylinder(double height, double radius, double rate, ParticleDisplay d) {
        int levels = clamp((int) Math.ceil(Math.abs(height) * 3), 2, 80);
        for (int y = 0; y <= levels; y++) {
            double dy = height * y / levels;
            ellipse(radius, radius, rate, new OffsetDisplay(d, 0, dy, 0));
        }
    }

    public static void diamond(double width, double rate, double height, ParticleDisplay d) {
        double[][] p = {{0,height,0},{width,0,0},{0,-height,0},{-width,0,0},{0,height,0}};
        for (int i = 0; i < p.length - 1; i++) line(new Location(d.getOrigin().getWorld(), p[i][0], p[i][1], p[i][2]),
                new Location(d.getOrigin().getWorld(), p[i+1][0], p[i+1][1], p[i+1][2]), 0.25, d);
    }

    public static void eye(double rx, double rz, double rate, double extension, ParticleDisplay d) {
        ellipse(rx, rz, rate, new OffsetDisplay(d, 0, extension, 0));
        ellipse(rx, rz, rate, new OffsetDisplay(d, 0, -extension, 0));
    }

    public static void filledCircle(double radius, double rate, double spacing, ParticleDisplay d) {
        double step = Math.max(0.2, Math.min(2.0, Math.abs(spacing)));
        int rings = clamp((int) Math.ceil(Math.abs(radius) / step), 1, 40);
        for (int ring = 1; ring <= rings; ring++) circle(radius * ring / rings, rate, d);
    }

    public static void illuminati(double size, double extension, ParticleDisplay d) {
        double h = Math.max(0.5, size);
        Location c = d.getOrigin();
        Location a = c.clone().add(0, extension, 0);
        Location b = c.clone().add(-h, -extension, 0);
        Location z = c.clone().add(h, -extension, 0);
        line(a, b, 0.25, d); line(b, z, 0.25, d); line(z, a, 0.25, d);
    }

    public static void infinity(double radius, double rate, ParticleDisplay d) {
        int points = clamp((int) Math.round(Math.abs(rate)), 24, 240);
        for (int i = 0; i < points; i++) {
            double t = Math.PI * 2 * i / points;
            d.spawn(radius * Math.cos(t) / (1 + Math.sin(t) * Math.sin(t)), 0,
                    radius * Math.sin(t) * Math.cos(t) / (1 + Math.sin(t) * Math.sin(t)));
        }
    }

    public static void ring(double rate, double rx, double rz, ParticleDisplay d) { ellipse(rx, rz, rate, d); }

    public static void sphere(double radius, double rate, ParticleDisplay d) {
        int latitudes = clamp((int) Math.round(Math.abs(rate) / 2), 6, 36);
        for (int i = 0; i <= latitudes; i++) {
            double phi = Math.PI * i / latitudes;
            double y = radius * Math.cos(phi);
            double ringRadius = radius * Math.sin(phi);
            ellipse(ringRadius, ringRadius, Math.max(12, rate), new OffsetDisplay(d, 0, y, 0));
        }
    }

    public static void blackSun(double radius, double radiusRate, double rate, double rateChange, ParticleDisplay d) {
        circle(radius, rate, d);
        int rays = clamp((int) Math.round(Math.abs(radiusRate)), 6, 48);
        for (int i = 0; i < rays; i++) {
            double angle = Math.PI * 2 * i / rays;
            line(d.getOrigin(), d.getOrigin().clone().add(Math.cos(angle) * radius, 0, Math.sin(angle) * radius),
                    Math.max(0.2, Math.abs(rateChange)), d);
        }
    }

    public static void meguminExplosion(Plugin plugin, double size, ParticleDisplay d) {
        int count = clamp((int) Math.round(Math.abs(size) * 20), 12, 160);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble(-size, size);
            double y = random.nextDouble(-size, size);
            double z = random.nextDouble(-size, size);
            d.spawn(x, y, z);
        }
    }

    private static int clamp(int value, int min, int max) { return Math.max(min, Math.min(max, value)); }

    private static final class Vector3 {
        final double x, y, z;
        Vector3(double x, double y, double z) { this.x=x; this.y=y; this.z=z; }
    }

    private static final class OffsetDisplay extends ParticleDisplay {
        private final ParticleDisplay parent;
        private final double x, y, z;
        OffsetDisplay(ParticleDisplay parent, double x, double y, double z) {
            super(parent.getPlugin(), parent.getOrigin(), parent.getParticle());
            this.parent=parent; this.x=x; this.y=y; this.z=z;
        }
        @Override public void spawn(double dx, double dy, double dz) { parent.spawn(dx+x, dy+y, dz+z); }
    }
}
