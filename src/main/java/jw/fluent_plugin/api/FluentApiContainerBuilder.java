package jw.fluent_plugin.api;


import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.fluent_api.updater.api.UpdaterOptions;
import jw.fluent_plugin.implementation.modules.player_context.PlayerContainerBuilder;
import jw.fluent_plugin.implementation.modules.resourcepack.ResourcepackOptions;

import java.util.function.Consumer;


public interface FluentApiContainerBuilder extends ContainerBuilder<FluentApiContainerBuilder> {
     FluentApiContainerBuilder addMediator();
     FluentApiContainerBuilder addMetrics(int metricsId);
     FluentApiContainerBuilder addUpdater(Consumer<UpdaterOptions> options);
     FluentApiContainerBuilder addResourcePacks(Consumer<ResourcepackOptions> options);
     FluentApiContainerBuilder addWebSocket();
     FluentApiContainerBuilder addPlayerContext(Consumer<PlayerContainerBuilder> options);
}
