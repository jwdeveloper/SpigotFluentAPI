package jw.fluent_api.validator.api;

import jw.fluent_api.validator.api.builder.ValidatorConfiguration;

public interface ValidatorProfile<T>
{
    void configure(ValidatorConfiguration<T> configuration);
}
