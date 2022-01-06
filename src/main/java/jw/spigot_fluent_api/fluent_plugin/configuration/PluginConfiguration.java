package jw.spigot_fluent_api.fluent_plugin.configuration;

import jw.spigot_fluent_api.data.DataContext;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.configuration.actions.ConfigAction;

import java.util.function.Consumer;

public interface PluginConfiguration {
    PluginConfiguration withDependencyInjection();

    PluginConfiguration withDependencyInjection(Consumer<InjectionManager> configuration);

    PluginConfiguration withDataContext(Consumer<DataContext> configuration);

    PluginConfiguration withInfoMessage();

    PluginConfiguration withCustomAction(ConfigAction configAction);

    PluginConfiguration withMetrics(int metricsId);

    PluginConfiguration withIntegrationTests();

    PluginConfiguration withDebugMode();
}
