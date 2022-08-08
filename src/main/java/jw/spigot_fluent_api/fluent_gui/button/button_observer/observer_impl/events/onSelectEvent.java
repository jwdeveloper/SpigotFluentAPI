package jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl.events;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUI buttonUI, T data, Player player)
{

}
