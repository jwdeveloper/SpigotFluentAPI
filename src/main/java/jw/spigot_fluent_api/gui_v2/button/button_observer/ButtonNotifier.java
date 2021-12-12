package jw.spigot_fluent_api.gui_v2.button.button_observer;

public interface ButtonNotifier<T>
{
     void onClick(ButtonObserverEvent<T> event);

     void onValueChanged(ButtonObserverEvent<T> event);
}
