package jw.fluent_api.player_context.api;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;

public interface PlayerContainerBuilder extends ContainerBuilder<PlayerContainerBuilder> {

    public Container build();

}
