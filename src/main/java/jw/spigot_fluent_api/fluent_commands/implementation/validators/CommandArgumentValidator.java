package jw.spigot_fluent_api.fluent_commands.implementation.validators;

import jw.spigot_fluent_api.fluent_commands.api.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
