package jw.fluent_plugin.implementation.modules.commands;

import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;

public class CommandExtention implements FluentApiExtention {


    private final CommandBuilder commandBuilder;

    public CommandExtention(CommandBuilder commandBuilder)
    {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

        commandBuilder.build();
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }
}
