package jw.fluent_plugin.implementation;

import jw.fluent_api.spigot.particles.FluentParticle;
import jw.fluent_api.spigot.tasks.FluentTaskFasade;
import jw.fluent_api.spigot.tasks.FluentTasks;
import jw.fluent_plugin.implementation.modules.player_context.FluentPlayerContext;

public class FluentAPISpigot
{
    private FluentPlayerContext fluentPlayerContext;
    private FluentParticle particle;

    public FluentPlayerContext playerContext()
    {
          return fluentPlayerContext;
    }
    public void message()
    {

    }

    public void gui() {

    }

    public void commands() {

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