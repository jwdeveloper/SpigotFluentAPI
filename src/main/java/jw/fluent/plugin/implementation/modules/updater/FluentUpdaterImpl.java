package jw.fluent.plugin.implementation.modules.updater;

import jw.fluent.api.updater.implementation.SimpleUpdater;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.function.Consumer;

public class FluentUpdaterImpl implements FluentUpdater {
    private final SimpleUpdater simpleUpdater;

    public FluentUpdaterImpl(SimpleUpdater simpleUpdater) {
        this.simpleUpdater = simpleUpdater;
    }

    @Override
    public void checkUpdate(Consumer<Boolean> consumer) {
        simpleUpdater.checkUpdate(consumer);
    }

    @Override
    public void checkUpdate(ConsoleCommandSender commandSender) {
        simpleUpdater.checkUpdate(commandSender);
    }

    @Override
    public void downloadUpdate(CommandSender commandSender) {
        simpleUpdater.downloadUpdate(commandSender);
    }
}
