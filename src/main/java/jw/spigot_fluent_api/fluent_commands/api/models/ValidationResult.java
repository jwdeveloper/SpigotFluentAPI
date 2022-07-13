package jw.spigot_fluent_api.fluent_commands.api.models;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ValidationResult
{
    private  boolean success;
    private String message;

    public ValidationResult(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public ValidationResult(boolean success)
    {
        this.success = success;
    }

    public static ValidationResult success()
    {
        return new ValidationResult(true);
    }

    public static ValidationResult error(String message)
    {
        return new ValidationResult(false,message);
    }
}

