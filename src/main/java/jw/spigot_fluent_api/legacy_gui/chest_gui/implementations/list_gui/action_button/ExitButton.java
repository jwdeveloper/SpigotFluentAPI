package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.legacy_gui.button.ButtonFactory;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.legacy_gui.events.ButtonEvent;
import org.bukkit.entity.Player;

public class ExitButton extends ActionButton
{
    public ExitButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button) {
        button = ButtonFactory.exitButton();
        button.setPosition(listGUI.getHeight() - 1, 8);
    }

    @Override
    public ButtonEvent onActionClick() {
        return (a,b)->
        {
            listGUI.triggerAction(ButtonActionsEnum.CANCEL);
            listGUI.openParent();
        };
    }

    @Override
    public void onActionTriggered(Player player, Button button) {

    }
}
