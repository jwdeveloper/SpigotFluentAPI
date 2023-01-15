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
import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import jw.fluent.api.spigot.commands.implementation.SimpleCommandManger;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginDisableEvent;

public class SimpleCommandManagerTests  {

    private String commandName = "testcommand";
    private SimpleCommand simpleCommand;



    public void beforeAll() {
        simpleCommand = FluentCommand
                .create_OLDWAY(commandName)
                .setDescription("Test command full desciption")
                .setShortDescription("Test command short description")
                .nextStep()
                .nextStep()
                .nextStep()
                .build();
    }

  //  @Test
    public void shouldRegisterCommand() throws Exception {
        var result = SimpleCommandManger.register(simpleCommand);
        var allSpigotCommands = SimpleCommandManger.getAllServerCommandsName();
       // SpigotAssertion.shouldBeTrue(result);
     //   SpigotAssertion.shouldContains(allSpigotCommands, commandName);
    }

  //  @Test
    public void shouldUnregisterCommand() throws Exception {
        var result = SimpleCommandManger.unregister(simpleCommand);
        var allSpigotCommands = SimpleCommandManger.getAllServerCommands();
        var cmd = allSpigotCommands.stream()
                .filter(c -> c.getName().equalsIgnoreCase(simpleCommand.getName()))
                .findFirst();

    //    SpigotAssertion.shouldBeTrue(result);
    //    SpigotAssertion.shouldBeTrue(cmd.isPresent());
      //  SpigotAssertion.shouldBeFalse(cmd.get().isRegistered());
    }


    //@Test
    public void shouldUnRegisterAllSimpleCommandsOnServerDisable() throws Exception {

        var command1 = FluentCommand.create_OLDWAY("cmd1").nextStep().nextStep().nextStep().build();
        var command2 = FluentCommand.create_OLDWAY("cmd2").nextStep().nextStep().nextStep().build();

        var resultRegister1 = SimpleCommandManger.register(command1);
        var resultRegister2 = SimpleCommandManger.register(command2);

        Bukkit.getServer().getPluginManager().callEvent(new PluginDisableEvent(FluentApi.plugin()));

        var allSpigotCommands = SimpleCommandManger.getAllServerCommands();
        var cmd1 = allSpigotCommands.stream()
                .filter(c -> c.getName()
                        .equalsIgnoreCase(simpleCommand.getName()))
                .findFirst();

        var cmd2 = allSpigotCommands.stream()
                .filter(c -> c.getName()
                        .equalsIgnoreCase(simpleCommand.getName()))
                .findFirst();
      //  SpigotAssertion.shouldBeTrue(cmd1.isPresent());
      //  SpigotAssertion.shouldBeTrue(cmd2.isPresent());

      //  SpigotAssertion.shouldBeFalse(cmd1.get().isRegistered());
      //  SpigotAssertion.shouldBeFalse(cmd2.get().isRegistered());

     //   SpigotAssertion.shouldBeTrue(resultRegister1);
     //   SpigotAssertion.shouldBeTrue(resultRegister2);
    }
}
