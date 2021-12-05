package jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui.button.ButtonFactory;
import jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui.events.ButtonEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EditButton extends ActionButton{
    public EditButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button) {
        button = ButtonFactory.editButton();
        button.setSound(Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT);
        button.setPosition(0, 3);
    }

    @Override
    public ButtonEvent onActionClick()
    {
       return (a,b)->
       {
           listGUI.triggerAction(this.actionsEnum);
           listGUI.setBackgroundMaterial(Material.YELLOW_STAINED_GLASS_PANE);
       };
    }

    @Override
    public void onActionTriggered(Player player, Button button) {
        listGUI.playSound(Sound.UI_TOAST_IN);
        event.Execute(player, button);
        listGUI.triggerAction(ButtonActionsEnum.CANCEL);
        listGUI.displayPaginationResult();
        listGUI.refresh();
    }
}
