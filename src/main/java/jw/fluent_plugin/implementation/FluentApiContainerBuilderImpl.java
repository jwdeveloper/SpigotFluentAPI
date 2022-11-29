package jw.fluent_plugin.implementation;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.FluentContainerImpl;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.events.EventHandlerImpl;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.factory.InjectionInfoFactoryImpl;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.provider.InstanceProviderImpl;
import jw.fluent_api.logger.implementation.SimpleLoggerImpl;
import jw.fluent_api.updater.api.UpdaterOptions;
import jw.fluent_plugin.implementation.modules.player_context.PlayerContainerBuilder;
import jw.fluent_plugin.implementation.modules.player_context.PlayerContainerBuilderImpl;
import jw.fluent_plugin.implementation.modules.player_context.PlayerContextExtention;
import jw.fluent_plugin.implementation.modules.updater.FluentUpdaterExtention;
import jw.fluent_plugin.api.FluentApiContainerBuilder;
import jw.fluent_plugin.api.extention.FluentApiExtentionsManager;
import jw.fluent_plugin.implementation.modules.metrics.MetricsExtention;
import jw.fluent_plugin.implementation.modules.resourcepack.ResourcepackExtention;
import jw.fluent_plugin.implementation.modules.resourcepack.ResourcepackOptions;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.function.Consumer;


public class FluentApiContainerBuilderImpl extends ContainerBuilderImpl<FluentApiContainerBuilder> implements FluentApiContainerBuilder
{
    private final FluentApiExtentionsManager extentionsManager;
    private final JavaPlugin plugin;

    public FluentApiContainerBuilderImpl(FluentApiExtentionsManager eventBuilder,
                                         JavaPlugin plugin)
    {
        this.extentionsManager = eventBuilder;
        this.plugin = plugin;
    }

    @Override
    public FluentApiContainerBuilder addMetrics(int metricsId)
    {
        extentionsManager.register(new MetricsExtention(metricsId));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addUpdater(Consumer<UpdaterOptions> options) {
        extentionsManager.register(new FluentUpdaterExtention(options));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addResourcePacks(Consumer<ResourcepackOptions> options) {
        extentionsManager.register(new ResourcepackExtention(options));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addPlayerContext(Consumer<PlayerContainerBuilder> options) {
        extentionsManager.register(new PlayerContextExtention(options));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addMediator() {
        return this;
    }

    @Override
    public FluentApiContainerBuilder addWebSocket() {
        return this;
    }

    public FluentContainer buildFluentContainer() throws Exception {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new InstanceProviderImpl();
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var registrations = config.getRegistrations();



        var logger = new SimpleLoggerImpl(plugin.getLogger());
        return new FluentContainerImpl(
                instanceProvider,
                eventHandler,
                logger,
                injectionInfoFactory,
                registrations);
    }
}
