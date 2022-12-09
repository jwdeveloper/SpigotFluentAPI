package jw.fluent.plugin.implementation;

import jw.fluent.api.files.api.SimpleFilesBuilder;
import jw.fluent.api.logger.api.SimpleLoggerBuilder;
import jw.fluent.plugin.api.FluentApiContainerBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import jw.fluent.plugin.implementation.modules.command.FluentApiCommandBuilder;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FluentApiBuilder implements FluentApiSpigotBuilder
{

    public static FluentApiBuilder create(JavaPlugin plugin)
    {
        FluentApi.setPlugin(plugin);
        return new FluentApiBuilder(new FluentApiSpiogtBuilderImpl(plugin));
    }


    private FluentApiSpiogtBuilderImpl builder;

    FluentApiBuilder(FluentApiSpiogtBuilderImpl builder)
    {
        this.builder = builder;
    }


    @Override
    public FluentApiCommandBuilder defaultCommand() {
        return builder.defaultCommand();
    }

    @Override
    public FluentApiContainerBuilder container() {
        return builder.container();
    }
    @Override
    public FluentApiSpigotBuilder useExtension(FluentApiExtension extension) {
        return builder.useExtension(extension);
    }

    @Override
    public SimpleLoggerBuilder logger() {
        return builder.logger();
    }

    @Override
    public SimpleFilesBuilder files() {
        return builder.files();
    }

    @Override
    public FluentConfig config() {
        return builder.config();
    }

    @Override
    public FluentPermissionBuilder permissions() {
        return builder.permissions();
    }

    @Override
    public JavaPlugin plugin() {
        return builder.plugin();
    }

    @Override
    public FluentAssemblyScanner assemblyScanner() {
        return builder.assemblyScanner();
    }

    public FluentApiSpigot build()
    {
        try
        {
            var api = builder.build();
            FluentApi.setFluentApiSpigot(api);
            api.enable();
            FluentApi.events().onEvent(PluginDisableEvent.class,event ->
            {
                 api.disable();
            });
            return api;
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Unable to initialize FluentAPI",e);
        }
        return null;
    }
}
