package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.examples;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import jw.spigot_fluent_api.utilites.binding.BindingField;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MaterialBindStrategy extends BindingStrategy<Material> {


    public MaterialBindStrategy(BindingField<Material> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button, BindingStrategy<Material> bindingStrategy, Material currentValue)
    {
       /* SelectListGUI.get(player, SelectListGUI.SearchType.Materials, (player1, button1) ->
        {
            setValue(button1.getHoldingObject());
            this.chestGUI.open(player1);
        }).setParent(chestGUI).open(player);*/
    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, Material newValue)
    {
        if(newValue == null)
            return;

        button.setMaterial(newValue);
        button.addDescription(ChatColor.WHITE+ Emoticons.arrowright+" "+newValue.name());
    }

}
