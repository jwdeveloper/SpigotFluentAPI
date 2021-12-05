package jw.spigot_fluent_api.gui.chest_gui.implementations.button;


import jw.spigot_fluent_api.gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui.InventoryGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui.events.InventoryEvent;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BuilderListGUI<T> {
    private ListGUI<T> chestGUI;
    private List<T> content = new ArrayList<>();
    private String title = "Select";
    private InventoryGUI parent;
    private Material material = Material.BLACK_STAINED_GLASS_PANE;
    private InventoryEvent onSelectEvent = (player, button) -> {};
    private int height = 6;
    private ButtonMapper<T> buttonMapper = (data, button) -> {
    };

    public BuilderListGUI<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public BuilderListGUI<T> setHeight(int height) {
        this.height = height;
        return this;
    }

    public BuilderListGUI<T> setButtonMapping(ButtonMapper<T> buttonMapper) {
        this.buttonMapper = buttonMapper;
        return this;
    }

    public BuilderListGUI<T> setBackGround(Material material) {
        this.material = material;
        return this;
    }

    public BuilderListGUI<T> setParent(InventoryGUI parent) {
        this.parent = parent;
        return this;
    }

    public BuilderListGUI<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    public BuilderListGUI<T> setOnSelect(InventoryEvent inventoryEvent) {
        this.onSelectEvent = inventoryEvent;
        return this;
    }

    public ListGUI<T> build()
    {
        chestGUI = new ListGUI<T>(parent, "Pick up", height) {
            @Override
            public void onInitialize() {



                this.setTitle(title);
                this.setBackgroundMaterial(material);
                this.addButtons(content, buttonMapper);
            }
        };
        return chestGUI;
    }


}
