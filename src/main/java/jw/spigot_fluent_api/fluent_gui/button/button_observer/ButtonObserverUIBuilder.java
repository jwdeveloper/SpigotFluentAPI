package jw.spigot_fluent_api.fluent_gui.button.button_observer;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUIBuilder;
import jw.spigot_fluent_api.desing_patterns.observer.fields.Observable;


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

