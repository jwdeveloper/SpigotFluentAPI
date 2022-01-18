package jw.spigot_fluent_api.fluent_commands.services;

import jw.spigot_fluent_api.fluent_commands.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.fluent_commands.events.PlayerCommandEvent;
import jw.spigot_fluent_api.fluent_commands.events.CommandEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class SimpleCommandEventsService implements EventsService
{
    private Consumer<CommandEvent> commandEvent = (a)->{};
    private Consumer<PlayerCommandEvent> playerCommandEvent= (a)->{};
    private Consumer<ConsoleCommandEvent>consoleCommandEvent= (a)->{};

    @Override
    public boolean invokeEvent(CommandSender sender, String[] allArgs, String[] commandArgs)
    {
        if(sender instanceof Player)
        {
            var eventDto = new PlayerCommandEvent(sender, commandArgs, allArgs, null, true);
            commandEvent.accept(eventDto);
            playerCommandEvent.accept(eventDto);
            return eventDto.getResult();
        }
        if(sender instanceof CommandSender)
        {
            var eventDto = new ConsoleCommandEvent(sender, commandArgs, allArgs, null, true);
            commandEvent.accept(eventDto);
            consoleCommandEvent.accept(eventDto);
            return eventDto.getResult();
        }
        return false;
    }

    @Override
    public void onInvoke(Consumer<CommandEvent> event) {
        if(event == null)
            return;
        commandEvent = event;
    }

    @Override
    public void onPlayerInvoke(Consumer<PlayerCommandEvent> event) {
        if(event == null)
            return;
        playerCommandEvent = event;
    }

    @Override
    public void onConsoleInvoke(Consumer<ConsoleCommandEvent> event) {
        if(event == null)
            return;
        consoleCommandEvent = event;
    }
}
