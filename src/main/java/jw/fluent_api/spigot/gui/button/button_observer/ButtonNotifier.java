package jw.fluent_api.spigot.gui.button.button_observer;

public interface ButtonNotifier<T>
{
     void onClick(ButtonObserverEvent<T> event);

     void onValueChanged(ButtonObserverEvent<T> event);
}
