package unit.fluent_commands;

import jw.fluent_api.minecraft.commands.api.models.CommandArgument;
import jw.fluent_api.minecraft.commands.implementation.services.CommandServiceImpl;
import jw.fluent_api.minecraft.commands.implementation.validators.CommandArgumentValidator;
import jw.fluent_api.minecraft.commands.implementation.validators.BoolValidator;
import jw.fluent_api.minecraft.commands.implementation.validators.NumberValidator;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SimpleCommandServiceValidationTests extends SpigotIntegrationTest
{
    CommandServiceImpl simpleCommandService;

    @Before
    public void beforeTests() {
        simpleCommandService = new CommandServiceImpl();
    }

    @Test
    public void shouldReturnTrueWhenHasNoArguments()  {
        var arguments = new String[5];
        var commandArguments = new ArrayList<CommandArgument>();
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(isSuccess.isSuccess());
    }

    @Test
    public void shouldReturnFalseWhenHasArguments()  {
        var arguments = new String[3];
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(isSuccess.isSuccess());
    }

    @Test
    public void shouldReturnTrue() {
        var arguments = new String[0];
        var commandArguments = new ArrayList<CommandArgument>();
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(isSuccess.isSuccess());
    }
    @Test
    public void shouldValidateNotNumber() {
        var arguments = new String[1];
        arguments[0] = "12.2asda";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(isSuccess.isSuccess());
    }

    @Test
    public void shouldValidateNotNumberWithComma() {
        var arguments = new String[1];
        arguments[0] = "12,2";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(isSuccess.isSuccess());
    }

    @Test
    public void shouldValidateNumber() {
        var arguments = new String[1];
        arguments[0] = "12.2";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(isSuccess.isSuccess());
    }

    @Test
    public void shouldValidateBool()  {
        var arguments = new String[1];
        arguments[0] = "TRUE";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new BoolValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(isSuccess.isSuccess());
    }

    @Test
    public void shouldIgnoreSubCommandArgument()  {
        var arguments = new String[2];
        arguments[0] = "true";
        arguments[1] = "false";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new BoolValidator()));
        commandArguments.add(getCommandArgument(new BoolValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(isSuccess.isSuccess());
    }

    @Test
    public void shouldNotValidateBool()  {
        var arguments = new String[1];
        arguments[0] = "randomtext";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new BoolValidator()));
        var isSuccess  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(isSuccess.isSuccess());
    }

    private CommandArgument getCommandArgument(CommandArgumentValidator validator)
    {
        CommandArgument commandArgument = new CommandArgument();
        commandArgument.addValidator(validator);
        return commandArgument;
    }
}
