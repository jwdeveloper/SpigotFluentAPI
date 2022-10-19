package jw.fluent_plugin.default_actions.implementation.updates;

import jw.fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_plugin.FluentPlugin;
import jw.fluent_plugin.default_actions.api.DefaultAction;
import jw.fluent_plugin.default_actions.implementation.updates.implementation.SimpleUpdater;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_api.minecraft.commands.FluentCommand;
import jw.fluent_api.minecraft.commands.api.builder.CommandBuilder;
import jw.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.fluent_plugin.default_actions.implementation.updates.api.data.UpdateDto;
import jw.fluent_api.utilites.java.JavaUtils;
import org.bukkit.Bukkit;

public class SimpleUpdateAction extends DefaultAction {


    private SimpleUpdater simpleUpdater;

    public SimpleUpdateAction(PluginOptionsImpl pluginOptions) {
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
        FluentInjection.getContainer().register(registrationInfo);
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
