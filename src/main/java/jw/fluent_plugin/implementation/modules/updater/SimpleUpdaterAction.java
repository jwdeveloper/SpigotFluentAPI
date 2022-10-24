package jw.fluent_plugin.implementation.modules.updater;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.default_actions.DefaultAction;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;
import jw.fluent_api.updater.implementation.SimpleUpdater;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
import jw.fluent_api.updater.api.UpdateDto;
import jw.fluent_api.utilites.java.JavaUtils;
import org.bukkit.Bukkit;

public class SimpleUpdaterAction extends DefaultAction {

    private SimpleUpdater simpleUpdater;

    public SimpleUpdaterAction(PluginOptionsImpl pluginOptions) {
        super(pluginOptions);
    }


    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var url = pluginOptions.getUpdateURL();
        if (pluginOptions.getUpdateURL().equals(JavaUtils.EMPTY_STRING)) {
            return;
        }
        var dto = new UpdateDto();
        dto.setGithub(url);
        dto.setUpdateCommandName(options.getDefaultCommand().getName());
        simpleUpdater = new SimpleUpdater(dto, options.getPlugin());


        var registrationInfo = new RegistrationInfo(SimpleUpdater.class,
                null,
                (x)->{ return simpleUpdater;},
                LifeTime.SINGLETON,
                RegistrationType.InterfaceAndProvider);
        var injection = (FluentInjectionImpl) FluentAPI.injection();
        injection.getContainer().register(registrationInfo);

        simpleUpdater.checkUpdate(Bukkit.getConsoleSender());

        registerCommand(options);
    }

    public void registerCommand(PipelineOptions options) throws Exception {
        if (!options.getDefaultCommand().hasDefaultCommand()) {
            throw new Exception("default command is required for " + this.getClass().getSimpleName());
        }

        var permission = options.getPluginOptions().getPermissionName();
        options.getDefaultCommand()
                .getBuilder()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(updatesCommand(permission));
                });
    }


    public CommandBuilder updatesCommand(String permission) {
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
                        simpleUpdater.downloadUpdate(consoleCommandEvent.getConsoleSender());
                    });
                    eventConfig.onPlayerExecute(event ->
                    {
                        simpleUpdater.downloadUpdate(event.getSender());
                    });
                });
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
