package jw.fluent.api.validator;

import jw.fluent.api.validator.api.ValidatorProfile;
import jw.fluent.api.validator.implementation.SimpleValidator;
import jw.fluent.api.validator.implementation.builder.ValidatiorConfigurationBuilder;
import jw.fluent.api.utilites.ActionResult;

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
