package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.binding.BindingField;
import org.bukkit.entity.Player;

public class TextBindStrategy  extends BindingStrategy<String> {


    public TextBindStrategy(BindingField<String> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button, BindingStrategy<String> bindingStrategy, String currentValue) {

    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, String newValue) {

    }
}