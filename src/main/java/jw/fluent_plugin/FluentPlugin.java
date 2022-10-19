package jw.fluent_plugin;

import com.google.common.collect.ImmutableList;
import jw.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.fluent_plugin.starup_actions.data.CommandOptions;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_api.minecraft.commands.FluentCommand;
import jw.fluent_api.minecraft.logger.FluentLogger;
import jw.fluent_plugin.config.PluginConfigFactory;
import jw.fluent_plugin.managers.TypeManager;
import jw.fluent_plugin.starup_actions.api.PluginConfiguration;
import jw.fluent_plugin.config.ConfigFile;
import jw.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.fluent_api.utilites.java.ClassTypeUtility;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class FluentPlugin extends JavaPlugin {

    private TypeManager typeManager;
    private FluentPluginConfiguration configuration;
    private static JavaPlugin plugin;
    private static boolean isInitialized;
    private List<PluginPipeline> pluginPipeline;

    public FluentPlugin() {
        super();
    }

    protected FluentPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    protected abstract void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile);

    protected abstract void OnFluentPluginEnable();

    protected abstract void OnFluentPluginDisable();

    @Override
    public void onLoad() {
        plugin = this;
        typeManager = new TypeManager(ClassTypeUtility.findClassesInPlugin(this));
        configuration = new FluentPluginConfiguration();
    }

    @Override
    public final void onEnable() {
        try {
            var config =  new PluginConfigFactory().create(this);
            OnConfiguration(configuration, config);

            pluginPipeline = configuration.getConfigurationActions();

            var pluginOptions = configuration.getPluginOptions();
            var commandOptions = createDefaultCommand(pluginOptions);

            var options = new PipelineOptions(
                    this,
                    commandOptions,
                    pluginOptions,
                    config);
            for (var action : pluginPipeline) {
                try {
                    action.pluginEnable(options);
                } catch (Exception e) {
                    isInitialized = false;
                    throw e;
                }
            }
            commandOptions.getBuilder().build();
            OnFluentPluginEnable();

            isInitialized = true;
        } catch (Exception e) {
            FluentLogger.error("Plugin can not be loaded since ", e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public final void onDisable() {
        if (!isInitialized)
            return;
        OnFluentPluginDisable();
        for (var action : pluginPipeline) {
            try {
                action.pluginDisable(this);
            } catch (Exception e) {
                isInitialized = false;
                FluentLogger.error("Error while plugin disable ", e);
                return;
            }
        }
    }

    private CommandOptions createDefaultCommand(PluginOptionsImpl pluginOptions)
    {
        var cmdName=pluginOptions.getCommandName();
        var cmd = FluentCommand.create(cmdName);
        return new CommandOptions(cmdName,cmd);
    }

    public FluentPluginConfiguration configuration() {
        return configuration;
    }

    public static void setPlugin(JavaPlugin plugin) {
        FluentPlugin.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        return FluentPlugin.plugin;
    }

    public static File getPluginFile() {
        try {
            var plugin = getPlugin();
            var fileField = JavaPlugin.class.getDeclaredField("file");
            fileField.setAccessible(true);
            return (File) fileField.get(plugin);
        } catch (Exception e) {
            FluentLogger.error("Can not load plugin file", e);
        }
        return null;
    }

    public static void addPermissions(List<Permission> permissions)
    {
        addPermissions(permissions.toArray(new Permission[permissions.size()]));
    }

    public static void addPermissions(Permission... permissions)
    {
        try {
            var description = getPlugin().getDescription();
            var permissionsField = PluginDescriptionFile.class.getDeclaredField("permissions");
            permissionsField.setAccessible(true);

            var newPermissions = new ArrayList<Permission>();
            var currentPermission = description.getPermissions();
            for(var p : currentPermission)
            {
                newPermissions.add(p);
            }
            for(var p : permissions)
            {
                newPermissions.add(p);
            }

            var result  = ImmutableList.copyOf(newPermissions);
            permissionsField.set(description, result);
        } catch (Exception e) {
            FluentLogger.error("Can not add permission", e);
        }
    }

    public TypeManager getTypeManager() {
        return typeManager;
    }

    public ClassLoader getPluginClassLoader()
    {
        return getClassLoader();
    }

    public static String getPath() {

        if(FluentPlugin.getPlugin()== null)
        {
            return "D:\\tmp";
        }

        return FluentPlugin.getPlugin().getDataFolder().getAbsoluteFile().toString();
    }
}
