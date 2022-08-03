package jw.spigot_fluent_api.fluent_validator.implementation.builder;

import jw.spigot_fluent_api.fluent_validator.api.ValidationAction;
import jw.spigot_fluent_api.fluent_validator.api.builder.PropertyValidator;
import jw.spigot_fluent_api.fluent_validator.implementation.ValidationDto;
import jw.spigot_fluent_api.fluent_validator.implementation.action.NotNullAction;

import java.lang.reflect.Field;

public class PropertyValidatorBuilder<T> implements PropertyValidator<T> {

    private final ValidatiorConfigurationBuilder validatiorBuilder;
    private final Field field;
    private final ValidationDto validationDto;

    public PropertyValidatorBuilder(ValidatiorConfigurationBuilder validatiorBuilder, Field field, ValidationDto validationDto)
    {
        this.validatiorBuilder = validatiorBuilder;
        this.field = field;
        this.validationDto = validationDto;
    }

    @Override
    public PropertyValidator<T> notNull()
    {
        validationDto.addAction(field,new NotNullAction());
        return this;
    }

    @Override
    public PropertyValidator<T> maxSize(int length) {
        return null;
    }

    @Override
    public PropertyValidator<T> minSize(int length) {
        return null;
    }

    @Override
    public PropertyValidator<T> betweenSize(int min, int max) {
        return null;
    }

    @Override
    public PropertyValidator<T> notEmpty() {
        return null;
    }

    @Override
    public PropertyValidator<T> customValidation(ValidationAction<T> validationAction) {
        validationDto.addAction(field,validationAction);
        return this;
    }

    public ValidatiorConfigurationBuilder build()
    {
        return validatiorBuilder;
    }

}
