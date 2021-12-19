package jw.spigot_fluent_api.gui.scroll_selector;

import jw.spigot_fluent_api.events.EventBase;
import lombok.Getter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.HashSet;

@Getter
public class ScrollSelectorManager extends EventBase {

    public HashSet<ScrollSelector> scrollSelectors;
    private static ScrollSelectorManager instance;

    public ScrollSelectorManager()
    {
        scrollSelectors = new HashSet<>();
    }

    public static ScrollSelectorManager getInstance()
    {
        if (instance == null)
            instance = new ScrollSelectorManager();
        return instance;
    }

    public void register(ScrollSelector scrollSelector)
    {
        scrollSelectors.add(scrollSelector);
    }

    public void unregister(ScrollSelector scrollSelector)
    {
        scrollSelectors.remove(scrollSelector);
    }

    @EventHandler
    public void playerScrollEvent(PlayerItemHeldEvent event)
    {
        for(ScrollSelector scrollSelector: scrollSelectors)
        {
            if(!scrollSelector.getPlayer().equals(event.getPlayer()))
                continue;

             scrollSelector.onSlotIndexChanged(new ScrollSelectorEvent(
                     event.getPlayer(),
                     event.getPreviousSlot(),
                     event.getNewSlot()));
        }
    }

    @EventHandler
    public void playerQuiteEvent(PlayerQuitEvent event)
    {
        for(ScrollSelector scrollSelector: scrollSelectors)
        {
            if(!scrollSelector.getPlayer().equals(event.getPlayer()))
                continue;

             scrollSelector.close();
        }
    }

    @EventHandler
    public void playerClickEvent(PlayerInteractEvent event)
    {
        for(ScrollSelector scrollSelector: scrollSelectors)
        {
            if(!validate(scrollSelector,event.getPlayer()))
                continue;

            event.setCancelled(true);
            var player = event.getPlayer();
            scrollSelector.onClick(new ScrollSelectorEvent(
                    player,
                    player.getInventory().getHeldItemSlot(),
                    player.getInventory().getHeldItemSlot()));
        }
    }

    @EventHandler
    public void playerDropItemEvent(PlayerDropItemEvent event)
    {
        for(ScrollSelector scrollSelector: scrollSelectors)
        {
            if(!scrollSelector.getPlayer().equals(event.getPlayer()))
                continue;

            scrollSelector.close();
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event)
    {
        for(ScrollSelector scrollSelector: scrollSelectors)
        {
            scrollSelector.close();
        }
    }

    @EventHandler
    public void playerOpenInventory(InventoryOpenEvent event)
    {
        for(ScrollSelector scrollSelector: scrollSelectors)
        {
            if(!validate(scrollSelector,event.getPlayer()))
                continue;

            event.setCancelled(true);
        }
    }

    private boolean validate(ScrollSelector scrollSelector, HumanEntity player)
    {
        if(player.isDead())
            return false;
        if(!scrollSelector.getPlayer().equals(player))
            return false;
        if(!scrollSelector.isOpen())
            return false;

        return true;
    }
}
