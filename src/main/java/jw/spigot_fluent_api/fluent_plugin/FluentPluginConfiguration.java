package jw.spigot_fluent_api.fluent_plugin;

import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.ContainerBuilder;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.DependecyInjectionContainerBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.*;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.DefaultCommandDto;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.DefaultPermissionsDto;
import jw.spigot_fluent_api.fluent_plugin.languages.LangAction;
import jw.spigot_fluent_api.fluent_plugin.languages.api.models.LangOptions;
import jw.spigot_fluent_api.fluent_plugin.updates.SimpleUpdateAction;
import jw.spigot_fluent_api.fluent_plugin.updates.api.data.UpdateDto;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluentPluginConfiguration implements PluginConfiguration {
    private PluginPipeline dataContext;
    private PluginPipeline integrationTests;
    private PluginPipeline infoMessage;
    private PluginPipeline metrics;
    private PluginPipeline updates;
    private final List<PluginPipeline> configurationActions;
    private final List<PluginPipeline> customActions;
    private DependecyInjectionContainerBuilder dependecyInjectionContainerBuilder;
    private Consumer<LangOptions> langOptionsConsumer;
    private DefaultCommandDto defaultCommandDto;
    private DefaultPermissionsDto defaultPermissionsDto;

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
    public PluginConfiguration useCustomAction(PluginPipeline pipeline) {
        customActions.add(pipeline);
        return this;
    }

    @Override
    public PluginConfiguration useUpdates(Consumer<UpdateDto> consumer)
    {
        updates = new SimpleUpdateAction(consumer);
        return this;
    }


    public PluginConfiguration useDefaultCommand(String name, Consumer<CommandBuilder> consumer)
    {
        defaultCommandDto = new DefaultCommandDto(name,consumer);
        return this;
    }

    @Override
    public PluginConfiguration userDefaultPermission(String name) {
        defaultPermissionsDto = new DefaultPermissionsDto(name);
        return this;
    }

    public PluginConfiguration configureLanguages(Consumer<LangOptions> langConfig)
    {
        langOptionsConsumer =langConfig;
        return this;
    }

    @Override
    public PluginConfiguration useMetrics(Supplier<Integer> metricsId)
    {
        metrics = new MetricsAction(metricsId);
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
                    FluentLogger.info("Plugins disabled");
                    Bukkit.getPluginManager().disablePlugins();

                })
                .nextStep()
                .buildAndRegister();
        return this;
    }

    public DefaultCommandDto getDefaultCommand()
    {
        return defaultCommandDto;
    }

    public DefaultPermissionsDto getDefaultPermissionsDto()
    {
        return defaultPermissionsDto;
    }

    @Override
    public PluginConfiguration configureLogs() {
        return null;
    }

    public List<PluginPipeline> getConfigurationActions() {

        addIfNotNull(new DependecyInjectionAction(dependecyInjectionContainerBuilder));
        addIfNotNull(new LangAction(langOptionsConsumer));
        addIfNotNull(updates);
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
