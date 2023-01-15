package jw.fluent.plugin.implementation.modules.updater;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.api.spigot.permissions.api.enums.Visibility;
import jw.fluent.api.updater.api.UpdaterOptions;
import jw.fluent.api.updater.implementation.SimpleUpdater;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class FluentUpdaterExtention implements FluentApiExtension {

    private final Consumer<UpdaterOptions> consumer;
    private final String commandName = "update";
    public FluentUpdaterExtention(Consumer<UpdaterOptions> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().register(FluentUpdater.class, LifeTime.SINGLETON, (c) ->
        {
            var options = new UpdaterOptions();
            consumer.accept(options);
            var simpleUpdater = new SimpleUpdater(options, builder.plugin(),builder.defaultCommand().getName());
            return new FluentUpdaterImpl(simpleUpdater);
        });

        var permission = createPermission(builder.permissions());
        builder.permissions().registerPermission(permission);
        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(updatesCommand(permission, builder.defaultCommand().getName()));
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var updater= fluentAPI.container().findInjection(FluentUpdater.class);
        updater.checkUpdate(Bukkit.getConsoleSender());
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }

    private CommandBuilder updatesCommand(PermissionModel permission, String defaultCommandName) {
        return FluentCommand.create(commandName)
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission.getName());
                    propertiesConfig.setDescription("download plugin latest version, can be trigger both by player or console");
                    propertiesConfig.setUsageMessage("/"+defaultCommandName+" "+commandName);
                })
                .eventsConfig(eventConfig ->
                {
                    var updater= FluentApi.container().findInjection(FluentUpdater.class);
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

    private PermissionModel createPermission(FluentPermissionBuilder builder)
    {
        var permission = new PermissionModel();
        permission.setName("update");
        permission.setDescription("players with this permission can update plugin");
        builder.defaultPermissionSections().commands().addChild(permission);
        return permission;
    }
}
