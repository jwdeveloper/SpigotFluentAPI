package jw.fluent_api.minecraft.gui.events;

import jw.fluent_api.minecraft.gui.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonUIEvent
{
    void execute(Player player, ButtonUI button);
}
