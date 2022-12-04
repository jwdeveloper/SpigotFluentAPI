package jw.fluent.api.spigot.commands.implementation.validators;

import jw.fluent.api.spigot.commands.api.models.ValidationResult;

public class NumberValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg) {
        if(arg.matches("^[1-9]\\d*(\\.\\d+)?$"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be number");
    }
}
