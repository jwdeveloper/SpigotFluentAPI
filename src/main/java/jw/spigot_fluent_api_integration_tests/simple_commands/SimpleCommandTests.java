package jw.spigot_fluent_api_integration_tests.simple_commands;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;
import jw.spigot_fluent_api.simple_commands.models.CommandTarget;
import jw.spigot_fluent_api.simple_commands.services.CommandService;
import jw.spigot_fluent_api.simple_commands.services.SimpleCommandService;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTest;
import jw.spigot_fluent_api_integration_tests.SpigotTest;
import jw.spigot_fluent_api_integration_tests.spigotAssertions.SpigotAssertion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SimpleCommandTests extends SpigotIntegrationTest
{
    SimpleCommand simpleCommand;
    Consumer<SimpleCommandEvent> onExecute;
    CommandService commandServiceMock;
    @Override
    public void beforeTests()
    {
        simpleCommand = SimpleCommand
                .builder("example-command")
                .setDescription("full description")
                .setShortDescription("short description")
                .onExecute(simpleCommandEvent ->
                {
                    onExecute.accept(simpleCommandEvent);
                })
                .addArgument("testBool")
                .setColor(ChatColor.AQUA)
                .setType(CommandArgumentType.BOOL)
                .build()
                .addArgument("testInt")
                .setColor(ChatColor.GREEN)
                .setType(CommandArgumentType.INT)
                .build()
                .register();
        commandServiceMock = getCommandServiceMock();
        simpleCommand.setImplementation(commandServiceMock);
    }

    @Override
    public void afterTests()
    {
        simpleCommand.unregister();
    }

    @SpigotTest
    public void shouldInvokeCommand() throws Exception {
        //init
        AtomicBoolean eventInvoked = new AtomicBoolean(false);
        AtomicBoolean parameterValue1 = new AtomicBoolean(false);
        AtomicInteger parameterValue2 = new AtomicInteger(0);
        onExecute = (c)->
        {
            eventInvoked.set(true);
            parameterValue1.set(c.getArgumentValue(0));
            parameterValue2.set(c.getArgumentValue(1));
        };
        var command  = simpleCommand.getName();
        var argument1 = "True";
        var argument2 = "20";
        var fullCommand = command+" "+argument1+" "+argument2;

        //act
        var result = Bukkit.dispatchCommand(Bukkit.getConsoleSender(),fullCommand);

        //assert
        SpigotAssertion.shouldBeTrue(result);
        SpigotAssertion.shouldBeEqual(parameterValue1.get(),true);
        SpigotAssertion.shouldBeEqual(parameterValue2.get(),20);
        SpigotAssertion.shouldBeTrue(eventInvoked.get());
    }
    @SpigotTest
    public void shouldNotInvokeCommand() throws Exception {
        AtomicBoolean eventInvoked = new AtomicBoolean(false);
        onExecute = (c)->
        {
            eventInvoked.set(true);
        };
        var command  = simpleCommand.getName()+"something123";
        var result = Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
        SpigotAssertion.shouldBeFalse(result);
        SpigotAssertion.shouldBeFalse(eventInvoked.get());
    }

    public CommandService getCommandServiceMock()
    {
        return new SimpleCommandService() {
            @Override
            public String connectArgs(String[] stringArray) {
                return null;
            }

            @Override
            public Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments) {
                return super.getArgumentValues(args,commandArguments);
            }

            @Override
            public boolean hasSenderAccess(CommandSender commandSender, List<CommandAccessType> commandAccessType)
            {
                FluentPlugin.logInfo("Checking access for "+commandSender.getName());
                return super.hasSenderAccess(commandSender,commandAccessType);
            }

            @Override
            public CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args) {
                FluentPlugin.logInfo("Looking for sub Command "+command.getName());
                return super.isSubcommandInvoked(command,args);
            }

            @Override
            public boolean hasSenderAccess(CommandSender commandSender, CommandAccessType commandAccessType) {
                return true;
            }

            @Override
            public boolean hasSenderPermissions(CommandSender commandSender, List<String> permissons) {
                FluentPlugin.logInfo("Checking permissions for "+commandSender.getName());
                return super.hasSenderPermissions(commandSender,permissons);
            }

            @Override
            public boolean validateArguments(String[] args, List<CommandArgument> commandArguments) {
                FluentPlugin.logInfo("Validating arguments");
                return super.validateArguments(args,commandArguments);
            }

            @Override
            public List<Consumer<SimpleCommandEvent>> getEventsToInvoke(CommandSender sender, HashMap<CommandAccessType, Consumer<SimpleCommandEvent>> events)
            {
                FluentPlugin.logInfo("Preparing events to execute");
                return super.getEventsToInvoke(sender,events);
            }
        };
    }
}
