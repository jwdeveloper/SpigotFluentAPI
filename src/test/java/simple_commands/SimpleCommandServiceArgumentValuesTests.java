package simple_commands;

import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.services.SimpleCommandService;
import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SimpleCommandServiceArgumentValuesTests
{
    SimpleCommandService simpleCommandService;

    @Before
    public void beforeTests() {
        simpleCommandService = new SimpleCommandService();
    }

    @Test
    public void shouldConvertInt()  {
        var arguments = new String[1];
        arguments[0] = "1";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(CommandArgumentType.INT));
        var result  = simpleCommandService.getArgumentValues(arguments,commandArguments);
        Assert.assertEquals(result[0],1);
    }

    @Test
    public void shouldConvertBool()  {
        var arguments = new String[1];
        arguments[0] = "True";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(CommandArgumentType.BOOL));
        var result  = simpleCommandService.getArgumentValues(arguments,commandArguments);
        Assert.assertEquals(result[0],true);
    }

    @Test
    public void shouldConvertFloat()  {
        var arguments = new String[1];
        arguments[0] = "1.2";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(CommandArgumentType.NUMBER));
        var result  = simpleCommandService.getArgumentValues(arguments,commandArguments);
        Assert.assertEquals(result[0],1.2f);
    }

    @Test
    public void shouldConvertColor()  {
        var arguments = new String[1];
        arguments[0] = "yellow";
        var commandArguments = new ArrayList<CommandArgument>();
        commandArguments.add(getCommandArgument(CommandArgumentType.COLOR));
        var result  = simpleCommandService.getArgumentValues(arguments,commandArguments);
        Assert.assertEquals(result[0], ChatColor.YELLOW);
    }

    public CommandArgument getCommandArgument(CommandArgumentType type)
    {
        CommandArgument commandArgument = new CommandArgument();
        commandArgument.setType(type);
        return commandArgument;
    }

}
