package jw.spigot_fluent_api.fluent_gui.button;

import jw.spigot_fluent_api.fluent_gui.InventoryUI;
import jw.spigot_fluent_api.fluent_gui.enums.ButtonType;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import jw.spigot_fluent_api.fluent_plugin.default_actions.implementation.languages.Lang;
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
            result.setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets(Lang.get("gui.base.exit.title")));
        else
            result.setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets(Lang.get("gui.base.back.title")));
        return result;
    }

    public ButtonUIBuilder backgroundButton(int height, int width, Material material) {
        return new ButtonUIBuilder<>()
                .setLocation(height,width)
                .setMaterial(material)
                .setButtonType(ButtonType.BACKGROUND)
                .setTitle(" ");
    }

    public ButtonUIBuilder goBackButton(InventoryUI inventory) {
        return goBackButton(inventory, null);
    }
}
