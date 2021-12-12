package jw.spigot_fluent_api.gui_v2.button.button_observer;

import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.utilites.binding.Observable;
import org.bukkit.entity.Player;

public class ButtonObserver<T>
{
    private final Observable<T> observable;
    private final ButtonNotifier buttonNotifier;
    private ButtonUI buttonUI;

    public ButtonObserver(Observable<T> observable,ButtonNotifier buttonNotifier)
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

    public void invoke(Player player)
    {
        if(!validateButton())
            return;
        buttonNotifier.onClick(new ButtonObserverEvent(player, buttonUI,this, observable.get()));
    }

    public void refresh()
    {
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
        if(buttonUI == null || !buttonUI.isActive())
            return false;

        return true;
    }
}
