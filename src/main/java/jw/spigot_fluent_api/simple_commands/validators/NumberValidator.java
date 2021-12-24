package jw.spigot_fluent_api.simple_commands.validators;

import jw.spigot_fluent_api.simple_commands.models.CommandArgumentValidator;

public class NumberValidator implements CommandArgumentValidator {
    @Override
    public boolean validate(String arg) {
        return arg.matches("^[1-9]\\d*(\\.\\d+)?$");
    }
}
