package jw.fluent.api.spigot.inventory_gui.events;

import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonUIEvent
{
    void execute(Player player, ButtonUI button);
}
