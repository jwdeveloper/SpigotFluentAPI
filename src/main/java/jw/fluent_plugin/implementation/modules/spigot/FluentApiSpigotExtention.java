package jw.fluent_plugin.implementation.modules.spigot;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.FluentApiSpigot;
import jw.fluent_plugin.implementation.modules.player_context.implementation.FluentPlayerContext;
import jw.fluent_plugin.implementation.modules.spigot.commands.FluentApiCommandBuilder;

public class FluentApiSpigotExtention implements FluentApiExtention {

    private FluentApiSpigot spigot;
    private FluentApiCommandBuilder commandBuilder;

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        builder.container().register(FluentApiSpigot.class, LifeTime.SINGLETON, container ->
        {
            var playerContext = (FluentPlayerContext) container.find(FluentPlayerContext.class);
            spigot = new FluentApiSpigot(builder.command(), playerContext);
            return spigot;
        });
        commandBuilder = builder.command();
    }


    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {
        commandBuilder.build();
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
