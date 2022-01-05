package jw.spigot_fluent_api.simple_commands.validators;

import jw.spigot_fluent_api.simple_commands.models.ValidationResult;

public class FloatValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg) {
        if(arg.matches("[+-]?([0-9]*[.])?[0-9]+"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be number");
    }
}
