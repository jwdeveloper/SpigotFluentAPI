package jw.spigot_fluent_api.gui_v2.events;

import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonUIEvent
{
    void Execute(Player player, ButtonUI button);
}
