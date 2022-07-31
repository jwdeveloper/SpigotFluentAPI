package jw.spigot_fluent_api.fluent_plugin.starup_actions;

import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.ContainerBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.updates.api.data.UpdateDto;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface PluginConfiguration {

    PluginConfiguration configureDependencyInjection(Consumer<ContainerBuilder> configuration);

    PluginConfiguration useFilesHandler(Consumer<FluentDataContext> configuration);

    PluginConfiguration useFilesHandler();

    PluginConfiguration useCustomAction(PluginPipeline pipeline);

    PluginConfiguration useMetrics(Supplier<Integer> metricsId);

    PluginConfiguration useMetrics(int metricsId);

    PluginConfiguration useIntegrationTests();

    PluginConfiguration useDebugMode();

    PluginConfiguration configureLogs();

    PluginConfiguration useUpdates(Consumer<UpdateDto> dto);

    PluginConfiguration useDefaultCommand(String name, Consumer<CommandBuilder> consumer);

    PluginConfiguration userDefaultPermission(String name);
}
