package commands;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import org.bukkit.Bukkit;
import org.junit.Ignore;
import org.junit.Test;

public class SimpleCommandBuilderTest {

    @Ignore
    @Test
    public void shouldBuild() {
       // Bukkit.setServer(new ServerMock());
       /// var commandSenderMock = new PlayerMock();
        var command = "player openItems";
        var wrongCommand = "player openx";

      //  commandSenderMock.setDisplayName("testPlayer");

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
                .newCommand(name)
                .setDescription("help all player")
                .setShortDescription("help player")
                .addPermissions("player")
                .addOpPermission()
                .newArgument("subcomands")
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
                .newCommand(name)
                .setDescription("child command "+name)
                .register();
    }
}
