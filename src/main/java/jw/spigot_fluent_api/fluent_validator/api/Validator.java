package jw.spigot_fluent_api.fluent_validator.api;

import jw.spigot_fluent_api.utilites.ActionResult;

public interface Validator {

    <T> ActionResult validate(T input);

    void registerValidatorProfile(Class<ValidatorProfile> validationProfile);
}
