package jw.fluent_api.minecraft.gui.button.button_observer.observer_impl.events;

import jw.fluent_api.minecraft.gui.button.ButtonUI;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUI buttonUI, T data, Player player)
{

}
