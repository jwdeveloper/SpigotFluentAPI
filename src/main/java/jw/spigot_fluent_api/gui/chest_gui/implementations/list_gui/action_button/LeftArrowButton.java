package jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.action_button;

import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.button.ButtonFactory;
import jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui.ListGUI;
import jw.spigot_fluent_api.gui.events.ButtonEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

public class LeftArrowButton extends ActionButton{
    public LeftArrowButton(ListGUI listGUI) {
        super(listGUI);
    }

    @Override
    public void setupButton(Button button) {
      /*  var leftArrow = ButtonFactory.BackButton(PotionType.SLOWNESS);
        leftArrow.setPosition(this.height - 1, 3);
        leftArrow.setOnClick((a, b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.backPage();
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
