package jw.fluent_api.minecraft.gui.button.button_observer;

import jw.fluent_api.minecraft.gui.button.ButtonUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class ButtonObserverEvent<T>
{
    private Player player;
    private ButtonUI button;
    private ButtonObserver<T> observer;
    private T value;

    @Override
    public String toString() {
        return "ButtonObserverEvent{" +
                "player=" + player +
                ", button=" + button +
                ", observer=" + observer +
                ", value=" + value +
                '}';
    }
}
