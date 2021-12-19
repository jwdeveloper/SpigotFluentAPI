package jw.spigot_fluent_api.initialization;

import jw.spigot_fluent_api.data.DataManager;
import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.utilites.messages.LogUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FluentPlugin extends JavaPlugin {
    private FluentPluginConfiguration fluentPluginConfiguration;
    private static FluentPlugin instnace;
    private DataManager dataManager;

    protected abstract void OnConfiguration(FluentPluginConfiguration configuration);

    protected abstract void OnFluentPluginEnable();

    protected abstract void OnFluentPluginDisable();

    @Override
    public final void onEnable() {
        instnace = this;
        fluentPluginConfiguration = new FluentPluginConfiguration(this);
        OnConfiguration(fluentPluginConfiguration);
        dataManager = new DataManager(fluentPluginConfiguration.pluginPath);
        if (fluentPluginConfiguration.dependencyInjectionEnable) {
            InjectionManager.Instance();
            InjectionManager.registerAllFromPackage(getClass().getPackage());
            dataManager.addSaveableObject(InjectionManager.getObjectByType(Saveable.class));
        }
        if (fluentPluginConfiguration.dataManagerConsumerConfiguration != null) {
            fluentPluginConfiguration.dataManagerConsumerConfiguration.accept(dataManager);
        }
        dataManager.load();
        OnFluentPluginEnable();
    }

    @Override
    public final void onDisable() {
        OnFluentPluginDisable();
        dataManager.save();
    }

    public FluentPluginConfiguration configuration() {
        return fluentPluginConfiguration;
    }

    public static JavaPlugin getPlugin() {
        return instnace;
    }

    public static String getPath() {
        return instnace.fluentPluginConfiguration.pluginPath;
    }

    public static DataManager getDataManager() {
        return instnace.dataManager;
    }

    public static MessageBuilder log(String message) {
        return logFormat(LogUtility.info(), message);
    }

    public static void logInfo(String message) {
        logFormat(LogUtility.info(), message).send();
    }

    public static void logError(String message) {

        logFormat(LogUtility.error(), message).send();
    }

    public static void logSuccess(String message) {
        logFormat(LogUtility.success(), message).send();
    }

    private static MessageBuilder logFormat(MessageBuilder messageBuilder, String message) {
        return messageBuilder.color(ChatColor.YELLOW)
                .inBrackets(getPlugin().getName())
                .space()
                .color(ChatColor.RESET)
                .text(message);
    }


}
