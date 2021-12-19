package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui_legacy.events.ButtonEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ActionButton {
    protected final ListGUI listGUI;
    protected ButtonActionsEnum actionsEnum;
    protected ButtonEvent event;
    protected Button button;

    public ActionButton(ListGUI listGUI) {
        button = new Button(Material.DIRT);
        this.listGUI = listGUI;
        setupButton(button);
        this.button.setOnClick(onActionClick());

    }

    public void setEvent(ButtonEvent buttonEvent) {
        this.event = buttonEvent;
    }

    public Button getButton() {
        return button;
    }

    public ButtonActionsEnum getAction() {
        return actionsEnum;
    }

    public abstract void setupButton(Button button);

    public abstract ButtonEvent onActionClick();

    public abstract void onActionTriggered(Player player, Button button);
}
