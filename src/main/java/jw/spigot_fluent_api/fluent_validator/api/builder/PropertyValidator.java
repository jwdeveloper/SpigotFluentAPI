package jw.spigot_fluent_api.fluent_validator.api.builder;


import jw.spigot_fluent_api.fluent_validator.api.ValidationAction;
import jw.spigot_fluent_api.fluent_validator.implementation.builder.ValidatiorConfigurationBuilder;

public interface PropertyValidator<T>
{
    PropertyValidator<T> notNull();
    PropertyValidator<T> maxSize(int length);
    PropertyValidator<T> minSize(int length);
    PropertyValidator<T> betweenSize(int min, int max);
    PropertyValidator<T> notEmpty();
    PropertyValidator<T> customValidation(ValidationAction<T> validationAction);

    public ValidatiorConfigurationBuilder build();
}
