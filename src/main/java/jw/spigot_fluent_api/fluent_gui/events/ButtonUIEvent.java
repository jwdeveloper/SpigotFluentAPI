package jw.spigot_fluent_api.fluent_gui.events;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonUIEvent
{
    void execute(Player player, ButtonUI button);
}
