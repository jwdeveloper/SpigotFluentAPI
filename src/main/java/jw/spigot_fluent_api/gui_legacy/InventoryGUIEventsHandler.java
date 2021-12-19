package jw.spigot_fluent_api.gui_legacy;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class InventoryGUIEventsHandler implements Listener {
    private  static InventoryGUIEventsHandler instance;
    private final ArrayList<InventoryGUI> inventoriesGui = new ArrayList();
    private final HashMap<Player,Consumer<String>> textInputEvents = new HashMap<>();
    public static InventoryGUIEventsHandler Instance() {
        if (instance == null) {
            instance = new InventoryGUIEventsHandler();
        }
        return instance;
    }

    public InventoryGUIEventsHandler() {
        Bukkit.getPluginManager().registerEvents(this, FluentPlugin.getPlugin());
    }

    public void registerTextInput(Player player,Consumer<String> event)
    {
        if(textInputEvents.containsKey(player))
        {
            textInputEvents.replace(player,event);
        }
        else
        {
            textInputEvents.put(player,event);
        }
    }
    public void register(InventoryGUI InventoryGUIBase) {
        if (!inventoriesGui.contains(InventoryGUIBase)) {
            inventoriesGui.add(InventoryGUIBase);
        }
    }
    public void unregister(InventoryGUI InventoryGUIBase) {
        inventoriesGui.remove(InventoryGUIBase);
    }
    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event)
    {
        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui)
        {
            inventory = inventoryGUI.inventory;
            if (inventory == null || !inventoryGUI.isOpen()) continue;
            if (event.getInventory() == inventory) {
                inventoryGUI.refresh();
                break;
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui) {
            inventory = inventoryGUI.inventory;
            if (inventory == null || !inventoryGUI.isOpen()) continue;

            if (event.getInventory() == inventory) {
                inventoryGUI.close();
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        if (event.getRawSlot() == -999)
            return;

        Inventory inventory;
            for (InventoryGUI inventoryGUI : inventoriesGui)
            {
                inventory = inventoryGUI.inventory;
                if (inventory == null || !inventoryGUI.isOpen()) continue;

                if (event.getInventory() == inventory)
                {
                    event.setCancelled(true);
                    final ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
                        inventoryGUI.doClick((Player)event.getWhoClicked(), event.getRawSlot(), clickedItem,event);
                    break;
                }
            }
    }

    @EventHandler
    private void onPlayerExit(PlayerQuitEvent event) {
        for(int i=0;i<inventoriesGui.size();i++)
        {
            if (event.getPlayer() == inventoriesGui.get(i).player) {
                inventoriesGui.get(i).close();
                this.unregister(inventoriesGui.get(i));
                return;
            }
        }
    }
    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(FluentPlugin.getPlugin()))
        {
            InventoryGUI inventoryGUI = null;
            for(int i=0;i<inventoriesGui.size();i++)
            {
                inventoriesGui.get(i).close();
            }
        }
    }
    @EventHandler
    private void onChatEvent(AsyncPlayerChatEvent event)
    {
        if(textInputEvents.containsKey(event.getPlayer()))
        {
            Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(),()->
            {
                textInputEvents.get(event.getPlayer()).accept(event.getMessage());
                textInputEvents.remove(event.getPlayer());
            });
            event.setCancelled(true);
        }
    }
}