package commands;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import org.bukkit.Material;
import org.junit.Test;

public class SimpleCommandBuilderTest {

    @Test
    public void shouldBuild() {
        var command = SimpleCommand
                .build("player")
                .setDescription("help all player")
                .setShortDescription("help player")
                .addArgument("item")
                .setType(Material.class)
                .setDescription("works always")
                .build()
                .addPermission("player")
                .addOpPermission()
                .onPlayerExecute(simpleCommandEvent ->
                {

                    simpleCommandEvent.setResult(true);
                })
                .register();

        command.addSubCommand(command);
        command.setActive(false);
    }
}
