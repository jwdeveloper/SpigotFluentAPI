package jw.spigot_fluent_api.fluent_commands.validators;

import jw.spigot_fluent_api.fluent_commands.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
