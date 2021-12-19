package jw.spigot_fluent_api.gui.implementation.accept_ui;

import jw.spigot_fluent_api.gui.button.ButtonUI;
import jw.spigot_fluent_api.gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class AcceptUI extends ChestUI
{
    private  Consumer<Boolean> onUserChoice;
    private  ButtonUI acceptButton;
    private  ButtonUI denayButton;
    private final Material backGroundMaterial = Material.GRAY_STAINED_GLASS_PANE;

    public AcceptUI(String name, int height)
    {
        super(name, height);
    }

    @Override
    protected void onInitialize()
    {
        setFillMaterial(backGroundMaterial);
        acceptButton =ButtonUI.builder()
                .setLocation(getHeight()/2,3)
                .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                .setTitle(new MessageBuilder().color(ChatColor.DARK_GREEN).inBrackets("Accept"))
                .setOnClick(this::onAccept)
                .buildAndAdd(this);

        denayButton =ButtonUI.builder()
                .setLocation(getHeight()/2,5)
                .setMaterial(Material.RED_STAINED_GLASS_PANE)
                .setTitle(new MessageBuilder().color(ChatColor.DARK_RED).inBrackets("Denay"))
                .setOnClick(this::onDenay)
                .buildAndAdd(this);
    }

    private void onAccept(Player player, ButtonUI button)
    {
        if(onUserChoice == null)
            return;
        onUserChoice.accept(true);
    }

    private void onDenay(Player player, ButtonUI button)
    {
        if(onUserChoice == null)
            return;
        onUserChoice.accept(false);
    }

    public AcceptUI onUserChoice(Consumer<Boolean> onUserChoice)
    {
            this.onUserChoice = onUserChoice;
            return this;
    }

    public void open(Player player, String title)
    {
        open(player);
        setTitle(title);
    }
}
