package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui_legacy.events.ButtonEvent;
import org.bukkit.entity.Player;

public class CopyButton extends ActionButton{
    public CopyButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button) {
       /* if (this.copyButton != null)
            return this.copyButton;

        copyButton = ButtonFactory.copyButton();
        copyButton.setPosition(0, 5);
        copyButton.setOnClick((a, b) ->
        {
            this.setCurrentAction(ButtonActionsEnum.COPY);
            this.drawBorder(Material.LIME_STAINED_GLASS_PANE);
            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_GREEN + " [Copy]");
            else
                this.refresh();
        });
        return copyButton; */
    }

    @Override
    public ButtonEvent onActionClick() {
        return null;
    }

    @Override
    public void onActionTriggered(Player player, Button button) {

    }
}
