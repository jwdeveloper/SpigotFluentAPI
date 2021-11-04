package jw.spigot_fluent_api.initialization;
import jw.spigot_fluent_api.data_models.DataManager;
import jw.spigot_fluent_api.data_models.Saveable;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.utilites.messages.LogUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FluentPlugin extends JavaPlugin
{
    private FluentPluginConfiguration fluentPluginConfiguration;
    private static FluentPlugin instnace;
    private DataManager dataManager;

    protected abstract void OnConfiguration(FluentPluginConfiguration configuration);
    protected abstract void OnFluentPluginEnable();
    protected abstract void OnFluentPluginDisable();

    @Override
    public final void onEnable()
    {
        instnace = this;
        fluentPluginConfiguration = new FluentPluginConfiguration(this);
        OnConfiguration(fluentPluginConfiguration);
        dataManager = new DataManager(fluentPluginConfiguration.pluginPath);
        if(fluentPluginConfiguration.dependencyInjectionEnable)
        {
            InjectionManager.Instance();
            InjectionManager.registerAllFromPackage(getClass().getPackage());
            dataManager.addSaveableObject(InjectionManager.getObjectByType(Saveable.class));
        }
        if(fluentPluginConfiguration.dataManagerConsumerConfiguration != null)
        {
            fluentPluginConfiguration.dataManagerConsumerConfiguration.accept(dataManager);
        }
        dataManager.load();
        OnFluentPluginEnable();
    }
    @Override
    public final void onDisable()
    {
        OnFluentPluginDisable();
        dataManager.save();
    }

    public FluentPluginConfiguration configuration()
    {
        return fluentPluginConfiguration;
    }

    public static JavaPlugin getPlugin()
    {
        return instnace;
    }

    public static String getPath()
    {
         return instnace.fluentPluginConfiguration.pluginPath;
    }

    public static DataManager getDataManager()
    {
        return instnace.dataManager;
    }

    public static MessageBuilder log(String message)
    {
        return LogUtility.info().text(message);
    }

    public static void logInfo(String message)
    {
        LogUtility.info().text(message).send();
    }

    public static void logError(String message)
    {
        LogUtility.error().text(message).send();
    }

    public static void logSuccess(String message)
    {
        LogUtility.success().text(message).send();
    }
}
