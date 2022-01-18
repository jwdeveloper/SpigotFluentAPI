package jw.spigot_fluent_api.fluent_commands.validators;

import jw.spigot_fluent_api.fluent_commands.models.ValidationResult;

public class BoolValidator implements CommandArgumentValidator{
    @Override
    public ValidationResult validate(String arg)
    {
        arg = arg.toUpperCase();
        if(arg.matches("^([T][R][U][E]|[F][A][L][S][E])$"))
         return new ValidationResult(true,"");
        else
         return new ValidationResult(false,"should be True or False");
    }
}
