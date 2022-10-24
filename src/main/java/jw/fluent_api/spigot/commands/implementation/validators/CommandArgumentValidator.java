package jw.fluent_api.spigot.commands.implementation.validators;

import jw.fluent_api.spigot.commands.api.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
