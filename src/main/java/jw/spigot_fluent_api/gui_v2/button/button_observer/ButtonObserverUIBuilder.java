package jw.spigot_fluent_api.gui_v2.button.button_observer;

import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.gui_v2.button.ButtonUIBuilder;
import jw.spigot_fluent_api.utilites.binding.Observable;


public class ButtonObserverUIBuilder extends ButtonUIBuilder<ButtonObserverUIBuilder,ButtonObserverUI>
{
    public ButtonObserverUIBuilder()
    {
        super();
    }
    @Override
    protected ButtonObserverUI createButton()
    {
        return new ButtonObserverUI();
    }

    public ButtonObserverUIBuilder addObserver(ButtonObserver observer)
    {
        button.addObserver(observer);
        return self();
    }

    public <T> ButtonObserverUIBuilder addObserver(Observable<T> observable, ButtonNotifier<T> buttonNotifier)
    {
        button.addObserver(observable, buttonNotifier);
        return self();
    }

    public <T> ButtonObserverUIBuilder addObserver(ButtonObserverBuilder<T> buttonObserverBuilder)
    {
        button.addObserver(buttonObserverBuilder.build());
        return self();
    }


}

