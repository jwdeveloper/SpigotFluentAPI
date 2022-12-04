package jw.fluent.api.player_context.api;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;

public interface PlayerContainerBuilder extends ContainerBuilder<PlayerContainerBuilder> {

    public Container build();

}
