package jw.spigot_fluent_api.gui.events;

import jw.spigot_fluent_api.gui.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonUIEvent
{
    void execute(Player player, ButtonUI button);
}
