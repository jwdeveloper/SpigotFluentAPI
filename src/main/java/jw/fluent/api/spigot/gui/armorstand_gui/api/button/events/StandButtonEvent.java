package jw.fluent.api.spigot.gui.armorstand_gui.api.button.events;

import jw.fluent.api.spigot.gui.armorstand_gui.api.button.enums.StandButtonEventType;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import org.bukkit.entity.Player;

public record StandButtonEvent(Player player,StandButton button,  StandButtonEventType eventType)
{

}
