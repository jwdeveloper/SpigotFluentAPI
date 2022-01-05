package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.legacy_gui.button.Button;
import jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.legacy_gui.events.ButtonEvent;
import org.bukkit.entity.Player;

public class RightArrowButton extends ActionButton
{
    public RightArrowButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button) {
       /* var rightArrow = ButtonFactory.nextButton(PotionType.SLOWNESS);
        rightArrow.setPosition(this.height - 1, 5);
        rightArrow.setOnClick((a, b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.nextPage();
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
