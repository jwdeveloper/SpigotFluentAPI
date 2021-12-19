package jw.spigot_fluent_api.gui.button.button_observer;

import jw.spigot_fluent_api.gui.button.ButtonUI;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.binding.Observable;
import org.bukkit.entity.Player;

public class ButtonObserver<T>
{
    private final Observable<T> observable;
    private final ButtonNotifier buttonNotifier;
    public ButtonUI buttonUI;

    public ButtonObserver(Observable<T> observable,ButtonNotifier buttonNotifier)
    {
        this.observable = observable;
        this.buttonNotifier =buttonNotifier;
        this.observable.onChange(value ->
        {
            FluentPlugin.logInfo("C");
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
