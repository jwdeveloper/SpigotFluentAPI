package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.interfaces;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import org.bukkit.entity.Player;

public interface OnClickEvent<T>
{
    public  void OnClick(Player player, Button button, BindingStrategy<T> bindingStrategy, T currentValue);
}
