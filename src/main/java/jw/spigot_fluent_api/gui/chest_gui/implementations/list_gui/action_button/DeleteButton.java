package jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonActionsEnum;
import jw.spigot_fluent_api.gui.button.ButtonFactory;
import jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui.events.ButtonEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DeleteButton extends ActionButton{

    public DeleteButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button)
    {
      /*  button = ButtonFactory.deleteButton();
        button.setPosition(0, 8);
        button.setOnClick((a, b) ->
        {
            this.setCurrentAction(ButtonActionsEnum.DELETE);
            this.getCancelButton().setActive(false);
            this.drawBorder(Material.RED_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_RED + " [Delete]");
            else
                this.refresh();
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
