package jw.spigot_fluent_api_integration_tests.simple_commands;

import jw.spigot_fluent_api.fluent_commands.builders.FluentCommand;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.enums.AccessType;
import jw.spigot_fluent_api.fluent_commands.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.events.CommandEvent;
import jw.spigot_fluent_api.fluent_commands.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.models.CommandTarget;
import jw.spigot_fluent_api.fluent_commands.models.ValidationResult;
import jw.spigot_fluent_api.fluent_commands.services.CommandService;
import jw.spigot_fluent_api.fluent_commands.services.SimpleCommandService;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTest;
import jw.spigot_fluent_api_integration_tests.SpigotTest;
import jw.spigot_fluent_api_integration_tests.spigotAssertions.SpigotAssertion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SimpleCommandTests extends SpigotIntegrationTest
{
    SimpleCommand simpleCommand;
    Consumer<CommandEvent> onExecute;
    CommandService commandServiceMock;
    @Override
    public void beforeTests()
    {
        simpleCommand = FluentCommand
                .create("example-command")
                .setDescription("full description")
                .setShortDescription("short description")
                .addPermissions("test-permissions")
                .nextStep()
                .withArgument("testBool")
                .setColor(ChatColor.AQUA)
                .setType(ArgumentType.BOOL)
                .build()
                .withArgument("testInt")
                .setColor(ChatColor.GREEN)
                .setType(ArgumentType.INT)
                .build()
                .nextStep()
                .onExecute(simpleCommandEvent ->
                {
                    onExecute.accept(simpleCommandEvent);
                })
                .nextStep()
                .buildAndRegister();
        commandServiceMock = getCommandServiceMock();
        simpleCommand.setCommandService(commandServiceMock);
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

    @SpigotTest
    public void shouldNotInvokeCommandLackOfPermissions() throws Exception {
        AtomicBoolean eventInvoked = new AtomicBoolean(false);
        onExecute = (c)->
        {
            eventInvoked.set(true);
        };
        var command  = simpleCommand.getName();
        var player = getExamplePlayer();
        player.setOp(false);
        var result = Bukkit.dispatchCommand(player,command);
        player.setOp(true);
        SpigotAssertion.shouldBeFalse(result);
        SpigotAssertion.shouldBeFalse(eventInvoked.get());
     
    }

    public CommandService getCommandServiceMock()
    {
        return new SimpleCommandService() {

            @Override
            public Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments)
            {
                var start = System.nanoTime();
                var res = super.getArgumentValues(args,commandArguments);;
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentPlugin.logInfo("Perparing arguments "+" in time: "+inMS);
                return res;
            }

            @Override
            public boolean hasSenderAccess(CommandSender commandSender, List<AccessType> commandAccessType)
            {
                var start = System.nanoTime();
                var res = super.hasSenderAccess(commandSender,commandAccessType);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentPlugin.logInfo("Checking access for "+commandSender.getName()+" in time: "+inMS);
                return res;
            }
            @Override
            public boolean hasSenderAccess(CommandSender commandSender, AccessType commandAccessType) {
                return true;
            }

            @Override
            public CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args) {
                var start = System.nanoTime();
                var res = super.isSubcommandInvoked(command,args);;
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentPlugin.logInfo("Looking for sub Command "+command.getName()+" in time: "+inMS);
                return res;
            }


            @Override
            public ValidationResult hasSenderPermissions(CommandSender commandSender, List<String> permissions) {
                var start = System.nanoTime();
                var res = super.hasSenderPermissions(commandSender, permissions);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentPlugin.logInfo("Checking permissions for "+commandSender.getName()+" in time: "+inMS+ " result "+ res.result());
                return res;
            }

            @Override
            public ValidationResult validateArguments(String[] args, List<CommandArgument> commandArguments) {
                var start = System.nanoTime();
                var res = super.validateArguments(args,commandArguments);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentPlugin.logInfo("Validating arguments"+" in time: "+inMS);
                return res;
            }
        };
    }
}
