package jw.fluent.api.validator.api;

import jw.fluent.api.utilites.ActionResult;

public interface Validator {

    <T> ActionResult validate(T input);

    void registerValidatorProfile(Class<ValidatorProfile> validationProfile);
}
