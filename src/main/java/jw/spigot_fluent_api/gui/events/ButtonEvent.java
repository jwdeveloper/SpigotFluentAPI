package jw.spigot_fluent_api.gui.events;

import jw.spigot_fluent_api.gui.button.Button;
import org.bukkit.entity.Player;

public interface ButtonEvent
{
    void Execute(Player player, Button button);
}
