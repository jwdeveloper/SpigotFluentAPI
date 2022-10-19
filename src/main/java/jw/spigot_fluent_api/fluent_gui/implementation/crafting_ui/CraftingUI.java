package jw.spigot_fluent_api.fluent_gui.implementation.crafting_ui;

import jw.spigot_fluent_api.fluent_gui.InventoryUI;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CraftingUI extends InventoryUI
{

    private boolean initialized;

    public CraftingUI(String name)
    {
        super(name, 1, InventoryType.WORKBENCH);
        setEnableLogs(true);
    }

    protected void onInitialize() {
    }



    @Override
    protected void doClick(Player player, int index, ItemStack itemStack, InventoryInteractEvent interactEvent) {

    }

    @Override
    protected void onClick(Player player, ButtonUI button) {

    }

    @Override
    protected void onRefresh(Player player) {

    }

    @Override
    protected void onOpen(Player player) {

    }

    @Override
    protected void onClose(Player player) {

    }

    @Override
    protected int calculateButtonSlotIndex(ButtonUI buttonUI)
    {
        FluentLogger.log("widht",buttonUI.getTitle());
        return buttonUI.getWidth();
    }

    protected List<ButtonUI> mapRecipe(ShapedRecipe recipe)
    {
        var result = new ArrayList<ButtonUI>();
        var shape = recipe.getShape();
        result.add(mapButton(recipe.getResult()));
        for(var i =0;i<shape.length;i++)
        {
            for(var j =0;j<3;j++)
            {
                var item = recipe.getIngredientMap().get(shape[i].charAt(j));
                var btn = mapButton(item);
                result.add(btn);
            }
        }
        return result;
    }

    protected ButtonUI mapButton(ItemStack itemStack)
    {
        return ButtonUI.builder().fromItemStack(itemStack).build();
    }

    private void initialize() {
        if (!initialized) {
            initialized = true;
            FluentLogger.log("siema2");
            displayLog("Initialization", ChatColor.GREEN);
            onInitialize();
            FluentLogger.log("siema3");
        }
    }

    @Override
    public final void open(Player player) {
        initialize();
        super.refresh();
        super.open(player);
    }
}