package jw.spigot_fluent_api.gui.chest_gui.implementations.select_list;


import jw.spigot_fluent_api.gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui.events.InventoryEvent;
import jw.spigot_fluent_api.gui.chest_gui.implementations.ListGUI;
import org.bukkit.entity.Player;

public class SelectGUI<T> extends ListGUI<T>
{
    public enum SearchType {
        PLAYERS, MATERIALS, INVENTORY, FILE, BLOCK, COLORS, FOOD
    }

    public SelectGUI(String name, int size)
    {
        super(null, name, size);
    }

    public SelectGUI setTitleName(String title) {
        super.setTitle(title);
        return this;
    }

    public SelectGUI onSelect(InventoryEvent event)
    {
        this.onSelect = event;
        return this;
    }
    @Override
    public void onOpen(Player player)
    {
        this.currentAcction.set(ButtonActionsEnum.GET);
        super.onOpen(player);
    }

    @Override
    public void onClose(Player player)
    {
        this.currentAcction.set(ButtonActionsEnum.EMPTY);
        super.onClose(player);
    }
}
