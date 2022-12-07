package jw.fluent.plugin.api;


import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.fluent.api.updater.api.UpdaterOptions;
import jw.fluent.api.player_context.api.PlayerContainerBuilder;
import jw.fluent.plugin.implementation.modules.documentation.DocumentationOptions;
import jw.fluent.plugin.implementation.modules.resourcepack.ResourcepackOptions;
import jw.fluent.plugin.implementation.modules.websocket.api.WebsocketOptions;

import java.util.function.Consumer;


public interface FluentApiContainerBuilder extends ContainerBuilder<FluentApiContainerBuilder> {
    FluentApiContainerBuilder addMediator();

    FluentApiContainerBuilder addMetrics(int metricsId);

    FluentApiContainerBuilder addUpdater(Consumer<UpdaterOptions> options);

    FluentApiContainerBuilder addDocumentation(Consumer<DocumentationOptions> options);

    FluentApiContainerBuilder addDocumentation();

    FluentApiContainerBuilder addResourcePack(Consumer<ResourcepackOptions> options);

    FluentApiContainerBuilder addWebSocket(Consumer<WebsocketOptions> options);

    FluentApiContainerBuilder addWebSocket();

    FluentApiContainerBuilder addPlayerContext();

    FluentApiContainerBuilder addPlayerContext(Consumer<PlayerContainerBuilder> options);

    <T> FluentApiContainerBuilder registerDecorator(Class<T> _interface, Class<? extends T> _implementaition);
}
