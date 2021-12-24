package jw.spigot_fluent_api.simple_commands.validators;

import jw.spigot_fluent_api.simple_commands.models.CommandArgumentValidator;

public class BoolValidator implements CommandArgumentValidator{
    @Override
    public boolean validate(String arg) {
        return arg.matches("^([Tt][Rr][Uu][Ee]|[Ff][Aa][Ll][Ss][Ee])$");
    }
}
