package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples;

import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.SelectListGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.binding.BindingField;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileStrategy extends BindingStrategy<String> {

    private final String path;
    private final String[] extentions;
    private  Material material = Material.PAPER;

    public FileStrategy(BindingField<String> bindingField, Material material, String path, String... extentions)
    {
        this(bindingField,path,extentions);
        this.material = material;
    }
    public FileStrategy(BindingField<String> bindingField, String path, String... extentions)
    {
        super(bindingField);
        this.path = path;
        this.extentions = extentions;
    }

    @Override
    public void onClick(Player player, Button button, BindingStrategy<String> bindingStrategy, String currentValue)
    {
        SelectListGUI.get(player,"Select file", Files(),(player2, button1) ->
        {
            setValue(button1.getHoldingObject());
            chestGUI.open(player);
        }).setParent(chestGUI).open(player);
    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, String newValue)
    {
        button.setDescription("File: "+ newValue);
    }
    private ArrayList<Button> Files()
    {
        return (ArrayList<Button>) FileUtility

                .getFolderFilesName(path,extentions)
                .stream()
                .map(name ->
                 {
                    Button button = new Button(material,name);
                    button.setObjectHolder(name);
                    return button;
                 }).collect(Collectors.toList());
    }
}