package jw.fluent.plugin.implementation;

import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FluentPlugin extends JavaPlugin implements FluentApiExtension {

    public abstract void onConfiguration(FluentApiSpigotBuilder builder);

    public abstract void onFluentApiEnable(FluentApiSpigot fluentAPI);

    public abstract void onFluentApiDisabled(FluentApiSpigot fluentAPI);

    @Override
    public final void onEnable() {
        var apiBuilder = FluentApiBuilder.create(this);
        apiBuilder.useExtension(this);
        var api = apiBuilder.build();
        if(api == null)
        {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        debugCommand();
    }

    @Override
    public final void onDisable() {
    }


    public void debugCommand() {
        FluentCommand.create("disable")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("Command only for plugin development purpose. Can be only trigger by Console. disables all plugins");
                    propertiesConfig.setUsageMessage("/disable");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onConsoleExecute(consoleCommandEvent ->
                    {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Plugins disabled");
                        Bukkit.getPluginManager().disablePlugins();
                    });
                })
                .build();
    }

}
