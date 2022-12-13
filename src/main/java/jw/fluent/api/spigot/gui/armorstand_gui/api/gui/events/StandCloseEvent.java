package jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public record StandCloseEvent(Player player, Location location) {
}