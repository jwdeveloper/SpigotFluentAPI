package jw.fluent_plugin.implementation.modules.updater;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.updater.api.UpdaterOptions;
import jw.fluent_api.updater.implementation.SimpleUpdater;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class FluentUpdaterExtention implements FluentApiExtention {

    private final Consumer<UpdaterOptions> consumer;
    private FluentUpdaterImpl updater;

    public FluentUpdaterExtention(Consumer<UpdaterOptions> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        builder.container().register(FluentUpdater.class, LifeTime.SINGLETON, (c) ->
        {
            var options = new UpdaterOptions();
            consumer.accept(options);
            var simpleUpdater = new SimpleUpdater(options, builder.plugin());
            updater = new FluentUpdaterImpl(simpleUpdater);
            return updater;
        });

        builder.command()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(updatesCommand("default"));
                });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {
        updater.checkUpdate(Bukkit.getConsoleSender());
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }

    private CommandBuilder updatesCommand(String permission) {
        return FluentCommand.create("update")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission);
                    propertiesConfig.addPermissions(permission + ".commands.update");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onConsoleExecute(consoleCommandEvent ->
                    {
                        updater.downloadUpdate(consoleCommandEvent.getConsoleSender());
                    });
                    eventConfig.onPlayerExecute(event ->
                    {
                        updater.downloadUpdate(event.getSender());
                    });
                });
    }
}
