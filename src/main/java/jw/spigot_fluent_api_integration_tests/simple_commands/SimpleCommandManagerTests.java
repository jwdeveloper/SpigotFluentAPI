package jw.spigot_fluent_api_integration_tests.simple_commands;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.SimpleCommandManger;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTest;
import jw.spigot_fluent_api_integration_tests.SpigotTest;
import jw.spigot_fluent_api_integration_tests.spigotAssertions.SpigotAssertion;
import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginDisableEvent;

public class SimpleCommandManagerTests extends SpigotIntegrationTest {

    private String commandName = "testcommand";
    private SimpleCommand simpleCommand;

    @Override
    public void beforeTests()
    {
        simpleCommand = SimpleCommand
                .builder(commandName)
                .setDescription("Test command full desciption")
                .setShortDescription("Test command short description")
                .addAccess(CommandAccessType.COMMAND_SENDER)
                .build();
    }

    @SpigotTest
    public void shouldRegisterCommand() throws Exception {
        var result = SimpleCommandManger.register(simpleCommand);
        var allSpigotCommands = SimpleCommandManger.getAllServerCommands();
        SpigotAssertion.shouldBeTrue(result);
        SpigotAssertion.shouldContains(allSpigotCommands,commandName);
    }

    @SpigotTest
    public void shouldUnregisterCommand() throws Exception {
        var result = SimpleCommandManger.unregister(simpleCommand);
        var allSpigotCommands = SimpleCommandManger.getAllServerCommands();
        SpigotAssertion.shouldBeTrue(result);
        SpigotAssertion.shouldNotContains(allSpigotCommands,commandName);
    }


    @SpigotTest
    public void shouldUnRegisterAllSimpleCommandsOnServerDisable() throws Exception {

       var cmd1 = SimpleCommand.builder("cmd1").build();
       var cmd2 = SimpleCommand.builder("cmd2").build();

        var resultRegister1 = SimpleCommandManger.register(cmd1);
        var resultRegister2 = SimpleCommandManger.register(cmd2);

        Bukkit.getServer().getPluginManager().callEvent(new PluginDisableEvent(FluentPlugin.getPlugin()));

        var allCommands = SimpleCommandManger.getAllServerCommands();
        SpigotAssertion.shouldBeTrue(resultRegister1);
        SpigotAssertion.shouldBeTrue(resultRegister2);
        SpigotAssertion.shouldNotContains(allCommands,cmd1.getName());
        SpigotAssertion.shouldNotContains(allCommands,cmd2.getName());
    }
}
