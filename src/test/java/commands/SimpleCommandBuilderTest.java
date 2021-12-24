package commands;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
import jw.spigot_fluent_api.utilites.bukkit_mocks.PlayerMock;
import jw.spigot_fluent_api.utilites.bukkit_mocks.ServerMock;
import org.bukkit.Bukkit;
import org.junit.Test;

public class SimpleCommandBuilderTest {

    @Test
    public void shouldBuild() {

        Bukkit.setServer(new ServerMock());
        var commandSenderMock = new PlayerMock();
        var command = "player openItems";
        var wrongCommand = "player openx";

        commandSenderMock.setDisplayName("testPlayer");

        var root = getRootCommand("player");
        var child1= getChild("openItems");
        var child2= getChild("openMoney");
        root.addSubCommand(child1);
        root.addSubCommand(child2);

       // root.execute(commandSenderMock,)
    }


    public SimpleCommand getRootCommand(String name)
    {
        return SimpleCommand
                .builder(name)
                .setDescription("help all player")
                .setShortDescription("help player")
                .addPermission("player")
                .addOpPermission()
                .addArgument("subcomands")
                .setType(CommandArgumentType.SUBCOMMAND)
                .build()
                .onPlayerExecute(simpleCommandEvent ->
                {
                    simpleCommandEvent.setResult(true);
                })
               .register();
    }

    public SimpleCommand getChild(String name)
    {
        return SimpleCommand
                .builder(name)
                .setDescription("child command "+name)
                .register();
    }
}
