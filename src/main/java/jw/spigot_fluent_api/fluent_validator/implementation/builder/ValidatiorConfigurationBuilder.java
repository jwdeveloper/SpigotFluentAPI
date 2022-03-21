package jw.spigot_fluent_api.fluent_validator.implementation.builder;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_validator.api.builder.PropertyValidator;
import jw.spigot_fluent_api.fluent_validator.api.builder.ValidatorConfiguration;
import jw.spigot_fluent_api.fluent_validator.implementation.ValidationDto;

public class ValidatiorConfigurationBuilder<T> implements ValidatorConfiguration<T>
{
    private final ValidationDto validationDto;
    private final Class _class;

    public ValidatiorConfigurationBuilder(Class classType)
    {
        validationDto = new ValidationDto();
        _class = classType;
    }

    public PropertyValidator<T> forProperty(String name)
    {
        try {
            var field = _class.getDeclaredField(name);
            return new PropertyValidatorBuilder(this,field,validationDto);
        } catch (NoSuchFieldException e)
        {
            FluentPlugin.logException("Validator exception",e);
            return null;
        }
    }

    public ValidationDto build()
    {
        return validationDto;
    }

    public ValidationDto buildAndRegister()
    {
        return validationDto;
    }
}
