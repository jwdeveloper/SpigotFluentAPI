package jw.fluent_plugin.implementation.modules.spigot;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.FluentApiSpigot;
import jw.fluent_plugin.implementation.modules.player_context.FluentPlayerContext;

public class FluentApiSpigotExtention implements FluentApiExtention {

    private FluentApiSpigot spigot;

    @Override
    public void onConfiguration(FluentApiBuilder builder)
    {
        builder.container().register(FluentApiSpigot.class, LifeTime.SINGLETON, container ->
        {
            var playerContext = (FluentPlayerContext)container.find(FluentPlayerContext.class);
            spigot = new FluentApiSpigot(builder.command(), playerContext);
            return spigot;
        });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI)
    {
        spigot.init();
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
