package jw.spigot_fluent_api.simple_commands.validators;

import jw.spigot_fluent_api.simple_commands.models.CommandArgumentValidator;

public class FloatValidator implements CommandArgumentValidator {
    @Override
    public boolean validate(String arg) {
        return arg.matches("[+-]?([0-9]*[.])?[0-9]+");
    }
}
