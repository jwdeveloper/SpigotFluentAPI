package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui_legacy.button.ButtonFactory;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui_legacy.events.ButtonEvent;
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
