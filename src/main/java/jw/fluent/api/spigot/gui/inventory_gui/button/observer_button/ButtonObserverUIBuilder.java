package jw.fluent.api.spigot.gui.inventory_gui.button.observer_button;

import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUIBuilder;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonNotifier;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserver;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserverBuilder;

import java.util.function.Supplier;


public class ButtonObserverUIBuilder  extends ButtonUIBuilder<ButtonObserverUIBuilder,ButtonObserverUI>
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

    public <T> ButtonObserverUIBuilder addObserver(Observer<T> observable, ButtonNotifier<T> buttonNotifier)
    {
        button.addObserver(observable, buttonNotifier);
        return self();
    }

    public <T> ButtonObserverUIBuilder addObserver(Supplier<Observer<T>> observable, ButtonNotifier<T> buttonNotifier)
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

