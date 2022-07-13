package jw.spigot_fluent_api.fluent_commands.implementation.validators;

import jw.spigot_fluent_api.fluent_commands.api.models.ValidationResult;

public class NumberValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg) {
        if(arg.matches("^[1-9]\\d*(\\.\\d+)?$"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be number");
    }
}
