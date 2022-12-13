package jw.fluent.api.spigot.gui.armorstand_gui.implementation;

import jw.fluent.api.spigot.gui.armorstand_gui.api.ArmorStandGui;
import jw.fluent.api.spigot.gui.armorstand_gui.api.button.enums.StandButtonEventType;
import jw.fluent.api.spigot.gui.armorstand_gui.api.button.events.StandButtonEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.enums.StandButtonAction;
import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events.StandCloseEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events.StandOpenEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events.StandUpdateEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class StandGui implements ArmorStandGui {
    private final Set<StandButton> buttons;

    private boolean isInitialized = false;
    @Getter
    private Player player;

    public StandGui() {
        buttons = new LinkedHashSet<>();
    }

    protected abstract void onInitialize();

    protected abstract void onOpen(StandOpenEvent event);

    protected abstract void onUpdate(StandUpdateEvent event);

    protected abstract void onClose(StandCloseEvent event);

    @Override
    public final void open(Player player) {
        this.player = player;
        if (!isInitialized) {
            onInitialize();
            isInitialized = true;
        }

        onOpen(new StandOpenEvent(player, player.getLocation()));
    }

    @Override
    public final void close() {
        onClose(new StandCloseEvent(player, player.getLocation()));
        player = null;
    }

    @Override
    public final void update() {

    }

    public void addButton(StandButton standButton) {
        if (buttons.contains(buttons)) {
            return;
        }
        buttons.add(standButton);
        standButton.onCreate(new StandButtonEvent( player,standButton, StandButtonEventType.LEFT_CLICK));
        onUpdate(new StandUpdateEvent(player, standButton, StandButtonAction.CREATED));
    }

    public void removeButton(StandButton standButton) {
        buttons.remove(standButton);
        standButton.onDestroy(new StandButtonEvent( player, standButton,StandButtonEventType.LEFT_CLICK));
        onUpdate(new StandUpdateEvent(player, standButton, StandButtonAction.REMOVED));
    }
}
