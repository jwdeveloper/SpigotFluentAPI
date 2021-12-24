package jw.spigot_fluent_api.gui.implementation.picker_list_ui;

import jw.spigot_fluent_api.gui.implementation.list_ui.ListUI;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class MaterialPickerUI extends PickerUI<Material> {
    private static final List<Material> items;

    static {
        items = Arrays.stream(Material.values()).filter(Material::isItem).toList();
    }

    public MaterialPickerUI(String name) {
        super(name);
        setContentButtons(items,(material, button) ->
        {
            if (material.isAir()) {
                button.setTitle("Empty");
                button.setMaterial(Material.BLACK_STAINED_GLASS_PANE);
                return;
            }
            button.setTitle(material.name());
            button.setMaterial(material);
            button.setDataContext(material);
        });
    }

    public void addFoodFilter() {
        this.addContentFilter(Material::isEdible);
    }

    public void addBlockFilter() {
        this.addContentFilter(input ->
        {
            return input.isSolid() && input.isBlock();
        });
    }

    public void addBurnableFilter() {
        this.addContentFilter(Material::isBurnable);
    }
}
