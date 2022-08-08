package jw.spigot_fluent_api.fluent_gui.implementation.chest_ui;


import jw.spigot_fluent_api.fluent_gui.InventoryUI;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.fluent_gui.enums.ButtonType;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class ChestUI extends InventoryUI {

    private boolean initialized = false;
    private final List<String> permissions;

    public ChestUI(String name, int height) {
        super(name, height, InventoryType.CHEST);
        permissions = new ArrayList<>();
    }

    public ChestUI(InventoryUI parent, String title, int size) {
        this(title, size);
        this.setParent(parent);
    }

    @Override
    protected void onClick(Player player, ButtonUI button) {

    }

    protected void onClickAtPlayerItem(Player player, ItemStack itemStack) {
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

    protected void onInitialize() {
    }

    private void initialize() {
        if (!initialized) {
            initialized = true;
            this.displayLog("Initialization", ChatColor.GREEN);
            this.onInitialize();
        }
    }

    @Override
    public final void open(Player player) {
        initialize();
        super.refresh();
        super.open(player);
    }

    public final void openParent() {
        close();
        if (hasParent())
            getParent().open(getPlayer());
    }


    @Override
    protected final void doClick(Player player, int index, ItemStack itemStack, InventoryInteractEvent interactEvent) {
        try {
            if (index > getSlots()) {
                onClickAtPlayerItem(player, itemStack);
                return;
            }

            var button = this.getButton(index);
            if (button == null || !button.isActive())
                return;

            if (!checkPermissions(button))
                return;

            if (button.hasSound())
                player.playSound(player.getLocation(), button.getSound(), 1, 1);

            var inventoryClickEvent = (InventoryClickEvent) interactEvent;
            switch (inventoryClickEvent.getClick()) {
                case SHIFT_LEFT, SHIFT_RIGHT -> button.getOnShiftClick().execute(player, button);
                case RIGHT -> button.getOnRightClick().execute(player, button);
                default -> {
                    if (button instanceof ButtonObserverUI buttonObserverUI)
                        buttonObserverUI.click(player, this);
                    else
                        button.click(player);
                    onClick(player, button);
                }
            }
        } catch (Exception e) {
            FluentLogger.error("Error onClick, inventory " + this.getName()+" by player "+player.getName(), e);
        }
    }

    public void refreshBorder() {
        ButtonUI button;
        for (int i = 0; i < INVENTORY_WIDTH; i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (i == 0 || j == 0 || j == getHeight() - 1 || i == INVENTORY_WIDTH - 1) {
                    button = getButton(j, i);
                    if (button.getButtonType() == ButtonType.BACKGROUND) {
                        refreshButton(button);
                    }
                }
            }
        }
    }

    public void setBorderMaterial(Material material) {
        ButtonUI button;
        for (int i = 0; i < INVENTORY_WIDTH; i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (i == 0 || j == 0 || j == getHeight() - 1 || i == INVENTORY_WIDTH - 1) {
                    button = getButton(j, i);
                    if (button == null) {
                        button = ButtonUI.builder()
                                .setMaterial(material)
                                .setButtonType(ButtonType.BACKGROUND)
                                .setTitle(" ")
                                .setLocation(j, i)
                                .build();
                        addButton(button);
                        continue;
                    }

                    if (button.getButtonType() == ButtonType.BACKGROUND) {
                        button.setMaterial(material);
                    }
                }
            }
        }
    }

    public void setFillMaterial(Material material) {
        ButtonUI button;
        for (int i = 0; i < INVENTORY_WIDTH; i++) {
            for (int j = 0; j < getHeight(); j++) {
                {
                    button = getButton(j, i);
                    if (button == null) {
                        button = ButtonUI.builder()
                                .setMaterial(material)
                                .setButtonType(ButtonType.BACKGROUND)
                                .setTitle(" ")
                                .setLocation(j, i)
                                .build();
                        addButton(button);
                        continue;
                    }
                    if (button.getButtonType() == ButtonType.BACKGROUND) {
                        button.setMaterial(material);
                    }
                }
            }
        }
    }


}
