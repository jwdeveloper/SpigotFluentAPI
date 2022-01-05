package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.binding.Observable;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BoolenBindStrategy extends BindingStrategy<Boolean> {


    public BoolenBindStrategy(Observable<Boolean> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button, BindingStrategy<Boolean>  bindingStrategy, Boolean currentValue) {
        boolean value = getValue();
        setValue(!value);
    }
    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, Boolean newValue) {

        String description = newValue ?ChatColor.GREEN+""+newValue: ChatColor.RED+""+newValue;
        description = ChatColor.WHITE+""+ChatColor.BOLD+"Enabled: "+ChatColor.RESET+description;
        button.setHighlighted(newValue);
        button.setDescription(description);
    }


}
