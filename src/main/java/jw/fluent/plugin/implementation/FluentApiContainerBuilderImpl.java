package jw.fluent.plugin.implementation;

import jw.fluent.plugin.api.FluentApiContainerBuilder;
import jw.fluent.plugin.implementation.modules.decorator.FluentDecorator;
import jw.fluent.plugin.implementation.modules.decorator.FluentDecoratorExtention;
import jw.fluent.plugin.implementation.modules.documentation.DocumentationOptions;
import jw.fluent.plugin.implementation.modules.documentation.FluentDocumentationExtention;
import jw.fluent.plugin.implementation.modules.metrics.MetricsExtention;
import jw.fluent.plugin.implementation.modules.player_context.FluentPlayerContextExtention;
import jw.fluent.plugin.implementation.modules.resourcepack.ResourcepackExtention;
import jw.fluent.plugin.implementation.modules.resourcepack.ResourcepackOptions;
import jw.fluent.plugin.implementation.modules.updater.FluentUpdaterExtention;
import jw.fluent.plugin.api.extention.ExtentionPiority;
import jw.fluent.api.desing_patterns.decorator.api.builder.DecoratorBuilder;
import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.FluentContainerImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.events.EventHandlerImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.factory.InjectionInfoFactoryImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.provider.InstanceProviderImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.search.SearchAgentImpl;
import jw.fluent.api.logger.implementation.SimpleLoggerImpl;
import jw.fluent.api.updater.api.UpdaterOptions;
import jw.fluent.api.player_context.api.PlayerContainerBuilder;
import jw.fluent.plugin.api.extention.FluentApiExtentionsManager;
import jw.fluent.plugin.implementation.modules.websocket.FluentWebsocketExtention;
import jw.fluent.plugin.implementation.modules.websocket.api.WebsocketOptions;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;


public class FluentApiContainerBuilderImpl extends ContainerBuilderImpl<FluentApiContainerBuilder> implements FluentApiContainerBuilder
{
    private final FluentApiExtentionsManager extentionsManager;
    private final DecoratorBuilder decoratorBuilder;
    private final JavaPlugin plugin;

    public FluentApiContainerBuilderImpl(FluentApiExtentionsManager eventBuilder,
                                         JavaPlugin plugin)
    {
        this.extentionsManager = eventBuilder;
        this.plugin = plugin;
        this.decoratorBuilder = FluentDecorator.CreateDecorator();
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
    public FluentApiContainerBuilder addDocumentation(Consumer<DocumentationOptions> options) {
        extentionsManager.register(new FluentDocumentationExtention(options), ExtentionPiority.HIGH);
        return this;
    }
    @Override
    public FluentApiContainerBuilder addDocumentation() {
        extentionsManager.register(new FluentDocumentationExtention((e)->{}));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addResourcePack(Consumer<ResourcepackOptions> options) {
        extentionsManager.register(new ResourcepackExtention(options));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addPlayerContext(Consumer<PlayerContainerBuilder> options) {
        extentionsManager.register(new FluentPlayerContextExtention(options));
        return this;
    }

    public FluentApiContainerBuilder addPlayerContext() {
        extentionsManager.register(new FluentPlayerContextExtention((e)->{}));
        return this;
    }

    @Override
    public <T> FluentApiContainerBuilder registerDecorator(Class<T> _interface, Class<? extends T> _implementaition)
    {
        decoratorBuilder.decorate(_interface,_implementaition);
        return this;
    }

    @Override
    public FluentApiContainerBuilder addMediator() {
        return this;
    }

    @Override
    public FluentApiContainerBuilder addWebSocket(Consumer<WebsocketOptions> options) {
        extentionsManager.register(new FluentWebsocketExtention(options));
        return this;
    }

    @Override
    public FluentApiContainerBuilder addWebSocket() {
        return addWebSocket((e)->{});
    }

    public FluentContainer build() throws Exception {
        extentionsManager.register(new FluentDecoratorExtention(decoratorBuilder));

        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new InstanceProviderImpl();
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var registrations = config.getRegistrations();
        var searchAgent = new SearchAgentImpl();


        var logger = new SimpleLoggerImpl(plugin.getLogger());
        return new FluentContainerImpl(
                searchAgent,
                instanceProvider,
                eventHandler,
                logger,
                injectionInfoFactory,
                registrations);
    }
}
