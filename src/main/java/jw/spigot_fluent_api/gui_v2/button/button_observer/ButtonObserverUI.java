package jw.spigot_fluent_api.gui_v2.button.button_observer;

import jw.spigot_fluent_api.gui_v2.InventoryUI;
import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.utilites.binding.Observable;
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
    protected List<ButtonObserver> observers = new ArrayList<>();

    public void addObserver(ButtonObserver observer) {
        observer.setButtonUI(this);
        observers.add(observer);
    }

    public <T> void addObserver(Observable<T> observable, ButtonNotifier<T> buttonNotifier) {
        var observer = new ButtonObserver(observable, buttonNotifier);
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
            observable.invoke(player);
        }
        inventoryUI.refreshButtons();
    }

    public static ButtonObserverUIBuilder builder()
    {
        return new ButtonObserverUIBuilder();
    }
}
