package jw.fluent.api.spigot.gui.armorstand_gui.implementation.button;

import jw.fluent.api.spigot.gui.armorstand_gui.api.button.events.StandButtonEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.api.button.events.StandVisibilityEvent;
import jw.fluent.api.utilites.math.collistions.HitBox;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

@Builder
public class StandButton {
    @Setter
    @Getter
    private boolean visible;

    @Setter
    @Getter
    private String tag;

    @Setter
    @Getter
    private Vector offset;

    @Getter
    @Setter
    private Vector hitBox;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private ItemStack icon;

    private Consumer<StandButtonEvent> onLeftClick = (d) -> {
    };
    private Consumer<StandButtonEvent> onRightClick = (d) -> {
    };
    private Consumer<StandButtonEvent> onClick = (d) -> {
    };
    private Consumer<StandVisibilityEvent> onVisibility = (d) -> {
    };
    private Consumer<StandButtonEvent> onDestroy = (d) -> {
    };
    private Consumer<StandButtonEvent> onCreate = (d) -> {
    };

    public void setVisible(boolean visible) {
        onVisibility(new StandVisibilityEvent(this, visible));
    }

    public void onLeftClick(StandButtonEvent event) {
        onLeftClick.accept(event);
    }

    public void onRightClick(StandButtonEvent event) {
        onRightClick.accept(event);
    }

    public void onClick(StandButtonEvent event) {
        onClick.accept(event);
    }

    public void onDestroy(StandButtonEvent event) {
        onDestroy.accept(event);
    }

    public void onCreate(StandButtonEvent event) {
        if(onCreate == null)
            return;
        onCreate.accept(event);
    }

    public void onVisibility(StandVisibilityEvent event) {
        onVisibility.accept(event);
    }
}
