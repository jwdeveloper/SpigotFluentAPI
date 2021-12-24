package simple_commands;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
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
                .builder("root")
                .addArgument("s1")
                .setType(CommandArgumentType.SUBCOMMAND)
                .build()
                .build();
        subCommand = SimpleCommand
                .builder("subcommand")
                .addArgument("int")
                .setType(CommandArgumentType.INT)
                .build()
                .addArgument("s1")
                .setType(CommandArgumentType.SUBCOMMAND)
                .build()
                .build();
        subSubCommand = SimpleCommand
                .builder("subsubCommand")
                .addArgument("color")
                .setType(CommandArgumentType.COLOR)
                .build()
                .build();

        rootCommand.addSubCommand(subCommand);
        subCommand.addSubCommand(subSubCommand);
    }

    @Test
    public void shouldInvokeRoot()
    {
        var args = new String[0];
        var result = simpleCommandService.isSubcommandInvoked( rootCommand,args);
        Assert.assertEquals(result.getSimpleCommand(),rootCommand);
    }

    @Test
    public void shouldInvokeSubCommand()
    {
        String[] args = new String[2];
        args[0] =  subCommand.getName();
        args[1] = "12";
        var result = simpleCommandService.isSubcommandInvoked(rootCommand, args);
        Assert.assertEquals(result.getSimpleCommand(),subCommand);
        Assert.assertEquals(result.getArgs().length,1);
        Assert.assertEquals(result.getArgs()[0],"12");
    }

    @Test
    public void shouldInvokeSubSubCommand()
    {
        String[] args = new String[4];
        args[0] =  subCommand.getName();
        args[1] = "12";
        args[2] = subSubCommand.getName();
        args[3] = ChatColor.GREEN.name();
        var result = simpleCommandService.isSubcommandInvoked(rootCommand, args);
        Assert.assertEquals(result.getSimpleCommand(),subSubCommand);
        Assert.assertEquals(result.getArgs().length,1);
        Assert.assertEquals(result.getArgs()[0],ChatColor.GREEN.name());
    }
}
