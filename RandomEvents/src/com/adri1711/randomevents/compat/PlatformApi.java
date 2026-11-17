package com.adri1711.randomevents.compat;

import java.io.Reader;

import org.bukkit.Bukkit;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.json.GsonFactory;
import com.google.gson.Gson;

/** Bukkit/Paper-only replacement for the version-pinned Lib1711 adapter. */
public final class PlatformApi {
    private final Gson gson = GsonFactory.getCompactGson();

    public String getInventoryName(InventoryClickEvent event) {
        return event.getView().getTitle();
    }

    public String toJson(Object value) {
        return gson.toJson(value);
    }

    public <T> T fromJson(Reader reader, Class<T> type) {
        return gson.fromJson(reader, type);
    }

    public void send(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public void send(CommandSender sender, String first, String clickable, List<String> hover, String command, String last) {
        LegacyComponentSerializer legacy = LegacyComponentSerializer.legacyAmpersand();
        Component result = legacy.deserialize(first == null ? "" : first.replace("<jump>", "\n"));
        Component action = legacy.deserialize(clickable == null ? "" : clickable)
                .clickEvent(ClickEvent.runCommand(command == null ? "" : command));
        if (hover != null && !hover.isEmpty()) {
            Component hoverText = Component.text(String.join("\n", hover));
            action = action.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(hoverText));
        }
        result = result.append(action).append(legacy.deserialize(last == null ? "" : last.replace("<jump>", "\n")));
        sender.sendMessage(result);
    }

    public void usaTitle(Player player, String title, String subtitle) {
        player.sendTitle(title == null ? "" : title, subtitle == null ? "" : subtitle, 0, 50, 10);
    }

    public void createWorldBorder(Player player, Double size, Location center) {
        if (player == null || size == null || center == null || center.getWorld() == null) return;
        WorldBorder border = Bukkit.createWorldBorder();
        border.setCenter(center);
        border.setSize(Math.max(1.0, size));
        player.setWorldBorder(border);
    }

    public void correctDirectionFireball(Fireball fireball, Vector direction) {
        if (fireball != null && direction != null) fireball.setDirection(direction);
    }

    public void convertBlock(Location location, MaterialData data) {
        if (location != null && location.getWorld() != null && data != null) {
            location.getBlock().setType(data.getItemType(), false);
        }
    }

    public Double getHorseSpeed(Horse horse) {
        return horse.getAttribute(Attribute.MOVEMENT_SPEED).getBaseValue();
    }

    public void setHorseSpeed(Horse horse, double speed) {
        horse.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(speed);
    }

    public boolean isCraftHorse(Object entity) { return entity instanceof Horse; }
    public boolean isCraftEnderPearl(Object entity) { return entity instanceof EnderPearl; }
    public boolean isCraftItem(Object entity) { return entity instanceof Item; }
    public boolean isCraftBoat(Object entity) { return entity instanceof Boat; }

    public FastBoard createFastBoard(Player player) {
        return new FastBoard(player);
    }
}
