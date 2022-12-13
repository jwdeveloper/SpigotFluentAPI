package jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events;

import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.enums.StandButtonAction;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public record StandUpdateEvent(Player player, StandButton button, StandButtonAction action) {
}
