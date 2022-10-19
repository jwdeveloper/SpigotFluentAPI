package jw.spigot_fluent_api.fluent_plugin.starup_actions.api;

import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;

import java.util.function.Consumer;

public interface PluginConfiguration {

    PluginConfiguration configureDependencyInjection(Consumer<ContainerBuilder> configuration);

    PluginConfiguration useFilesHandler(Consumer<FluentDataContext> configuration);

    PluginConfiguration useFilesHandler();

    PluginConfiguration useCustomAction(PluginPipeline pipeline);

    PluginConfiguration useDebugMode();

    PluginConfiguration useDefaultCommand(String name, Consumer<CommandBuilder> consumer);

    PluginConfiguration configurePlugin(Consumer<PluginOptions> optionsConsumer);

     PluginConfiguration useIntegrationTests();
}
