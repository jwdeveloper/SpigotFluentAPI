package jw.spigot_fluent_api.gui.chest_gui;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui.button.ButtonFactory;
import jw.spigot_fluent_api.gui.InventoryGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.button.ButtonBuilderChestGUI;
import jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.BindingStrategy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class ChestGUI<T> extends InventoryGUI {

    public T detail;
    private Class<T> detailClass;
    private final ArrayList<BindingStrategy> bindingStrategies = new ArrayList<>();
    private boolean isInitialized = false;

    public ChestGUI(String name, int size) {
        super(name, size, InventoryType.CHEST);
    }

    public ChestGUI(InventoryGUI parent, String name, int size) {
        super(parent, name, size, InventoryType.CHEST);
    }

    public ChestGUI(InventoryGUI parent, String name, int size, Class<T> detailClass) {
        super(parent, name, size, InventoryType.CHEST);
        this.detailClass = detailClass;
    }


    public abstract void onInitialize();

    @Override
    public void onClick(Player player, Button button) {

    }

    @Override
    public void onRefresh(Player player) {

    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }

    public void onClickAtPlayerItem(Player player, ItemStack itemStack) {
    }



    @Override
    protected void doClick(Player player, int index, ItemStack itemStack, InventoryInteractEvent interactEvent) {
        if (index < this.size) {
            Button button = this.getButton(index);
            if (button != null && button.isActive()) {
                if (button.hasSound())
                    player.playSound(player.getLocation(), button.getSound(), 1, 1);
                if (!button.checkPermission(player))
                    return;
                //Invoke all binded varables events for button
                for (BindingStrategy bindingStrategy : bindingStrategies) {

                    if (bindingStrategy.getButton() == button) {
                        bindingStrategy.Execute(player, button);
                    }
                }
                //Invoke button onclick
                InventoryClickEvent inventoryClickEvent = (InventoryClickEvent)interactEvent;
                switch (inventoryClickEvent.getClick())
                {
                    case SHIFT_LEFT:
                    case SHIFT_RIGHT:
                        button.getOnShiftClick().Execute(player, button);
                        break;
                    default:
                        button.getOnClick().Execute(player, button);
                        onClick(player, button);
                        break;
                }

            }
        } else {
            onClickAtPlayerItem(player, itemStack);
        }
    }
    @Override
    public void open(Player player) {
        initialize();
        refreshBindedButtons();
        super.open(player);
    }

    public void open(Player player, T detail) {
        this.detail = detail;
        this.open(player);
    }

    public void openParent() {
        if (this.getParent() != null) {
            this.close();
            this.getParent().open(player);
        } else {
            this.close();
        }
    }

    @Override
    public ButtonBuilderChestGUI buildButton() {
        return new ButtonBuilderChestGUI(this);
    }

    protected void initialize() {
        if (!isInitialized) {
            isInitialized = true;
            this.onInitialize();
        }
    }
    public void drawBorder(Material material) {
        Button button;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < this.height; j++) {
                if (i == 0 || j == 0 || j == this.height - 1 || i == 8) {
                    button = getButton(j, i);
                    if (button == null)
                    {
                        this.addButton(ButtonFactory.getBackground(material), j, i);
                    } else if (button.getAction() == ButtonActionsEnum.BACKGROUND || button.getAction() == ButtonActionsEnum.EMPTY)
                    {
                        button.setType(material);
                    }
                }
            }
        }
    }

    public void fillWithMaterial(Material material) {
        Button button = null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < this.height; j++) {
                button = getButton(j, i);
                if (button == null) {
                    this.addButton(ButtonFactory.getBackground(material), j, i);
                } else if (button.getAction() == ButtonActionsEnum.BACKGROUND || button.getAction() == ButtonActionsEnum.EMPTY) {
                    button.setType(material);
                }
            }
        }
    }
    public void addBackArrow() {
        Button button = ButtonFactory.exitButton();
        button.setPosition(this.height - 1, 8);
        button.setOnClick((a, b) ->
        {
            this.openParent();
        });
        this.addButton(button);
    }

    public void setDetail(T detail)
    {
        this.detail =detail;
    }

    public void refreshBindedButtons()
    {
        for (BindingStrategy strategy : bindingStrategies) {
            strategy.setObject(this.detail);
            strategy.onChangeEvent.OnValueChanged(this, strategy.getButton(), strategy.getValue());
        }
    }

    public void addBindStrategy(BindingStrategy bindingStrategy) {
        if (!this.bindingStrategies.contains(bindingStrategy))
            this.bindingStrategies.add(bindingStrategy);
    }


}