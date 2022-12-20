package jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers;

public interface ButtonNotifier<T>
{
     void onLeftClick(ButtonObserverEvent<T> event);

     default void onRightClick(ButtonObserverEvent<T> event)
     {

     }

     void onValueChanged(ButtonObserverEvent<T> event);
}
