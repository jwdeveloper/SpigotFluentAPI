package jw.fluent_api.validator.api;

import jw.fluent_api.utilites.ActionResult;

public interface Validator {

    <T> ActionResult validate(T input);

    void registerValidatorProfile(Class<ValidatorProfile> validationProfile);
}
