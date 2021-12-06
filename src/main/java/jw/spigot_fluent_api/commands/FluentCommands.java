package jw.spigot_fluent_api.commands;

import jw.spigot_fluent_api.commands.events.FluentCommandConsoleEvent;
import jw.spigot_fluent_api.commands.events.FluentCommandPlayerEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FluentCommands {
    public static FluentCommand onPlayerCommand(String name, FluentCommandPlayerEvent fluentCommandEvent) {
        return new FluentCommand(name) {
            @Override
            protected void onPlayerInvoke(Player playerSender, String[] args) {
                fluentCommandEvent.execute(playerSender, args);
            }

            @Override
            public void onInitialize() {

            }
        };
    }

    public static FluentCommand onCommand(String name, FluentCommandConsoleEvent fluentCommandEvent) {
        return new FluentCommand(name) {

            @Override
            protected void onInvoke(CommandSender serverSender, String[] args) {
                fluentCommandEvent.execute(serverSender, args);
            }

            @Override
            public void onInitialize() {

            }
        };
    }

    public static FluentCommand onConsoleCommand(String name, FluentCommandConsoleEvent fluentCommandEvent) {
        return new FluentCommand(name) {

            @Override
            protected void onConsoleInvoke(ConsoleCommandSender serverSender, String[] args) {
                fluentCommandEvent.execute(serverSender, args);
            }

            @Override
            public void onInitialize() {

            }
        };
    }
}
