package jw.spigot_fluent_api.fluent_plugin.updates;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_plugin.updates.api.data.UpdateDto;
import jw.spigot_fluent_api.fluent_plugin.updates.implementation.SimpleUpdater;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class SimpleUpdateAction implements PluginPipeline {

    private Consumer<UpdateDto> consumer;
    private SimpleUpdater simpleUpdater;

    public SimpleUpdateAction(Consumer<UpdateDto> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var dto = new UpdateDto();
        consumer.accept(dto);
        dto.setUpdateCommandName(options.getDefaultCommand().getName());
        simpleUpdater = new SimpleUpdater(dto, options.getPlugin());
        FluentInjection.getInjectionContainer().register(simpleUpdater);
        simpleUpdater.checkUpdate(Bukkit.getConsoleSender());

        registerCommand(options);
    }

    public void registerCommand(PipelineOptions options) throws Exception {
        if (options.getDefaultCommand() == null) {
            throw new Exception("default command is required for "+this.getClass().getSimpleName());
        }
        if (options.getDefaultPermissions() == null) {
            throw new Exception("default permission is required for Lang Action"+this.getClass().getSimpleName());
        }

        var permission = options.getDefaultPermissions().getName();
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
                    propertiesConfig.addPermissions(permission + ".update");
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
