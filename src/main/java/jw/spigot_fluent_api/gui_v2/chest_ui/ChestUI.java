package jw.spigot_fluent_api.gui_v2.chest_ui;


import jw.spigot_fluent_api.gui_v2.InventoryUI;
import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.gui_v2.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.gui_v2.enums.ButtonType;
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
    private List<String> permissions;

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

    protected void initialize() {
        if (!initialized) {
            initialized = true;
            this.displayLog("Initialization", ChatColor.GREEN);
            this.onInitialize();
        }
    }

    @Override
    public void open(Player player) {
        initialize();
        super.refresh();
        super.open(player);
    }
    @Override
    protected void doClick(Player player, int index, ItemStack itemStack, InventoryInteractEvent interactEvent) {
        if (index > getSlots()) {
            onClickAtPlayerItem(player, itemStack);
            return;
        }

        var button = this.getButton(index);
        if (button == null || !button.isActive())
            return;

        if (!checkPermissions(button.getPermissions()))
            return;

        if (button.hasSound())
            player.playSound(player.getLocation(), button.getSound(), 1, 1);

        //  TO DO BINDING
        var inventoryClickEvent = (InventoryClickEvent) interactEvent;
        switch (inventoryClickEvent.getClick()) {
            case SHIFT_LEFT:
            case SHIFT_RIGHT:
                button.getOnShiftClick().Execute(player, button);
                break;
            default:
                if (button instanceof ButtonObserverUI buttonObserverUI)
                    buttonObserverUI.click(player, this);
                else
                    button.click(player);
                onClick(player, button);
                break;
        }
    }

    public void setBorder(Material material) {
        ButtonUI button;
        for (int i = 0; i < INVENTORY_WIDTH; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || j == height - 1 || i == INVENTORY_WIDTH - 1) {

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

    public void openParent() {
        close();
        if (hasParent())
            getParent().open(getPlayer());
    }


}
