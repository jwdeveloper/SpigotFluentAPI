package simple_commands;

import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandArgumentValidator;
import jw.spigot_fluent_api.simple_commands.services.SimpleCommandService;
import jw.spigot_fluent_api.simple_commands.validators.BoolValidator;
import jw.spigot_fluent_api.simple_commands.validators.NumberValidator;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SimpleCommandServiceValidationTests extends SpigotIntegrationTest
{
    SimpleCommandService simpleCommandService;

    @Before
    public void beforeTests() {
        simpleCommandService = new SimpleCommandService();
    }

    @Test
    public void shouldReturnFalse()  {
        var arguments = new String[5];
        var commandArguments = new ArrayList<CommandArgument>();
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnTrue() {
        var arguments = new String[0];
        var commandArguments = new ArrayList<CommandArgument>();
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(result);
    }
    @Test
    public void shouldValidateNotNumber() {
        var arguments = new String[1];
        arguments[0] = "12.2asda";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldValidateNotNumberWithComma() {
        var arguments = new String[1];
        arguments[0] = "12,2";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldValidateNumber() {
        var arguments = new String[1];
        arguments[0] = "12.2";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new NumberValidator()));
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldValidateBool()  {
        var arguments = new String[1];
        arguments[0] = "TRUE";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new BoolValidator()));
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldNotValidateBool()  {
        var arguments = new String[1];
        arguments[0] = "randomtext";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(new BoolValidator()));
        var result  = simpleCommandService.validateArguments(arguments,commandArguments);
        Assert.assertFalse(result);
    }

    private CommandArgument getCommandArgument(CommandArgumentValidator validator)
    {
        CommandArgument commandArgument = new CommandArgument();
        commandArgument.addValidator(validator);
        return commandArgument;
    }
}
