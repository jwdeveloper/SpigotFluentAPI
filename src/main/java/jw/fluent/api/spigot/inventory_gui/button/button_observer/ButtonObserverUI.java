package jw.fluent.api.spigot.inventory_gui.button.button_observer;

import jw.fluent.api.spigot.inventory_gui.EventsListenerInventoryUI;
import jw.fluent.api.spigot.inventory_gui.InventoryUI;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ButtonObserverUI extends ButtonUI {
    @Singular
    protected List<ButtonObserver<?>> observers = new ArrayList<>();


    public void addObserver(ButtonObserver<?> observer) {
        observer.setButtonUI(this);
        observers.add(observer);
    }

    public <T> void addObserver(Observer<T> observable, ButtonNotifier<T> buttonNotifier) {
        var observer = new ButtonObserver<>(observable, buttonNotifier);
        observer.setButtonUI(this);
        observers.add(observer);
    }

    @Override
    public ItemStack getItemStack() {
        for (var observable : observers) {
            observable.refresh();
        }
        return super.getItemStack();
    }

    public void onClick(Player player, InventoryUI inventoryUI) {
        super.click(player);
        for (var observable : observers) {
            observable.click(player);
            inventoryUI.refreshButton(observable.buttonUI);
        }
        EventsListenerInventoryUI.refreshAllAsync(inventoryUI.getClass(), inventoryUI);
    }

    public void onRightClick(Player player, InventoryUI inventoryUI) {
        super.rightClick(player);
        for (var observable : observers) {
            observable.rightClick(player);
            inventoryUI.refreshButton(observable.buttonUI);
        }
        EventsListenerInventoryUI.refreshAllAsync(inventoryUI.getClass(), inventoryUI);
    }

    public static ButtonObserverUIFactory factory() {
        return new ButtonObserverUIFactory();
    }

    public static ButtonObserverUIBuilder builder() {
        return new ButtonObserverUIBuilder();
    }
}
