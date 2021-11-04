package jw.spigot_fluent_api.gui.chest_gui.implementations;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonFactory;
import jw.spigot_fluent_api.gui.events.InventoryEvent;
import jw.spigot_fluent_api.gui.SingleInstanceGUI;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectListGUI extends SingleInstanceGUI<ListGUI> {
    public enum SearchType {
        Players, Materials, PlayerInventory, File, Block
    }

    public static SelectListGUI instnace() {
        if (Instnace == null) {
            Instnace = new SelectListGUI();
        }
        return Instnace;
    }

    private static SelectListGUI Instnace;
    private ArrayList<Button> materials;
    private ArrayList<Button> players_buttons;

    @Override
    public ListGUI setGUI() {
        ListGUI listGUI = new ListGUI(null, "GUI list", 6);
        return listGUI;
    }

    public static ListGUI get(Player player, String title, ArrayList<Button> items, InventoryEvent action) {
        ListGUI gui_list = instnace().getGUI(player);
        gui_list.setName(title);
       // gui_list.selectItem(acction);
        gui_list.clearButtons();
        gui_list.addButtons(items);
        return gui_list;
    }

    public static ListGUI get(Player player, SearchType searchType, InventoryEvent action) {
        ListGUI gui_list = instnace().getGUI(player);
        ArrayList<Button> buttons = new ArrayList<>();
        switch (searchType) {
            case Materials:
                gui_list.setName("Select material");
                gui_list.clearButtons();
                gui_list.addButtons(instnace().getMaterials());
                break;
            case Block:
                gui_list.setName("Select material");
                gui_list.clearButtons();
                gui_list.addButtons(instnace().getMaterials().stream().filter(e ->
                {
                    Material material = e.getHoldingObject();
                    if (material.isBlock())
                        return true;
                    else
                        return false;
                }).collect(Collectors.toList()));

                break;
            case Players:
                gui_list.setName("Select player");
                gui_list.clearButtons();
                gui_list.addButtons(instnace().getPlayers());
                break;
            case PlayerInventory:

                PlayerInventory inventory = player.getInventory();
                Button button;
                for (int i = 0; i < 90; i++) {
                    if (inventory.getItem(i) != null) {
                        button = ButtonFactory.fromItemStack(inventory.getItem(i));
                        button.setObjectHolder(inventory.getItem(i));
                        buttons.add(button);
                    }
                }
                gui_list.setName("Select item");
                gui_list.clearButtons();
                gui_list.addButtons(buttons);
                break;
        }
      //  gui_list.selectItem(acction);
        return gui_list;
    }

    private ArrayList<Button> getMaterials() {
        if (materials == null) {
            materials = new ArrayList<>();
            Arrays.stream(Material.values()).forEach(material ->
            {
                Button button = new Button(material, material.toString());
                button.setObjectHolder(material);
                materials.add(button);
            });
        }
        return materials;
    }

    private ArrayList<Button> getPlayers() {
        if (players_buttons == null) {
            players_buttons = new ArrayList<>();

            Arrays.stream(Bukkit.getServer().getOfflinePlayers()).forEach(e ->
            {
                Button Button = new Button(Material.PLAYER_HEAD, e.getName());
                Button.setObjectHolder(e);
                players_buttons.add(Button);
                Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(), () ->
                {
                    SkullMeta meta = (SkullMeta) Button.getItemMeta();
                    meta.setOwningPlayer(e);
                    Button.setItemMeta(meta);
                });
            });
        }
        return players_buttons;
    }

}