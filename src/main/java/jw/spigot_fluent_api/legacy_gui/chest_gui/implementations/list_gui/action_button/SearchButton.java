package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.legacy_gui.events.ButtonEvent;
import org.bukkit.entity.Player;

public class SearchButton extends ActionButton{

    public SearchButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button) {
       /* if (this.searchButton != null)
            return this.searchButton;
        searchButton = ButtonFactory.searchButton();
        searchButton.setPosition(0, 0);
        searchButton.setOnClick((a, b) ->
        {
            cancelAction();
            this.openTextInput("Enter value on chat", input ->
            {
                // this.filterContent(input);
                this.getCancelButton().setActive(true);
            });
        });
        return searchButton;*/
    }

    @Override
    public ButtonEvent onActionClick() {
        return null;
    }

    @Override
    public void onActionTriggered(Player player, Button button) {

    }
}
