package jw.spigot_fluent_api.gui.chest_gui.implementations.select_list.examples;


import jw.spigot_fluent_api.gui.chest_gui.implementations.select_list.SelectGUI;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockSelectGUI extends SelectGUI<Material>
{
    private static List<Material> blocks = new ArrayList<>();
    static
    {
        blocks = Arrays.stream(Material.values()).toList().stream().filter(Material::isBlock).toList();
    }
    public BlockSelectGUI(String name, int size)
    {
        super(name, size);
        this.addButtons(blocks,(material, button) ->
        {
            button.setMaterial(material);
            button.setName(material.name());
        });
    }
}
