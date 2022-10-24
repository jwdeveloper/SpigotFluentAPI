package jw.fluent_plugin.implementation.modules.updater;

import jw.fluent_api.updater.implementation.SimpleUpdater;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.function.Consumer;

public class FluentUpdaterImpl implements FluentUpdater
{
    private SimpleUpdater simpleUpdater;

    public FluentUpdaterImpl(SimpleUpdater simpleUpdater)
    {
        this.simpleUpdater = simpleUpdater;
    }

    @Override
    public void checkUpdate(Consumer<Boolean> consumer) {

    }

    @Override
    public void checkUpdate(ConsoleCommandSender commandSender) {

    }

    @Override
    public void downloadUpdate(CommandSender commandSender) {

    }
}
