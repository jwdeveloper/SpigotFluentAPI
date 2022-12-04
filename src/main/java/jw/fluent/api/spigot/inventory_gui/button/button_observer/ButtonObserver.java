package jw.fluent.api.spigot.inventory_gui.button.button_observer;

import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import org.bukkit.entity.Player;

public class ButtonObserver<T>
{
    private final Observer<T> observable;
    private final ButtonNotifier buttonNotifier;
    public ButtonUI buttonUI;

    public ButtonObserver(Observer<T> observable, ButtonNotifier buttonNotifier)
    {
        this.observable = observable;
        this.buttonNotifier =buttonNotifier;
        this.observable.onChange(value ->
        {
            if(!validateButton())
                return;
            buttonNotifier.onValueChanged(new ButtonObserverEvent(null,buttonUI,this,value));
        });
    }
    public static <T> ButtonObserverBuilder<T> builder()
    {
        return new ButtonObserverBuilder<T>();
    }

    public void setButtonUI(ButtonUI buttonUI)
    {
        this.buttonUI = buttonUI;
    }

    public void click(Player player)
    {
        //FluentPlugin.logInfo("A");
        if(!validateButton())
            return;
        buttonNotifier.onClick(new ButtonObserverEvent(player, buttonUI,this, observable.get()));
    }

    public void refresh()
    {
       // FluentPlugin.logInfo("B"+ this.toString());
        if(!validateButton())
            return;
        buttonNotifier.onValueChanged(new ButtonObserverEvent(null, buttonUI,this, observable.get()));
    }

    public void setValue(T value)
    {
        observable.set(value);
    }

    public T getValue()
    {
        return observable.get();
    }

    private boolean validateButton()
    {
        return buttonUI != null && buttonUI.isActive();
    }
}
