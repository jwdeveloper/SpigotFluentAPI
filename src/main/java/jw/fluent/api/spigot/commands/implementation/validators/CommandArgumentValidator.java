package jw.fluent.api.spigot.commands.implementation.validators;

import jw.fluent.api.spigot.commands.api.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
