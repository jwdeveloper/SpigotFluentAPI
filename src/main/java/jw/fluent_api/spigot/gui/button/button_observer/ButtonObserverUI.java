package jw.fluent_api.spigot.gui.button.button_observer;

import jw.fluent_api.spigot.gui.EventsListenerInventoryUI;
import jw.fluent_api.spigot.gui.InventoryUI;
import jw.fluent_api.spigot.gui.button.ButtonUI;
import jw.fluent_api.desing_patterns.observer.implementation.Observer;
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

    public void click(Player player, InventoryUI inventoryUI) {
        super.click(player);
        for (var observable : observers) {
            observable.click(player);
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
