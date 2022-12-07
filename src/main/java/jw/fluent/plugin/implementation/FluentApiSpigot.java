package jw.fluent.plugin.implementation;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import jw.fluent.api.spigot.messages.FluentMessage;
import jw.fluent.api.spigot.particles.FluentParticle;
import jw.fluent.api.spigot.tasks.FluentTaskFasade;
import jw.fluent.api.spigot.tasks.FluentTasks;
import jw.fluent.plugin.implementation.modules.player_context.implementation.FluentPlayerContext;

public class FluentApiSpigot
{
    private FluentPlayerContext fluentPlayerContext;
    private FluentParticle particle;
    private SimpleCommand defaultCommand;

    public FluentMessage message()
    {
       return new FluentMessage();
    }

    private CommandBuilder commandBuilder;

    public FluentApiSpigot(CommandBuilder commandBuilder,
                           FluentPlayerContext fluentPlayerContext)
    {
        this.commandBuilder = commandBuilder;
        this.fluentPlayerContext = fluentPlayerContext;
        this.particle = new FluentParticle();
    }




    public FluentPlayerContext playerContext()
    {
        return fluentPlayerContext;
    }



    public void gui() {

    }

    public void commands() {

    }
    public SimpleCommand defaultCommand()
    {
        return defaultCommand;
    }
    public FluentParticle particles()
    {
        return particle;
    }

    public FluentTaskFasade tasks() {
        return FluentTasks.getInstance();
    }

    public void events()
    {

    }
}