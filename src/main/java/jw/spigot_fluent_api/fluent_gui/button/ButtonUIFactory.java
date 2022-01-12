package jw.spigot_fluent_api.fluent_gui.button;

import jw.spigot_fluent_api.fluent_gui.InventoryUI;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ButtonUIFactory {
    public ButtonUIBuilder goBackButton(InventoryUI parent) {
        return new ButtonUIBuilder<>().setOnClick((player, button) ->
                {
                    parent.open(player);
                }).setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Go back"))
                .setMaterial(Material.ARROW)
                .setLocation(parent.getHeight()-1, parent.getWidth()-1);
    }
}
