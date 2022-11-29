package jw.fluent_api.validator.implementation.builder;

import jw.fluent_api.validator.api.builder.PropertyValidator;
import jw.fluent_api.validator.api.builder.ValidatorConfiguration;
import jw.fluent_api.validator.implementation.ValidationDto;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

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
            FluentLogger.LOGGER.error("Validator exception",e);
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
