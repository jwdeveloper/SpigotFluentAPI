package jw.fluent.api.spigot.commands.implementation.validators;

import jw.fluent.api.spigot.commands.api.models.ValidationResult;

public class IntValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg) {
        if(arg.matches("^(0|-*[1-9]+[0-9]*)$"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be integer number");
    }
}
