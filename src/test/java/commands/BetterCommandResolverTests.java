package commands;

import jw.spigot_fluent_api.simple_commands.utilites.SimpleCommandResolver;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.util.List;


public class BetterCommandResolverTests
{
    private SimpleCommandResolver resolver;
    private final String commandCorrectName = "test {amount} {playerName}";
    private final String commandInCorrectName = "test {amount} playerName}";

    @Test
    public void should_getAllParametersFromName() throws Exception
    {
        List<String> result = null;
        result =  SimpleCommandResolver.getAllParametersFromName(commandCorrectName);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.get(0),"amount");
        Assertions.assertEquals(result.get(1),"playerName");
    }

    @Test(expected = Exception.class)
    public void should_throw_ex_getAllParametersFromName() throws Exception {
       SimpleCommandResolver.getAllParametersFromName(commandInCorrectName);
    }

    @Test
    public void should_resolved_parameters()  throws Exception
    {
        var commandMethod = goodMethod();
        var name = "test {amount} {playerName}";
        List<CommandArgument> result = null;
        result =  SimpleCommandResolver.resolveParameters(commandMethod,name);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(),2);
    }

    public Method goodMethod()
    {
        return SimpleCommandResolver.getCommandMethods(BetterTestCommand.class).get(0);
    }

    public Method wrongMethod()
    {
        return SimpleCommandResolver.getCommandMethods(BetterTestCommand.class).get(1);
    }


}
