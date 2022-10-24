package jw.fluent_plugin.implementation;

import jw.fluent_api.files.api.DataContext;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.implementation.modules.plugin.FluentPluginConfigurationAction;
import jw.fluent_plugin.implementation.modules.translator.FluentTranslatorAction;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionAction;
import jw.fluent_plugin.implementation.modules.files.FluentFilesAction;
import jw.fluent_plugin.implementation.modules.mapper.FluentMapperAction;
import jw.fluent_plugin.implementation.modules.mediator.FluentMediatorAction;
import jw.fluent_plugin.implementation.modules.tests.FluentTestsAction;
import jw.fluent_plugin.api.PluginOptions;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.logger.OldLogger;
import jw.fluent_plugin.api.PluginConfiguration;
import jw.fluent_plugin.api.data.DefaultCommandDto;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
import lombok.Getter;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPluginConfiguration implements PluginConfiguration {
    private PluginAction dataContext;
    private PluginAction integrationTests;
    private final List<PluginAction> configurationActions;
    private final List<PluginAction> customActions;
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
        dataContext = new FluentFilesAction();
        return this;
    }


    @Override
    public PluginConfiguration useFilesHandler(Consumer<DataContext> configuration) {
        dataContext = new FluentFilesAction(configuration);
        return this;
    }


    @Override
    public PluginConfiguration useCustomAction(PluginAction pipeline) {
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
    public PluginConfiguration runSmokeTests() {
        integrationTests = new FluentTestsAction();
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
                    OldLogger.info("Plugins disabled");
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




    public List<PluginAction> getConfigurationActions() {

        addIfNotNull(new FluentInjectionAction(dependecyInjectionContainerBuilder));
        addIfNotNull(new FluentPluginConfigurationAction(pluginOptions));
        addIfNotNull(new FluentTranslatorAction(pluginOptions));
        addIfNotNull(dataContext);
        addIfNotNull(new FluentMediatorAction());
        addIfNotNull(new FluentMapperAction());

        configurationActions.addAll(customActions);
        addIfNotNull(integrationTests);
        return configurationActions;
    }



    private void addIfNotNull(PluginAction action) {
        if (action != null)
            configurationActions.add(action);
    }


}
