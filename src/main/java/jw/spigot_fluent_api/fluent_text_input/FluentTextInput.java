package jw.spigot_fluent_api.fluent_text_input;

import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.function.Consumer;

public class FluentTextInput extends EventBase
{
    private HashMap<Player,Consumer<String>> players;
    private static FluentTextInput instance;

    private FluentTextInput()
    {
        players = new HashMap<>();
    }

    private static FluentTextInput getInstance()
    {
        if(instance == null)
            instance = new FluentTextInput();

        return instance;
    }

    public static void openTextInput(Player player, String message, Consumer<String> onInput)
    {
         if(getInstance().players.containsKey(player))
         {
             getInstance().players.remove(player);
         }
        getInstance().players.put(player,onInput);
         player.sendMessage(message);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        if(!getInstance().players.containsKey(event.getPlayer()))
        {
           return;
        }
        new FluentTaskTimer(1,(iteration, task) ->
        {
            try {
                var textEvent =  getInstance().players.get(event.getPlayer());
                textEvent.accept(event.getMessage());
                getInstance().players.remove(event.getPlayer());
            }
            catch (Exception e)
            {
                FluentPlugin.logException("Error while text input",e);
            }

        }).stopAfterIterations(1).run();
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if(getInstance().players.containsKey(event.getPlayer()))
        {
            getInstance().players.remove(event.getPlayer());
        }
    }
}
