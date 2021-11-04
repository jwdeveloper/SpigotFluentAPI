package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import jw.spigot_fluent_api.utilites.binding.BindingField;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class SelectPopUpStrategy extends BindingStrategy<Integer> {
    private String[] options;

    public SelectPopUpStrategy(BindingField<Integer> bindingField, String... options) {
        super(bindingField);
        this.options = options;
    }

    @Override
    protected void onClick(Player player, Button button, BindingStrategy<Integer> bindingStrategy, Integer currentIndex) {
        setValue((currentIndex + 1) % options.length);
    }

    @Override
    protected void onValueChanged(ChestGUI inventoryGUI, Button button, Integer newIndex) {
        String[] description = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            if (i == newIndex) {
                description[i] = ChatColor.WHITE + "" + ChatColor.BOLD + Emoticons.arrowright + " " + options[i];
            } else {
                description[i] = ChatColor.GRAY + "- " + options[i];
            }
        }
        button.setDescription(description);
    }
}
