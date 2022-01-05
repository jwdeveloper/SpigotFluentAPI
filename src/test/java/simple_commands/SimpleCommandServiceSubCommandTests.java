package simple_commands;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.ArgumentType;
import jw.spigot_fluent_api.simple_commands.services.SimpleCommandService;
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
        rootCommand = SimpleCommand
                .newCommand("root")
                .newArgument("textArg")
                .build()
                .build();
        subCommand = SimpleCommand
                .newCommand("subcommand")
                .newArgument("amount",ArgumentType.INT)
                .build()
                .newArgument("textArg")
                .build()
                .build();
        subSubCommand = SimpleCommand
                .newCommand("subsubCommand")
                .newArgument("color",ArgumentType.COLOR)
                .build()
                .build();

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
