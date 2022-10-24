package jw.fluent_plugin.implementation.modules.updater;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.function.Consumer;

public interface FluentUpdater
{
    public void checkUpdate(Consumer<Boolean> consumer);

    public void checkUpdate(ConsoleCommandSender commandSender) ;

    public void downloadUpdate(CommandSender commandSender);

}
