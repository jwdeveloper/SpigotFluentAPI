package jw.fluent_api.minecraft.commands.implementation.services;

import jw.fluent_api.minecraft.commands.implementation.events.ConsoleCommandEvent;
import jw.fluent_api.minecraft.commands.implementation.events.PlayerCommandEvent;
import jw.fluent_api.minecraft.commands.implementation.events.CommandEvent;
import jw.fluent_api.minecraft.commands.api.services.EventsService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class EventsServiceImpl implements EventsService
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
