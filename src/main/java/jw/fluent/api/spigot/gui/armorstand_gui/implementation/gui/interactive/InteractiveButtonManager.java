package jw.fluent.api.spigot.gui.armorstand_gui.implementation.gui.interactive;

import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class InteractiveButtonManager {
    private Map<StandButton, InteractiveButton> interactiveButtonMap;
    private SimpleTaskTimer playerTracker;

    public InteractiveButtonManager() {
        interactiveButtonMap = new HashMap<>();
    }

    public void register(StandButton standButton, Player player) {
        interactiveButtonMap.put(standButton, new InteractiveButton(standButton, player));
    }

    public void unregister(StandButton standButton) {
        interactiveButtonMap.remove(standButton);
    }

    public void flyToPlayer(Player player) {
        interactiveButtonMap.values().forEach(e -> e.flyToPlayer(player));
        playerTracker.stop();
    }

    public void flyAwayFromPlayer(Player player) {
        interactiveButtonMap.values().forEach(e -> e.flyAwayFromPlayer(player));
        runPlayerTracker(player);
    }

    InteractiveButton lastLookingBtn = null;

    private int tolerance = 20;
    public void runPlayerTracker(Player player) {

        FluentApi.events().onEvent(PlayerInteractEvent.class,event ->
        {
            if(lastLookingBtn == null)
            {
                return;
            }
            if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                lastLookingBtn.handleLeftPlayerClick(player);
                event.setCancelled(true);
                return;
            }
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                lastLookingBtn.handleRightPlayerClick(player);
                event.setCancelled(true);
                return;
            }

        });

        playerTracker = FluentApi.tasks().taskTimer(5, (iteration, task) ->
        {
            boolean foound = false;
            for (var button : interactiveButtonMap.values()) {
                if (!button.isPlayerLooking(player)) {
                    continue;
                }

                if (lastLookingBtn != null) {
                    lastLookingBtn.playerNotLooking();
                }

                button.playerLooking();
                lastLookingBtn = button;
                foound = true;
                tolerance = 5;
                break;
            }
            tolerance --;
            if(tolerance <0)
            {
                if(!foound && lastLookingBtn != null)
                {
                    lastLookingBtn.playerNotLooking();
                    lastLookingBtn = null;
                }
            }


        }).run();
    }

}
