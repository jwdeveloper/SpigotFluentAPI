package jw.spigot_fluent_api.fluent_gui.button;

import jw.spigot_fluent_api.fluent_gui.InventoryUI;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ButtonUIFactory {
    public ButtonUIBuilder goBackButton(InventoryUI inventory, InventoryUI parent) {
        var result = new ButtonUIBuilder<>().setOnClick((player, button) ->
        {
            if (parent == null) {
                inventory.close();
                return;
            }
            parent.open(player);
        }).setMaterial(Material.ARROW)
                .setLocation(inventory.getHeight() - 1, inventory.getWidth() - 1);

        if (parent == null)
            result.setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Exit"));
        else
            result.setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets("Go back"));
        return result;
    }

    public ButtonUIBuilder goBackButton(InventoryUI inventory) {
        return goBackButton(inventory, null);
    }
}
