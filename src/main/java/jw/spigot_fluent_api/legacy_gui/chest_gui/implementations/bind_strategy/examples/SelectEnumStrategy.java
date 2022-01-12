package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import jw.spigot_fluent_api.utilites.binding.Observable;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class SelectEnumStrategy<T extends Enum<T>> extends BindingStrategy<T> {
    private T[] enumValues;
    private int index = 0;

    public SelectEnumStrategy(Observable<T> bindingField, Class<T> tEnum) {
        super(bindingField);
        enumValues = tEnum.getEnumConstants();
    }

    @Override
    protected void onClick(Player player, Button button, BindingStrategy<T> bindingStrategy, T currentIndex) {
        index = (index + 1) % enumValues.length;
        setValue(enumValues[index]);
    }

    @Override
    protected void onValueChanged(ChestGUI inventoryGUI, Button button, T newIndex) {
        String[] description = new String[enumValues.length];
        for (int i = 0; i < enumValues.length; i++) {
            if (i == index) {
                description[i] = ChatColor.WHITE + "" + ChatColor.BOLD + Emoticons.arrowRight + " " + enumValues[i].name();
            } else {
                description[i] = ChatColor.GRAY + "- " + enumValues[i].name();
            }
        }
        button.setDescription(description);
    }
}