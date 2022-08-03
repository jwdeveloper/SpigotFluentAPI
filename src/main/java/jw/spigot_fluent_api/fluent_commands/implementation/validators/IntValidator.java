package jw.spigot_fluent_api.fluent_commands.implementation.validators;

import jw.spigot_fluent_api.fluent_commands.api.models.ValidationResult;

public class IntValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg) {
        if(arg.matches("^(0|-*[1-9]+[0-9]*)$"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be integer number");
    }
}
