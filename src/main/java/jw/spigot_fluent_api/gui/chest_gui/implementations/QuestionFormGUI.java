package jw.spigot_fluent_api.gui.chest_gui.implementations;

import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.SingleInstanceGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class QuestionFormGUI extends SingleInstanceGUI<ChestGUI> {

    public static QuestionFormGUI instance() {
        if (instance == null) {
            instance = new QuestionFormGUI();
        }
        return instance;
    }

    private static QuestionFormGUI instance;


    @Override
    public ChestGUI setGUI() {
        ChestGUI chestGUI = new ChestGUI(null, "Select", 4)
        {
            @Override
            public void onInitialize()
            {
                this.fillWithMaterial(Material.GRAY_STAINED_GLASS_PANE);
                this.buildButton()
                        .setName("Accept")
                        .setPosition(2,4)
                        .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                        .setOnClick((player1, button) ->
                        {

                        }).buildAndAdd();
                this.buildButton()
                        .setName("Denay")
                        .setPosition(2,2)
                        .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                        .setOnClick((player1, button) ->
                        {

                        }).buildAndAdd();
            }
            @Override
            public void onClose(Player player)
            {
                this.getParent().open(player);
            }
        };

        return chestGUI;
    }

    public static ChestGUI open(Player player,String title, Consumer<Boolean> result)
    {
        ChestGUI gui = instance().getGUI(player);
        gui.setTitle(title);
        return gui;
    }


}
