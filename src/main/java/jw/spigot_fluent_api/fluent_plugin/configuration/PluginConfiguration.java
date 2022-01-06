package jw.spigot_fluent_api.fluent_plugin.configuration;

import jw.spigot_fluent_api.data.DataContext;
import jw.spigot_fluent_api.data.DataHandler;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.configuration.actions.ConfigAction;

import java.util.function.Consumer;

public interface PluginConfiguration {
    PluginConfiguration useDependencyInjection();

    PluginConfiguration useDependencyInjection(Consumer<InjectionManager> configuration);

    PluginConfiguration useDataContext(Consumer<DataHandler> configuration);

     PluginConfiguration useDataContext();

    PluginConfiguration useInfoMessage();

    PluginConfiguration useCustomAction(ConfigAction configAction);

    PluginConfiguration useMetrics(int metricsId);

    PluginConfiguration useIntegrationTests();

    PluginConfiguration useDebugMode();
}
