package jw.spigot_fluent_api.fluent_validator.api;

import jw.spigot_fluent_api.fluent_validator.api.builder.ValidatorConfiguration;

public interface ValidatorProfile<T>
{
    void configure(ValidatorConfiguration<T> configuration);
}
