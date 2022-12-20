package jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers;

import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonObservable<T>
{
    public ButtonUI getButtonUI();

    public void click(Player player);

    public void rightClick(Player player);

    public void refresh();

    public void setValue(T value);

    public T getValue();
}
