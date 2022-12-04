package jw.fluent.api.validator.api;

import jw.fluent.api.validator.api.builder.ValidatorConfiguration;

public interface ValidatorProfile<T>
{
    void configure(ValidatorConfiguration<T> configuration);
}
