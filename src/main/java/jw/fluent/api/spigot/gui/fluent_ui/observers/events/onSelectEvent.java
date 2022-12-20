package jw.fluent.api.spigot.gui.fluent_ui.observers.events;

import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUI buttonUI, T data,int index, Player player)
{

}
