package jw.spigot_fluent_api.gui.chest_gui.implementations.button;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonBuilder;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples.BoolenBindStrategy;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples.MaterialBindStrategy;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples.NumberBindStrategy;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples.TextBindStrategy;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.binding.BindingField;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ButtonBuilderChestGUI extends ButtonBuilder<ButtonBuilderChestGUI> {

    private ChestGUI chestGUI;

    public ButtonBuilderChestGUI(ChestGUI chestGUI) {
        this.chestGUI = chestGUI;
    }

    @Override
    public ButtonBuilderChestGUI self() {
        return this;
    }


    public ButtonBuilderChestGUI bindField(BindingStrategy bindingStrTest) {
        bindingStrTest.setChestGUI(chestGUI);
        bindingStrTest.setButton(button);
        return self();
    }

    public ButtonBuilderChestGUI bindField(BindingField bindingField) {
        if (!bindingField.isBinded()) {
            FluentPlugin.logError("Field " +
                                  ChatColor.WHITE + bindingField.getClass() +
                                  ChatColor.DARK_RED + " is not binded");
        }

        BindingStrategy bindingStrategy = null;
        switch (bindingField.getType().getName()) {
            case "java.lang.String":
                bindingStrategy = new TextBindStrategy(bindingField);
                break;
            case "org.bukkit.Material":
                bindingStrategy = new MaterialBindStrategy(bindingField);
                break;
            case "int":
            case "float":
            case "double":
            case "java.lang.Number":
            case "java.lang.Integer":
                bindingStrategy = new NumberBindStrategy(bindingField);
                break;
            case "boolean":
            case "java.lang.Boolean":
                bindingStrategy = new BoolenBindStrategy(bindingField);
                break;
            default:
                Bukkit.getConsoleSender()
                        .sendMessage(ChatColor.DARK_RED + "Field type not supported for binding " +
                                ChatColor.WHITE + bindingField.getType().getTypeName());
                break;
        }
        return bindingStrategy == null ? self() : bindField(bindingStrategy);
    }

    public ButtonBuilderChestGUI setAnimationFrames(Material... frames) {

        return self();
    }


    public Button buildAndAdd() {
        chestGUI.addButton(this.button, this.button.getHeight(), this.button.getWidth());
        return button;
    }


}
