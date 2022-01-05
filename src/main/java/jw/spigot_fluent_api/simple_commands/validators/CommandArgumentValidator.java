package jw.spigot_fluent_api.simple_commands.validators;

import jw.spigot_fluent_api.simple_commands.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
