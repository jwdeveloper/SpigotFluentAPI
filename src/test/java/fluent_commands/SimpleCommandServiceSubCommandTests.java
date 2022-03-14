package fluent_commands;

import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.builders.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.services.SimpleCommandService;
import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleCommandServiceSubCommandTests {
    SimpleCommandService simpleCommandService;
    SimpleCommand rootCommand;
    SimpleCommand subCommand;
    SimpleCommand subSubCommand;
    @Before
    public void beforeTests() {
        simpleCommandService = new SimpleCommandService();
        rootCommand = FluentCommand
                .create("root")
                .nextStep()
                .withArgument("textArg")
                .build()
                .nextStep()
                .nextStep().buildAndRegister();
        subCommand = FluentCommand
                .create("subcommand")
                .nextStep()
                .withArgument("amount",ArgumentType.INT)
                .build()
                .withArgument("textArg")
                .build()
                .nextStep()
                .nextStep().build();
        subSubCommand = FluentCommand
                .create("subsubCommand")
                .nextStep()
                .withArgument("color",ArgumentType.COLOR)
                .build()
                .nextStep()
                .nextStep().build();

        rootCommand.addSubCommand(subCommand);
        subCommand.addSubCommand(subSubCommand);
    }

    @Test
    public void shouldInvokeRoot()
    {
        var args = new String[1];
        args[0] = "onlySomeText";
        var result = simpleCommandService.isSubcommandInvoked( rootCommand,args);
        Assert.assertEquals(result.getSimpleCommand(),rootCommand);
    }

    @Test
    public void shouldInvokeSubCommand()
    {
        String[] args = new String[4];
        args[0] = "onlySomeText";
        args[1] =  subCommand.getName();
        args[2] = "12";
        args[3] = "text";
        var result = simpleCommandService.isSubcommandInvoked(rootCommand, args);
        Assert.assertEquals(result.getSimpleCommand(),subCommand);
        Assert.assertEquals(result.getArgs().length,2);
        Assert.assertEquals(result.getArgs()[0],"12");
        Assert.assertEquals(result.getArgs()[1],"text");
    }

    @Test
    public void shouldInvokeSubSubCommand()
    {
        String[] args = new String[6];
        args[0] = "onlySomeText";
        args[1] =  subCommand.getName();
        args[2] = "12";
        args[3] = "text";
        args[4] = subSubCommand.getName();
        args[5] = ChatColor.GREEN.name();
        var result = simpleCommandService.isSubcommandInvoked(rootCommand, args);
        Assert.assertEquals(result.getSimpleCommand(),subSubCommand);
        Assert.assertEquals(result.getArgs().length,1);
        Assert.assertEquals(result.getArgs()[0],ChatColor.GREEN.name());
    }
}
