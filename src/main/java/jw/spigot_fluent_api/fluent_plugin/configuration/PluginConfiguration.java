package jw.spigot_fluent_api.fluent_plugin.configuration;

import jw.spigot_fluent_api.data.DataHandler;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

import java.util.function.Consumer;

public interface PluginConfiguration {
    PluginConfiguration useDependencyInjection();

    PluginConfiguration useDependencyInjection(Consumer<InjectionManager> configuration);

    PluginConfiguration useDataContext(Consumer<DataHandler> configuration);

     PluginConfiguration useDataContext();

    PluginConfiguration useInfoMessage();

    PluginConfiguration useCustomAction(PluginPipeline pipeline);

    PluginConfiguration useMetrics(int metricsId);

    PluginConfiguration useIntegrationTests();

    PluginConfiguration useDebugMode();

    PluginConfiguration configureLogs();
}
