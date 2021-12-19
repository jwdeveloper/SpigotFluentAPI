package jw.spigot_fluent_api.gui.implementation.items_list_ui;

import jw.spigot_fluent_api.gui.implementation.list_ui.ListUI;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemsListUI extends ListUI<Material>
{

    static
    {
        MATERIALS = new ArrayList<Material>(List.of(Material.values()));
    }

    private static ArrayList<Material> MATERIALS;



    public ItemsListUI()
    {
        super("Items", 6);
    }
}
