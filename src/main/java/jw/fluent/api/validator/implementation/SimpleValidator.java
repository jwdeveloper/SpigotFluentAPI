package jw.fluent.api.validator.implementation;

import jw.fluent.api.validator.api.ValidatorProfile;
import jw.fluent.api.validator.api.Validator;
import jw.fluent.api.utilites.ActionResult;

import java.util.HashMap;

public class SimpleValidator implements Validator {

    private HashMap<Class<?>, ValidationDto> validationProfiles = new HashMap<>();
    private static final String VALIDATOR_PROFILE_CLASS_NAME = ValidatorProfile.class.getTypeName();

    @Override
    public <T> ActionResult validate(T input) {
        ActionResult result = new ActionResult("", true);
        var validatorConfig = validationProfiles.get(input.getClass());
        try {
            var validationFields = validatorConfig.getActionList();
            for (var validationField : validationFields.keySet()) {

                var actions = validationFields.get(validationField);
                for (var action : actions) {
                    result = action.check(input, validationField);
                    if (!result.isSuccess())
                        break;
                }
            }
        } catch (Exception e) {
            return new ActionResult(e,false, "Validation error");
        }
        return result;
    }

    @Override
    public void registerValidatorProfile(Class<ValidatorProfile> validationProfile) {

    }

  /*  @SneakyThrows
    @Override
    public void registerValidatorProfile(Class<ValidatorProfile> validationProfileClass) {
        ParameterizedType validator = null;
        for (var _interface : validationProfileClass.getGenericInterfaces()) {
            var name = _interface.getTypeName();
            if (name.contains(VALIDATOR_PROFILE_CLASS_NAME)) {
                validator = (ParameterizedType) _interface;
                break;
            }
        }
        if (validator == null)
            return;

        var inputClass = (Class<?>) validator.getActualTypeArguments()[0];


        var registrationInfo = new RegistrationInfo(null,
                validationProfileClass,
                null,
                LifeTime.SINGLETON,
                RegistrationType.OnlyImpl);
       /* FluentInjection.getContainer().register(registrationInfo);

        var validationProfile = FluentInjection.findInjection(validationProfileClass);
        var builder = new ValidatiorConfigurationBuilder(inputClass);
        validationProfile.configure(builder);
        var result = builder.build();
        validationProfiles.put(inputClass, result);
    }*/
}
