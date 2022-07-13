package jw.spigot_fluent_api.fluent_plugin;

import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.ContainerBuilder;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.DependecyInjectionContainerBuilder;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.*;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPluginConfiguration implements PluginConfiguration {
    private PluginPipeline dataContext;
    private PluginPipeline integrationTests;
    private PluginPipeline infoMessage;
    private PluginPipeline metrics;
    private final List<PluginPipeline> configurationActions;
    private final List<PluginPipeline> customActions;
    private DependecyInjectionContainerBuilder dependecyInjectionContainerBuilder;

    public FluentPluginConfiguration() {
        this.configurationActions = new ArrayList<>();
        this.customActions = new ArrayList<>();
        dependecyInjectionContainerBuilder = new DependecyInjectionContainerBuilder();
    }

    @Override
    public PluginConfiguration configureDependencyInjection(Consumer<ContainerBuilder> configuration)
    {
        configuration.accept(dependecyInjectionContainerBuilder);
        return this;
    }

    @Override
    public PluginConfiguration useFilesHandler() {
        dataContext = new DataContextAction();
        return this;
    }


    @Override
    public PluginConfiguration useFilesHandler(Consumer<FluentDataContext> configuration) {
        dataContext = new DataContextAction(configuration);
        return this;
    }

    @Override
    public PluginConfiguration useInfoMessage() {
        infoMessage = new InfoMessageAction();
        return this;
    }

    @Override
    public PluginConfiguration useCustomAction(PluginPipeline pipeline) {
        customActions.add(pipeline);
        return this;
    }

    @Override
    public PluginConfiguration useMetrics(int metricsId) {
        metrics = new MetricsAction(metricsId);
        return this;
    }

    @Override
    public PluginConfiguration useIntegrationTests() {
        integrationTests = new IntegrationTestAction();
        return this;
    }

    @Override
    public PluginConfiguration useDebugMode() {

        FluentCommand.create_OLDWAY("disable")
                .setDescription("disable all plugin without restarting server")
                .setUsageMessage("Can be use only with Console")
                .nextStep()
                .nextStep()
                .onConsoleExecute(consoleCommandEvent ->
                {
                    Bukkit.getPluginManager().disablePlugins();
                    FluentPlugin.logInfo("Plugins disabled");
                })
                .nextStep()
                .buildAndRegister();
        return this;
    }

    @Override
    public PluginConfiguration configureLogs() {
        return null;
    }

    public List<PluginPipeline> getConfigurationActions() {
        addIfNotNull(new DependecyInjectionAction(dependecyInjectionContainerBuilder));
        addIfNotNull(dataContext);
        addIfNotNull(new MediatorAction());
        addIfNotNull(new MapperAction());
        addIfNotNull(new ValidatorAction());
        addIfNotNull(metrics);
        configurationActions.addAll(customActions);
        addIfNotNull(integrationTests);
        addIfNotNull(infoMessage);
        return configurationActions;
    }


    private void addIfNotNull(PluginPipeline action) {
        if (action != null)
            configurationActions.add(action);
    }


}
