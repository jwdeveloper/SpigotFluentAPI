package jw.fluent.api.spigot.gui.inventory_gui.implementation.items_list_ui;

import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.ListUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemsSearchUI extends ListUI<ItemsSearchUI.MaterialDto> {

    static {
        var materials = new ArrayList<Material>(List.of(Material.values()));
        var result = new ArrayList<MaterialDto>();
        for (var material : materials) {
            var name = material.name().toLowerCase();
            name = ChatColor.stripColor(name);
            name = name.replace("_", " ");

            result.add(new MaterialDto(name, material));
        }
        MATERIALS = result;
    }

    private static List<MaterialDto> MATERIALS;


    public record MaterialDto(String displayedName, Material material) {
    }


    public ItemsSearchUI() {
        super("Items", 6);
    }


    @Override
    protected void onInitialize() {
        setContentButtons(MATERIALS, (data, button) ->
        {
            button.setTitle(data.displayedName());
            button.setMaterial(data.material());
            button.setDataContext(data.material());
        });

        addSearchStrategy("By name",event ->
        {
            return event.data().displayedName().contains(event.searchKey());
        });

        addSearchStrategy("By last name",event ->
        {
            return event.data().displayedName().contains("wood");
        });
    }
}
