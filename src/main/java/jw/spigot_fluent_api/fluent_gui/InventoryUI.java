package jw.spigot_fluent_api.fluent_gui;


import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public abstract class InventoryUI {
    protected static final int MAX_TITLE_LENGTH = 38;
    protected static final int INVENTORY_WIDTH = 9;

    @Setter(value = AccessLevel.NONE)
    private Inventory inventory;

    @Setter(value = AccessLevel.NONE)
    private ButtonUI[] buttons;

    @Setter(value = AccessLevel.NONE)
    private InventoryType inventoryType;

    @Setter(value = AccessLevel.NONE)
    private int slots = 1;

    @Setter(value = AccessLevel.NONE)
    private int height = 1;

    @Setter(value = AccessLevel.NONE)
    private String name;

    private Player player;
    private String title;
    private boolean enableLogs;
    private InventoryUI parent;
    private boolean isOpen;

    protected abstract void doClick(Player player, int index, ItemStack itemStack, InventoryInteractEvent interactEvent);

    protected abstract void onClick(Player player, ButtonUI button);

    protected abstract void onRefresh(Player player);

    protected abstract void onOpen(Player player);

    protected abstract void onClose(Player player);

    public InventoryUI(String name, int height, InventoryType inventoryType) {
        this.name = name;
        this.title = name;
        this.inventoryType = inventoryType;
        this.height = calculateHeight(height);
        this.slots = calculateSlots(height);
        this.buttons = new ButtonUI[this.slots];
    }

    public void open(Player player) {
        if (!validatePlayer(player))
            return;

        if (hasParent()) {
            parent.close();
        }

        this.player = player;
        this.inventory = createInventory(inventoryType);

        onOpen(player);
        refreshButtons();
        EventsListenerInventoryUI.registerUI(this);
        player.openInventory(getInventory());
        this.isOpen = true;
        displayLog("Open with Bukkit inv " + inventory.hashCode(), ChatColor.GREEN);
    }

    public void close() {
        EventsListenerInventoryUI.unregisterUI(this);
        isOpen = false;
        if (!validatePlayer(player))
            return;

        onClose(player);
        player.closeInventory();
        displayLog("Close", ChatColor.RED);
    }

    public void setTitle(MessageBuilder title) {
        setTitle(title.toString());
    }

    public void setTitle(String title) {
        this.title = title;
        if (player == null || !player.isOnline())
            return;
        EventsListenerInventoryUI.unregisterUI(this);
        var currentContent = getInventory().getContents();
        this.inventory = createInventory(inventoryType);
        getInventory().setContents(currentContent);
        if (isOpen)
            player.openInventory(getInventory());
        EventsListenerInventoryUI.registerUI(this);

        this.displayLog("Title changed with Bukkit inv " + inventory.hashCode(), ChatColor.GREEN);
    }

    public void refresh() {
        if (!isOpen()) {
            displayLog("Gui cant be refresh since is closed", ChatColor.YELLOW);
            return;
        }

        if (!validatePlayer(player)) {
            displayLog("Gui cant be refresh since player is invalid", ChatColor.YELLOW);
            return;
        }


        refreshButtons();
        onRefresh(player);
        displayLog("Refresh", ChatColor.GREEN);
    }

    public void refreshButtons() {
        if (getInventory() == null)
            return;
        ButtonUI button = null;
        for (int i = 0; i < buttons.length; i++) {
            button = buttons[i];
            if (button != null && button.isActive()) {
                getInventory().setItem(i, button.getItemStack());
            } else
                getInventory().setItem(i, null);
        }
        displayLog("New content loaded for Bukkit inv " + inventory.hashCode(), ChatColor.GREEN);
    }

    public void refreshButton(ButtonUI button) {
        var index = calculateButtonSlotIndex(button);
        if (index == -1)
            return;
        if (button.isActive()) {
            this.inventory.setItem(index, button.getItemStack());
        } else
            this.inventory.setItem(index, null);
    }

    public ButtonUI getButton(int height, int width) {
        var position = calculateButtonSlotIndex(height, width);
        return getButton(position);
    }

    public ButtonUI getButton(int index) {
        var position = index >= buttons.length ? buttons.length - 1 : index;
        return buttons[position];
    }

    public void addButton(ButtonUI button) {
        var slotIndex = calculateButtonSlotIndex(button);
        addButton(button, slotIndex);
    }

    public void addButtons(List<ButtonUI> buttons) {
        for (var button : buttons) {
            addButton(button);
        }
    }

    public void addButtons(ButtonUI[] buttons) {
        for (var button : buttons) {
            addButton(button);
        }
    }

    public void addButton(ButtonUI button, int slotIndex) {
        if (slotIndex <= slots)
            buttons[slotIndex] = button;
    }

    public void displayLog(String message, ChatColor chatColor) {
        if (enableLogs)
            FluentPlugin.logInfo(this + ": " + chatColor + message);
    }

    public boolean isSlotEmpty(int slotIndex) {
        return buttons[slotIndex] == null;
    }

    public boolean hasParent() {
        return parent != null;
    }

    protected Inventory createInventory(InventoryType inventoryType) {
        switch (inventoryType) {
            case CHEST:
                return Bukkit.createInventory(player, slots, title);
            default:
                new Exception("Sorry UI for " + inventoryType.name() + " not implemented yet ;<");
        }
        return Bukkit.createInventory(player, inventoryType, title);
    }

    protected int calculateSlots(int height) {
        return Math.min(height, 6) * 9;
    }

    protected int calculateHeight(int height) {
        return Math.min(height, 6);
    }

    protected boolean checkPermissions(ButtonUI buttonUI) {
        if (!validatePlayer(player))
            return false;

        if (player.isOp())
            return true;

        if(buttonUI.getPermissions().size() == 0)
            return true;

        var result = switch (buttonUI.getPermissionType()) {
            case ALL -> shouldHaveAllPermission(buttonUI.getPermissions());
            case ONE_OF -> shouldHaveOnePermissions(buttonUI.getPermissions());
            default -> true;
        };
        return result;
    }

    private boolean shouldHaveAllPermission(List<String> permissions) {
        for (var permission : permissions) {
            if (!player.hasPermission(permission)) {
                FluentMessage.message()
                        .color(ChatColor.DARK_RED)
                        .text("You need to have permissions ")
                        .color(ChatColor.GRAY)
                        .text(Emoticons.arrowRight)
                        .space()
                        .color(ChatColor.RED)
                        .text(permission)
                        .send(player);
                return false;
            }
        }
        return true;
    }

    private boolean shouldHaveOnePermissions(List<String> permissions) {
        for (var permission : permissions) {
            if (player.hasPermission(permission)) {
                return true;
            }
        }

        FluentMessage.message()
                .color(ChatColor.DARK_RED)
                .text("You need to have one of those permissions").send(player);

        for (var permission : permissions) {
            FluentMessage.message()
                    .color(ChatColor.GRAY)
                    .text(Emoticons.arrowRight)
                    .space()
                    .color(ChatColor.RED)
                    .text(permission)
                    .send(player);
        }

        return false;
    }

    private int calculateButtonSlotIndex(ButtonUI button) {
        if (button.getHeight() > height - 1)
            return -1;

        return calculateButtonSlotIndex(button.getHeight(), button.getWidth());
    }

    private int calculateButtonSlotIndex(int height, int width) {
        return height * INVENTORY_WIDTH + width % 9;
    }

    private boolean validatePlayer(Player player) {
        if (player == null || !player.isOnline()) {
            displayLog("Invalid player", ChatColor.RED);
            return false;
        }
        return true;
    }

    public int getWidth() {
        return INVENTORY_WIDTH;
    }
}
