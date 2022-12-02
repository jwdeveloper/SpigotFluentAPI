package jw.fluent_plugin.implementation.modules.updater;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_api.spigot.permissions.api.enums.Visibility;
import jw.fluent_api.updater.api.UpdaterOptions;
import jw.fluent_api.updater.implementation.SimpleUpdater;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class FluentUpdaterExtention implements FluentApiExtention {

    private final Consumer<UpdaterOptions> consumer;
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
            var updater = new FluentUpdaterImpl(simpleUpdater);
            return updater;
        });

        var permission = createPermission(builder.permissions());
        builder.permissions().registerPermission(permission);
        builder.command()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(updatesCommand(permission));
                });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {
        var updater= fluentAPI.getFluentInjection().findInjection(FluentUpdater.class);
        updater.checkUpdate(Bukkit.getConsoleSender());
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }

    private CommandBuilder updatesCommand(PermissionModel permission) {
        return FluentCommand.create("update")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission.getName());
                })
                .eventsConfig(eventConfig ->
                {
                    var updater= FluentApi.injection().findInjection(FluentUpdater.class);
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
        var pluginName = builder.getBasePermission();
        permission.setName(pluginName+".commands.update");
        permission.setDescription("/"+pluginName+" update  (Updates plugin)");
        permission.setVisibility(Visibility.Op);
        FluentLogger.LOGGER.success("COMMAND PARENT GROUP",builder.defaultPermissionSections().commands().getParentGroup());
        permission.getGroups().add(builder.defaultPermissionSections().commands().getParentGroup());
        return permission;
    }
}
