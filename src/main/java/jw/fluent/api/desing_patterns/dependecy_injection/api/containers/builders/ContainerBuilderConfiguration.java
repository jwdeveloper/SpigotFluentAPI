package jw.fluent.api.desing_patterns.dependecy_injection.api.containers.builders;

import jw.fluent.api.desing_patterns.dependecy_injection.api.models.ContainerConfiguration;

import java.util.function.Consumer;

public interface ContainerBuilderConfiguration
{
    public ContainerBuilder configure(Consumer<ContainerConfiguration> configuration);
}
