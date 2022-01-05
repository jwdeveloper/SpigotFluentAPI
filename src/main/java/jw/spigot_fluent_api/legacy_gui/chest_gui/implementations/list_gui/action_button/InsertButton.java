package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.legacy_gui.events.ButtonEvent;
import org.bukkit.entity.Player;

public class InsertButton extends ActionButton
{
    public InsertButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button)
    {
       /* button = ButtonFactory.insertButton();
        button.setSound(Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT);
        button.setPosition(0, 6);
        button.setOnClick((a, b) ->
        {
            cancelAction();
            listGUI.openTextInput(ChatColor.BOLD + "Enter name", input ->
            {
                onInsert.accept(input);
            });
        });*/
    }

    @Override
    public ButtonEvent onActionClick() {
        return null;
    }

    @Override
    public void onActionTriggered(Player player, Button button) {

    }
}
