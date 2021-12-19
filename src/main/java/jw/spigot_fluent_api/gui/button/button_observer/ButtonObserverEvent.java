package jw.spigot_fluent_api.gui.button.button_observer;

import jw.spigot_fluent_api.gui.button.ButtonUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class ButtonObserverEvent<T>
{
    private Player player;
    private ButtonUI button;
    private ButtonObserver<T> bindingStrategy;
    private T value;
}
