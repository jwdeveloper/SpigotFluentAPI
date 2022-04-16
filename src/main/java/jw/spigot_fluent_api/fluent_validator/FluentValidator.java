package jw.spigot_fluent_api.fluent_validator;

import jw.spigot_fluent_api.fluent_validator.api.ValidatorProfile;
import jw.spigot_fluent_api.fluent_validator.implementation.SimpleValidator;
import jw.spigot_fluent_api.fluent_validator.implementation.builder.ValidatiorConfigurationBuilder;
import jw.spigot_fluent_api.utilites.ActionResult;

public class FluentValidator
{
    private static final FluentValidator instance;
    private SimpleValidator simpleMapper;

    static {
        instance = new FluentValidator();
    }

    FluentValidator() {
        simpleMapper = new SimpleValidator();
    }


    public static <T> ActionResult validate(T input)
    {
       return instance.simpleMapper.validate(input);
    }

    public static void registerValidationProfile(Class<ValidatorProfile> validationProfileClass)
    {
        instance.simpleMapper.registerValidatorProfile(validationProfileClass);
    }

    public static <T> ValidatiorConfigurationBuilder create(Class<T> inputType)
    {
        return new ValidatiorConfigurationBuilder(inputType);
    }
}
