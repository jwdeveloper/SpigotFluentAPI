package jw.spigot_fluent_api.commands;

import jw.spigot_fluent_api.commands.events.FluentCommandPlayerEvent;
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
    public FluentSubCommand(String name, FluentCommandPlayerEvent commandEvent, boolean runByConsle)
    {
        this(name,commandEvent);
        this.runByConsle = runByConsle;
    }
    @Override
    public void onInvoke(Player playerSender, String[] args) {
        commandEvent.execute(playerSender,args);
    }

    @Override
    public void onInvoke(ConsoleCommandSender serverSender, String[] args) {
       if(this.runByConsle)
        commandEvent.execute(null,args);
    }

    @Override
    public void onInitialize() {

    }
}
