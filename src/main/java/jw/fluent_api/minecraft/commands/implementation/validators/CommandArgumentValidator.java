package jw.fluent_api.minecraft.commands.implementation.validators;

import jw.fluent_api.minecraft.commands.api.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
