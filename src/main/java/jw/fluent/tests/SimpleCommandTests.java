/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.fluent.tests;

import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.api.spigot.commands.api.enums.AccessType;
import jw.fluent.api.spigot.commands.api.enums.ArgumentType;
import jw.fluent.api.spigot.commands.api.models.CommandArgument;
import jw.fluent.api.spigot.commands.api.models.CommandTarget;
import jw.fluent.api.spigot.commands.api.models.ValidationResult;
import jw.fluent.api.spigot.commands.api.services.CommandService;
import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import jw.fluent.api.spigot.commands.implementation.events.CommandEvent;
import jw.fluent.api.spigot.commands.implementation.services.CommandServiceImpl;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SimpleCommandTests {
    SimpleCommand simpleCommand;
    Consumer<CommandEvent> onExecute;
    CommandService commandServiceMock;




    public void beforeAll() {
        simpleCommand = FluentCommand
                .create_OLDWAY("example-command")
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



    public void afterAll() {
        simpleCommand.unregister();
    }

  //  @Test
    public void shouldInvokeCommand() throws Exception {
        //init
        AtomicBoolean eventInvoked = new AtomicBoolean(false);
        AtomicBoolean parameterValue1 = new AtomicBoolean(false);
        AtomicInteger parameterValue2 = new AtomicInteger(0);
        onExecute = (c) ->
        {
            eventInvoked.set(true);
            parameterValue1.set(c.getArgumentValue(0));
            parameterValue2.set(c.getArgumentValue(1));
        };
        var command = simpleCommand.getName();
        var argument1 = "True";
        var argument2 = "20";
        var fullCommand = command + " " + argument1 + " " + argument2;

        //act
        var result = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), fullCommand);

        //assert
     //   SpigotAssertion.shouldBeTrue(result);
      //  SpigotAssertion.shouldBeEqual(parameterValue1.get(), true);
      //  SpigotAssertion.shouldBeEqual(parameterValue2.get(), 20);
      //  SpigotAssertion.shouldBeTrue(eventInvoked.get());
    }

   // @Test
    public void shouldNotInvokeCommand() throws Exception {
        AtomicBoolean eventInvoked = new AtomicBoolean(false);
        onExecute = (c) ->
        {
            eventInvoked.set(true);
        };
        var command = simpleCommand.getName() + "something123";
        var result = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
      //  SpigotAssertion.shouldBeFalse(result);
      //  SpigotAssertion.shouldBeFalse(eventInvoked.get());
    }

   // @Test
    public void shouldNotInvokeCommandLackOfPermissions() throws Exception {
        AtomicBoolean eventInvoked = new AtomicBoolean(false);
        onExecute = (c) ->
        {
            eventInvoked.set(true);
        };
        var command = simpleCommand.getName();
       // var player = getExamplePlayer();
      //  player.setOp(false);
     //   var result = Bukkit.dispatchCommand(player, command);
      //  player.setOp(true);
      //  SpigotAssertion.shouldBeFalse(result);
     //   SpigotAssertion.shouldBeFalse(eventInvoked.get());

    }

    public CommandService getCommandServiceMock() {
        return new CommandServiceImpl() {

            @Override
            public Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments) {
                var start = System.nanoTime();
                var res = super.getArgumentValues(args, commandArguments);
                ;
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentLogger.LOGGER.success("Perparing arguments " + " in time: " + inMS);
                return res;
            }

            @Override
            public boolean hasSenderAccess(CommandSender commandSender, List<AccessType> commandAccessType) {
                var start = System.nanoTime();
                var res = super.hasSenderAccess(commandSender, commandAccessType);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentLogger.LOGGER.success("Checking access for " + commandSender.getName() + " in time: " + inMS);
                return res;
            }

            @Override
            public boolean hasSenderAccess(CommandSender commandSender, AccessType commandAccessType) {
                return true;
            }

            @Override
            public CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args) {
                var start = System.nanoTime();
                var res = super.isSubcommandInvoked(command, args);
                ;
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentLogger.LOGGER.success("Looking for sub Command " + command.getName() + " in time: " + inMS);
                return res;
            }


            @Override
            public ValidationResult hasSenderPermissions(CommandSender commandSender, List<String> permissions) {
                var start = System.nanoTime();
                var res = super.hasSenderPermissions(commandSender, permissions);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentLogger.LOGGER.success("Checking permissions for " + commandSender.getName() + " in time: " + inMS + " result " + res.isSuccess());
                return res;
            }

            @Override
            public ValidationResult validateArguments(String[] args, List<CommandArgument> commandArguments) {
                var start = System.nanoTime();
                var res = super.validateArguments(args, commandArguments);
                var finish = System.nanoTime();
                var result = finish - start;
                var inMS = result / Math.pow(10, 6);
                FluentLogger.LOGGER.success("Validating arguments" + " in time: " + inMS);
                return res;
            }
        };
    }
}
