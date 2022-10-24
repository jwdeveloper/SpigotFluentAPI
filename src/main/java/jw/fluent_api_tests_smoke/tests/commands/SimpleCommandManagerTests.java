package jw.fluent_api_tests_smoke.tests.commands;

import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_api.spigot.commands.implementation.SimpleCommand;
import jw.fluent_api.spigot.commands.implementation.SimpleCommandManger;
import jw.fluent_api_tests_smoke.api.SpigotIntegrationTest;
import jw.fluent_api_tests_smoke.api.SpigotTest;
import jw.fluent_api_tests_smoke.api.spigotAssertions.SpigotAssertion;
import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginDisableEvent;

public class SimpleCommandManagerTests extends SpigotIntegrationTest {

    private String commandName = "testcommand";
    private SimpleCommand simpleCommand;

    @Override
    public void beforeTests() {
        simpleCommand = FluentCommand
                .create_OLDWAY(commandName)
                .setDescription("Test command full desciption")
                .setShortDescription("Test command short description")
                .nextStep()
                .nextStep()
                .nextStep()
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

        var command1 = FluentCommand.create_OLDWAY("cmd1").nextStep().nextStep().nextStep().build();
        var command2 = FluentCommand.create_OLDWAY("cmd2").nextStep().nextStep().nextStep().build();

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
