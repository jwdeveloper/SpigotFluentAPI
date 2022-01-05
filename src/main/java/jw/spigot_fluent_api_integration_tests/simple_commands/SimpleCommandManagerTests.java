package jw.spigot_fluent_api_integration_tests.simple_commands;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.SimpleCommandManger;
import jw.spigot_fluent_api.simple_commands.enums.AccessType;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTest;
import jw.spigot_fluent_api_integration_tests.SpigotTest;
import jw.spigot_fluent_api_integration_tests.spigotAssertions.SpigotAssertion;
import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginDisableEvent;

public class SimpleCommandManagerTests extends SpigotIntegrationTest {

    private String commandName = "testcommand";
    private SimpleCommand simpleCommand;

    @Override
    public void beforeTests() {
        simpleCommand = SimpleCommand
                .newCommand(commandName)
                .setDescription("Test command full desciption")
                .setShortDescription("Test command short description")
                .build();
    }

    @SpigotTest
    public void shouldRegisterCommand() throws Exception {
        var result = SimpleCommandManger.register(simpleCommand);
        var allSpigotCommands = SimpleCommandManger.getAllServerCommandsName();
        SpigotAssertion.shouldBeTrue(result);
        SpigotAssertion.shouldContains(allSpigotCommands, commandName);
    }

    @SpigotTest
    public void shouldUnregisterCommand() throws Exception {
        var result = SimpleCommandManger.unregister(simpleCommand);
        var allSpigotCommands = SimpleCommandManger.getAllServerCommands();
        var cmd = allSpigotCommands.stream()
                .filter(c -> c.getName().equalsIgnoreCase(simpleCommand.getName()))
                .findFirst();

        SpigotAssertion.shouldBeTrue(result);
        SpigotAssertion.shouldBeTrue(cmd.isPresent());
        SpigotAssertion.shouldBeFalse(cmd.get().isRegistered());
    }


    @SpigotTest
    public void shouldUnRegisterAllSimpleCommandsOnServerDisable() throws Exception {

        var command1 = SimpleCommand.newCommand("cmd1").build();
        var command2 = SimpleCommand.newCommand("cmd2").build();

        var resultRegister1 = SimpleCommandManger.register(command1);
        var resultRegister2 = SimpleCommandManger.register(command2);

        Bukkit.getServer().getPluginManager().callEvent(new PluginDisableEvent(FluentPlugin.getPlugin()));

        var allSpigotCommands = SimpleCommandManger.getAllServerCommands();
        var cmd1 = allSpigotCommands.stream()
                .filter(c -> c.getName()
                .equalsIgnoreCase(simpleCommand.getName()))
                .findFirst();

        var cmd2 = allSpigotCommands.stream()
                .filter(c -> c.getName()
                        .equalsIgnoreCase(simpleCommand.getName()))
                .findFirst();
        SpigotAssertion.shouldBeTrue(cmd1.isPresent());
        SpigotAssertion.shouldBeTrue(cmd2.isPresent());

        SpigotAssertion.shouldBeFalse(cmd1.get().isRegistered());
        SpigotAssertion.shouldBeFalse(cmd2.get().isRegistered());

        SpigotAssertion.shouldBeTrue(resultRegister1);
        SpigotAssertion.shouldBeTrue(resultRegister2);
    }
}
