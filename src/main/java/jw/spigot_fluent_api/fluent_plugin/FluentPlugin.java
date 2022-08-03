package jw.spigot_fluent_api.fluent_plugin;

import com.google.common.collect.ImmutableList;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.config.PluginConfigFactory;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.CommandOptions;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_plugin.managers.TypeManager;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.PluginPipeline;
import jw.spigot_fluent_api.utilites.java.ClassTypeUtility;
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

            var defaultCommandDto = configuration.getDefaultCommand();
            var defaultPermissonDto = configuration.getDefaultPermissionsDto();
            var cmd = FluentCommand.create(defaultCommandDto.getName());

            var options = new PipelineOptions(this,
                    new CommandOptions(defaultCommandDto.getName(),cmd),
                    defaultPermissonDto,
                    config);
            for (var action : pluginPipeline) {
                try {
                    action.pluginEnable(options);
                } catch (Exception e) {
                    isInitialized = false;
                    FluentLogger.error("Plugin can not be loaded since ", e);
                    return;
                }
            }
            defaultCommandDto.getConsumer().accept(cmd);
            cmd.build();
            OnFluentPluginEnable();

            isInitialized = true;
        } catch (Exception e) {
            FluentLogger.error("Unable to load plugin ", e);
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
