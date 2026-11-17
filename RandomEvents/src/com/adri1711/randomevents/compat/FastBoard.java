package com.adri1711.randomevents.compat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/** Sidebar scoreboard implemented with Bukkit's public scoreboard API. */
public final class FastBoard {
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacySection();
    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final List<Team> lineTeams = new ArrayList<>();

    public FastBoard(Player player) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(
                "revent_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12),
                Criteria.DUMMY, LEGACY.deserialize("RandomEvents"));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    public Player getPlayer() { return player; }

    public void updateTitle(String title) {
        objective.displayName(LEGACY.deserialize(title == null ? "" : title));
    }

    public void updateLines(List<String> lines) {
        clearLines();
        if (lines == null) return;
        int visible = Math.min(lines.size(), 15);
        ChatColor[] colors = ChatColor.values();
        for (int i = 0; i < visible; i++) {
            String entry = colors[i].toString();
            Team team = scoreboard.registerNewTeam("rv" + i);
            team.addEntry(entry);
            team.prefix(LEGACY.deserialize(lines.get(i) == null ? "" : lines.get(i)));
            objective.getScore(entry).setScore(visible - i);
            lineTeams.add(team);
        }
    }

    public void delete() {
        clearLines();
        objective.unregister();
    }

    private void clearLines() {
        for (Team team : lineTeams) {
            for (String entry : new ArrayList<>(team.getEntries())) scoreboard.resetScores(entry);
            team.unregister();
        }
        lineTeams.clear();
    }
}
