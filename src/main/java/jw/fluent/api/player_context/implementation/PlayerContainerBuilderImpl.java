package jw.fluent.api.player_context.implementation;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.DefaultContainer;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.events.EventHandlerImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.factory.InjectionInfoFactoryImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.search.SearchAgentImpl;
import jw.fluent.api.logger.implementation.SimpleLoggerImpl;
import jw.fluent.api.player_context.api.PlayerContainerBuilder;

import java.util.logging.Logger;

public class PlayerContainerBuilderImpl extends ContainerBuilderImpl<PlayerContainerBuilder> implements PlayerContainerBuilder {
    private Container parentContainer;

    public PlayerContainerBuilder setParentContainer(Container container) {
        parentContainer = container;
        return this;
    }

    @Override
    public Container build() {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new PlayerContextInstanceProvider(parentContainer);
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var searchAgent = new SearchAgentImpl();

        return new DefaultContainer(
                searchAgent,
                instanceProvider,
                eventHandler,
                new SimpleLoggerImpl(Logger.getLogger("player container")),
                injectionInfoFactory,
                config.getRegistrations());
    }
}
