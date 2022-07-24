package jw.spigot_fluent_api.fluent_plugin.configuration;

import jw.spigot_fluent_api.data.interfaces.FileHandler;
import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.ContainerBuilder;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.updates.api.data.UpdateDto;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface PluginConfiguration {

    PluginConfiguration configureDependencyInjection(Consumer<ContainerBuilder> configuration);

    PluginConfiguration useFilesHandler(Consumer<FluentDataContext> configuration);

    PluginConfiguration useFilesHandler();

    PluginConfiguration useInfoMessage();

    PluginConfiguration useCustomAction(PluginPipeline pipeline);

    PluginConfiguration useMetrics(Supplier<Integer> metricsId);

    PluginConfiguration useMetrics(int metricsId);

    PluginConfiguration useIntegrationTests();

    PluginConfiguration useDebugMode();

    PluginConfiguration configureLogs();

    PluginConfiguration useUpdates(Consumer<UpdateDto> dto);
}
