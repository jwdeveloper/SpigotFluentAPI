package jw.fluent_plugin.implementation;

import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public abstract class FluentPlugin extends JavaPlugin implements FluentApiExtention {
    private boolean isInitialized;

    private FluentApi fluentApi;

    public abstract void onConfiguration(FluentApiBuilder builder);

    public abstract void onFluentApiEnable(FluentApi fluentAPI);

    public abstract void onFluentApiDisabled(FluentApi fluentAPI);

    @Override
    public final void onEnable() {
        FluentApi.plugin = this;
        debugCommand();
        try {
            var builder = new FluentApiBuilderImpl(this);
            builder.useExtention(this);
            fluentApi = builder.build();
            fluentApi.enable();
            isInitialized = true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Fluent API can not be loaded since " , e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public final void onDisable() {
        if (!isInitialized)
            return;
        fluentApi.disable();
    }

    public void debugCommand() {
        FluentCommand.create_OLDWAY("disable")
                .setDescription("disable all plugin without restarting server")
                .setUsageMessage("Can be use only with Console")
                .nextStep()
                .nextStep()
                .onConsoleExecute(consoleCommandEvent ->
                {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Plugins disabled");
                    Bukkit.getPluginManager().disablePlugins();
                })
                .nextStep()
                .buildAndRegister();
    }

}
