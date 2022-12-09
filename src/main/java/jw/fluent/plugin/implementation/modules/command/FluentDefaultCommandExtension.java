package jw.fluent.plugin.implementation.modules.command;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class FluentDefaultCommandExtension implements FluentApiExtension {

    private CommandBuilder commandBuilder;

    public FluentDefaultCommandExtension(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        commandBuilder.build();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
