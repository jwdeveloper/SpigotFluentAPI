package jw.spigot_fluent_api.fluent_gui;

import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class EventsListenerInventoryUI extends EventBase {
    private static EventsListenerInventoryUI instance;
    private final ArrayList<InventoryUI> inventoriesGui = new ArrayList();
    private final HashMap<Player, Consumer<String>> textInputEvents = new HashMap<>();

    private static EventsListenerInventoryUI getInstance() {
        if (instance == null) {
            instance = new EventsListenerInventoryUI();
        }
        return instance;
    }

    public static void registerTextInput(Player player, Consumer<String> event) {
        var instance = getInstance();
        if (instance.textInputEvents.containsKey(player)) {
            instance.textInputEvents.replace(player, event);
        } else {
            instance.textInputEvents.put(player, event);
        }
    }

    public static void registerUI(InventoryUI InventoryUIBase) {
        var instance = getInstance();
        if (!instance.inventoriesGui.contains(InventoryUIBase)) {
            instance.inventoriesGui.add(InventoryUIBase);
        }
    }

    public static void unregisterUI(InventoryUI InventoryUIBase) {
        var instance = getInstance();
        instance.inventoriesGui.remove(InventoryUIBase);
    }

    public static boolean isRegistered(InventoryUI InventoryUIBase) {
        var instance = getInstance();
        return instance.inventoriesGui.contains(InventoryUIBase);
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory;
        for (InventoryUI InventoryUI : inventoriesGui) {
            inventory = InventoryUI.getInventory();
            if (inventory == null || !InventoryUI.isOpen()) continue;
            if (event.getInventory() == inventory) {
                InventoryUI.refresh();
                break;
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory;
        for (InventoryUI InventoryUI : inventoriesGui) {
            inventory = InventoryUI.getInventory();
            if (inventory == null || !InventoryUI.isOpen()) continue;

            if (event.getInventory() == inventory) {
                InventoryUI.close();
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        if (event.getRawSlot() == -999)
            return;

        Inventory inventory;
        ItemStack selectedItem;
        for (InventoryUI inventoryUI : inventoriesGui) {
            inventory = inventoryUI.getInventory();
            if (inventory == null || !inventoryUI.isOpen())
                continue;

            if (event.getInventory() == inventory)
            {
                event.setCancelled(true);
                selectedItem = event.getCurrentItem();
                if (selectedItem == null || selectedItem.getType() == Material.AIR)
                    return;
                inventoryUI.doClick((Player) event.getWhoClicked(),
                        event.getRawSlot(),
                        selectedItem,
                        event);
                break;
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    private void onDrag(InventoryDragEvent event)
    {
        if(event.getInventorySlots().size() > 2)
            return;

        for(var slot: event.getInventorySlots())
        {
            if(slot == -999)
                return;
        }

        Inventory inventory;
        for (InventoryUI inventoryUI : inventoriesGui) {
            inventory = inventoryUI.getInventory();
            if (inventory == null || !inventoryUI.isOpen())
                continue;

            if (event.getInventory() == inventory)
            {

                break;
            }
        }
    }

    @EventHandler
    private void onPlayerExit(PlayerQuitEvent event) {
        for (int i = 0; i < inventoriesGui.size(); i++) {
            if (event.getPlayer() == inventoriesGui.get(i).getPlayer()) {
                inventoriesGui.get(i).close();
                EventsListenerInventoryUI.unregisterUI(inventoriesGui.get(i));
                return;
            }
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (int i = 0; i < inventoriesGui.size(); i++) {
            inventoriesGui.get(i).close();
        }
    }

    @EventHandler
    private void onChatEvent(AsyncPlayerChatEvent event) {
        if (textInputEvents.containsKey(event.getPlayer())) {
            Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(), () ->
            {
                textInputEvents.get(event.getPlayer()).accept(event.getMessage());
                textInputEvents.remove(event.getPlayer());
            });
            event.setCancelled(true);
        }
    }
}
