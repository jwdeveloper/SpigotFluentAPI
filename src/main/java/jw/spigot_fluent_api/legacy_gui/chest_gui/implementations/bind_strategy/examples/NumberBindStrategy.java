package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.desing_patterns.observer.fields.Observable;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NumberBindStrategy extends BindingStrategy<Number> {


    public NumberBindStrategy(Observable<Number> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button, BindingStrategy<Number> bindingStrategy, Number currentValue) {

    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, Number newValue)
    {
          button.setDescription(ChatColor.DARK_GREEN+this.getFieldName()+": "+this.getValue());
    }
}