package jw.fluent_plugin.api;

import jw.fluent_api.files.api.DataContext;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;

import java.util.function.Consumer;

public interface PluginConfiguration {

    PluginConfiguration configureDependencyInjection(Consumer<ContainerBuilder> configuration);

    PluginConfiguration useFilesHandler(Consumer<DataContext> configuration);

    PluginConfiguration useFilesHandler();

    PluginConfiguration useCustomAction(PluginAction pipeline);

    PluginConfiguration useDebugMode();

    PluginConfiguration useDefaultCommand(String name, Consumer<CommandBuilder> consumer);

    PluginConfiguration configurePlugin(Consumer<PluginOptions> optionsConsumer);

     PluginConfiguration runSmokeTests();
}