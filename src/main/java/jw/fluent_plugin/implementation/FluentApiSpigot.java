package jw.fluent_plugin.implementation;

import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.commands.implementation.SimpleCommand;
import jw.fluent_api.spigot.messages.FluentMessage;
import jw.fluent_api.spigot.particles.FluentParticle;
import jw.fluent_api.spigot.tasks.FluentTaskFasade;
import jw.fluent_api.spigot.tasks.FluentTasks;
import jw.fluent_plugin.implementation.modules.player_context.FluentPlayerContext;

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
    }

    public void init()
    {
        defaultCommand = commandBuilder.build();
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