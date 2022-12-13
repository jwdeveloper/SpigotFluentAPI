package jw.fluent.api.spigot.gui.armorstand_gui.api;

import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import org.bukkit.entity.Player;

public interface ArmorStandGui
{
    void open(Player player);

    void close();

    void update();

    void addButton(StandButton standButton);

    public void removeButton(StandButton standButton);
}
