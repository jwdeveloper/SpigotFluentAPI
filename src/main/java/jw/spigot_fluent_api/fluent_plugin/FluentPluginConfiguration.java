package jw.spigot_fluent_api.fluent_plugin;
import jw.spigot_fluent_api.data.DataHandler;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.*;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPluginConfiguration implements PluginConfiguration {
    private PluginPipeline dataContext;
    private PluginPipeline dependencyInjection;
    private PluginPipeline integrationTests;
    private PluginPipeline infoMessage;
    private PluginPipeline metrics;
    private final List<PluginPipeline> configurationActions;
    private final List<PluginPipeline> customActions;

    public FluentPluginConfiguration() {
        this.configurationActions =new ArrayList<>();
        this.customActions = new ArrayList<>();
    }

    @Override
    public PluginConfiguration useDependencyInjection()
    {
        dependencyInjection = new DependencyInjectionAction();
        return this;
    }
    @Override
    public PluginConfiguration useDependencyInjection(Consumer<InjectionManager> configuration)
    {
        dependencyInjection = new DependencyInjectionAction(configuration);
        return this;
    }

    @Override
    public PluginConfiguration useDataContext()
    {
        dataContext = new DataContextAction();
        return this;
    }


    @Override
    public PluginConfiguration useDataContext(Consumer<DataHandler> configuration)
    {
        dataContext = new DataContextAction(configuration);
        return this;
    }

    @Override
    public PluginConfiguration useInfoMessage()
    {
        infoMessage = new InfoMessageAction();
        return this;
    }

    @Override
    public PluginConfiguration useCustomAction(PluginPipeline pipeline)
    {
        customActions.add(pipeline);
        return this;
    }

    @Override
    public PluginConfiguration useMetrics(int metricsId)
    {
        metrics = new MetricsAction(metricsId);
        return this;
    }
    @Override
    public PluginConfiguration useIntegrationTests()
    {
        integrationTests = new IntegrationTestAction();
        return this;
    }

    @Override
    public PluginConfiguration useDebugMode()
    {

        SimpleCommand.newCommand("disable")
                        .setDescription("disable all plugin without restarting server")
                        .setUsageMessage("Can be use only with Console")
                        .onConsoleExecute(consoleCommandEvent ->
                        {
                            Bukkit.getPluginManager().disablePlugins();
                            FluentPlugin.logInfo("Plugins disabled");
                        })
                        .register();
        return this;
    }

    @Override
    public PluginConfiguration configureLogs() {
        return null;
    }

    public List<PluginPipeline> getConfigurationActions()
    {
        addIfNotNull(dependencyInjection);
        addIfNotNull(dataContext);
        addIfNotNull(metrics);
        configurationActions.addAll(customActions);
        addIfNotNull(integrationTests);
        addIfNotNull(infoMessage);
        return configurationActions;
    }


    private void addIfNotNull(PluginPipeline action)
    {
        if(action != null)
            configurationActions.add(action);
    }



}
