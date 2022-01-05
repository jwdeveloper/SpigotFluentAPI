package jw.spigot_fluent_api.legacy_commands;

import jw.spigot_fluent_api.legacy_commands.events.FluentCommandPlayerEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FluentSubCommand extends FluentCommand
{

    private FluentCommandPlayerEvent commandEvent;

    private boolean runByConsole =false;
    public FluentSubCommand(String name, FluentCommandPlayerEvent commandEvent)
    {
        super(name,false);
        this.commandEvent = commandEvent;
    }
    public FluentSubCommand(String name, FluentCommandPlayerEvent commandEvent, boolean runByConsole)
    {
        this(name,commandEvent);
        this.runByConsole = runByConsole;
    }

    @Override
    protected void onInvoke(CommandSender serverSender, String[] args) {
        if(this.runByConsole)
            commandEvent.execute(null,args);
        else
            commandEvent.execute((Player) serverSender,args);
    }

    @Override
    public void onInitialize() {

    }
}
