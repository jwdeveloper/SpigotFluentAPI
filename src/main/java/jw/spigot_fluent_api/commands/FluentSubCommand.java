package jw.spigot_fluent_api.commands;

import jw.spigot_fluent_api.commands.events.FluentCommandPlayerEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FluentSubCommand extends FluentCommand
{

    private FluentCommandPlayerEvent commandEvent;

    private boolean runByConsle =false;
    public FluentSubCommand(String name, FluentCommandPlayerEvent commandEvent)
    {
        super(name,false);
        this.commandEvent = commandEvent;
    }
    public FluentSubCommand(String name, FluentCommandPlayerEvent commandEvent, boolean runByConsole)
    {
        this(name,commandEvent);
        this.runByConsle = runByConsole;
    }

    @Override
    protected void onInvoke(CommandSender serverSender, String[] args) {
        if(this.runByConsle)
            commandEvent.execute(null,args);
        else
            commandEvent.execute((Player) serverSender,args);
    }

    @Override
    public void onInitialize() {

    }
}
