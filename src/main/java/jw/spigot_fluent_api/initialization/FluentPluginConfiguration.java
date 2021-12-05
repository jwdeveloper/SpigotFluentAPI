package jw.spigot_fluent_api.initialization;
import jw.spigot_fluent_api.commands.FluentCommands;
import jw.spigot_fluent_api.data.DataManager;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import org.bukkit.Bukkit;

import java.io.File;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class FluentPluginConfiguration
{
    boolean autoSaveLoadFiles;
    boolean dependencyInjectionEnable;
    String pluginPath;
    Consumer<DataManager> dataManagerConsumerConfiguration;
    private final FluentPlugin fluentPlugin;

    public FluentPluginConfiguration(FluentPlugin fluentPlugin) {
        this.fluentPlugin = fluentPlugin;
        this.pluginPath = getDefaultPath();
        this.autoSaveLoadFiles = true;
        this.dependencyInjectionEnable = false;
    }

    public FluentPluginConfiguration useDependencyInjection()
    {
        dependencyInjectionEnable = true;
        return this;
    }
    public FluentPluginConfiguration useDependencyInjection(Consumer<InjectionManager> configuration)
    {
        dependencyInjectionEnable = true;
        configuration.accept(InjectionManager.Instance());
        return this;
    }
    public FluentPluginConfiguration configureDataManager(Consumer<DataManager> configuration)
    {
        dataManagerConsumerConfiguration = configuration;
        return this;
    }

    public FluentPluginConfiguration configureDataBase(Consumer<DatabaseConfiguration> databaseConfiguration)
    {
        return this;
    }

    public FluentPluginConfiguration runInDebbug()
    {
        FluentCommands.onConsoleCommand("disable",(player, args) ->
        {
            FluentPlugin.logInfo("Plugins disabled");
            Bukkit.getPluginManager().disablePlugins();
        });
        return this;
    }

    public FluentPluginConfiguration usePath(String path) {
        this.pluginPath = path;
        return this;
    }

    private String getDefaultPath() {
        return new StringBuilder()
                .append(Paths.get("").toAbsolutePath())
                .append(File.separator)
                .append("plugins")
                .append(File.separator)
                .append(fluentPlugin.getName())
                .toString();
    }

}
