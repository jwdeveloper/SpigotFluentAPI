package jw.spigot_fluent_api.gui.events;

import jw.spigot_fluent_api.gui.button.Button;
import org.bukkit.entity.Player;

public interface InventoryEvent
{
    public void Execute(Player player, Button button);
}
