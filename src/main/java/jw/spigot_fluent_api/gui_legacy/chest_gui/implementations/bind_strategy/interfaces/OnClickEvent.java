package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.interfaces;


import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.BindingStrategy;
import org.bukkit.entity.Player;

public interface OnClickEvent<T>
{
    public  void OnClick(Player player, Button button, BindingStrategy<T> bindingStrategy, T currentValue);
}
