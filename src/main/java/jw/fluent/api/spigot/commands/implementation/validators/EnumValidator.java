package jw.fluent.api.spigot.commands.implementation.validators;

import jw.fluent.api.spigot.commands.api.models.ValidationResult;


public class EnumValidator<T extends Enum<T>> implements CommandArgumentValidator
{

    private final Class<T> enumClass;

    public EnumValidator(Class<T> enumClass)
    {
        this.enumClass =enumClass;
    }

    @Override
    public ValidationResult validate(String arg)
    {
        try {
            Enum.valueOf(enumClass, arg.toUpperCase());
            return new ValidationResult(true,"");
        } catch (Exception e) {
            return new ValidationResult(false,"should be " +enumClass.getSimpleName()+" name");
        }
    }
}
