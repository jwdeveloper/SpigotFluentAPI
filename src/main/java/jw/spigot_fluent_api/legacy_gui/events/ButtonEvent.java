package jw.spigot_fluent_api.legacy_gui.events;

import jw.spigot_fluent_api.legacy_gui.button.Button;
import org.bukkit.entity.Player;

public interface ButtonEvent
{
    void Execute(Player player, Button button);
}
