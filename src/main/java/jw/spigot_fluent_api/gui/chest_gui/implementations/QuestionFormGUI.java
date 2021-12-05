package jw.spigot_fluent_api.gui.chest_gui.implementations;

import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class QuestionFormGUI extends ChestGUI {

    private Consumer<Boolean> result;

    public QuestionFormGUI() {
        super("Are you sure?", 3);
    }

    public void open(Player player, String title, Consumer<Boolean> result)
    {
       this.result =result;
       this.setTitle(title);
       this.open(player);
    }
    @Override
    public void onClose(Player player)
    {
        this.getParent().open(player);
    }

    @Override
    public void onInitialize() {
        this.fillWithMaterial(Material.BLACK_STAINED_GLASS_PANE);
        this.buildButton()
                .setName("Accept")
                .setPosition(1,5)
                .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                .setOnClick((player1, button) ->
                {
                    result.accept(true);
                }).buildAndAdd();
        this.buildButton()
                .setName("Denay")
                .setPosition(1,3)
                .setMaterial(Material.RED_STAINED_GLASS_PANE)
                .setOnClick((player1, button) ->
                {
                    result.accept(false);
                }).buildAndAdd();
    }
}
