package jw.spigot_fluent_api.gui_v2.test;

import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.gui_v2.chest_ui.ChestUI;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TestUI extends ChestUI
{
    private final DataViewModel dataViewModel;

    public TestUI()
    {
        super("Test", 6);
        setEnableLogs(true);
        dataViewModel = new DataViewModel();
    }

    @Override
    protected void onInitialize()
    {
        ButtonObserverUI.builder()
                .setLocation(2, 5)
                .setMaterial(Material.DIAMOND)
                .setTitle(new MessageBuilder()
                        .color(ChatColor.GOLD)
                        .inBrackets("Change type"))
                .addObserver(dataViewModel.ButtonIncrease())
                .buildAndAdd(this);

        ButtonObserverUI.builder()
                .setLocation(3, 5)
                .setMaterial(Material.DIAMOND)
                .setTitle(new MessageBuilder()
                        .color(ChatColor.GOLD)
                        .inBrackets("Selected Type"))
                .addObserver(dataViewModel.ButtonDisplay())
                .buildAndAdd(this);
    }
}
