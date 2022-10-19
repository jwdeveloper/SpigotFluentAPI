package jw.spigot_fluent_api.fluent_plugin;

import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginOptions;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.*;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.DefaultCommandDto;
import jw.spigot_fluent_api.fluent_plugin.default_actions.implementation.languages.LangAction;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import lombok.Getter;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPluginConfiguration implements PluginConfiguration {
    private PluginPipeline dataContext;
    private PluginPipeline integrationTests;
    private final List<PluginPipeline> configurationActions;
    private final List<PluginPipeline> customActions;
    private ContainerBuilderImpl dependecyInjectionContainerBuilder;
    private DefaultCommandDto defaultCommandDto;
    @Getter
    private PluginOptionsImpl pluginOptions;

    public FluentPluginConfiguration() {
        this.configurationActions = new ArrayList<>();
        this.customActions = new ArrayList<>();
        pluginOptions = new PluginOptionsImpl();
        dependecyInjectionContainerBuilder = new ContainerBuilderImpl();
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



    public PluginConfiguration useDefaultCommand(String name, Consumer<CommandBuilder> consumer)
    {
        defaultCommandDto = new DefaultCommandDto(name,consumer);
        return this;
    }

    @Override
    public PluginConfiguration configurePlugin(Consumer<PluginOptions> optionsConsumer)
    {
        optionsConsumer.accept(pluginOptions);
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




    public List<PluginPipeline> getConfigurationActions() {

        addIfNotNull(new DependecyInjectionAction(dependecyInjectionContainerBuilder));
        addIfNotNull(new PluginConfigurationAction(pluginOptions));
        addIfNotNull(new LangAction(pluginOptions));
        addIfNotNull(dataContext);
        addIfNotNull(new MediatorAction());
        addIfNotNull(new MapperAction());
        addIfNotNull(new ValidatorAction());

        configurationActions.addAll(customActions);
        addIfNotNull(integrationTests);
        return configurationActions;
    }



    private void addIfNotNull(PluginPipeline action) {
        if (action != null)
            configurationActions.add(action);
    }


}
