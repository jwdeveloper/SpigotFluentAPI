package jw.spigot_fluent_api.gui_legacy.events;

import jw.spigot_fluent_api.gui_legacy.button.Button;
import org.bukkit.entity.Player;

public interface ButtonEvent
{
    void Execute(Player player, Button button);
}