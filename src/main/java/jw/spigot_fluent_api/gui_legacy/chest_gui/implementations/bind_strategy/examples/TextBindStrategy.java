package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.binding.Observable;
import org.bukkit.entity.Player;

public class TextBindStrategy  extends BindingStrategy<String> {


    public TextBindStrategy(Observable<String> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button, BindingStrategy<String> bindingStrategy, String currentValue) {

    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, String newValue) {

    }
}