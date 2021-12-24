package jw.spigot_fluent_api.initialization;

import jw.spigot_fluent_api.data.DataManager;
import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.utilites.messages.LogUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTestsRunner;
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
        if(configuration().runWithIntegrationTests)
        {
            SpigotIntegrationTestsRunner.loadTests();
        }

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
        return FluentPlugin.getPlugin().getDataFolder().getAbsoluteFile().toString();
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

    public static void logException(String message, Exception e) {

        var cause = e.getCause() != null? e.getCause().getMessage():e.getMessage();

        logFormat(LogUtility.error(), message)
                .newLine()
                .color(ChatColor.DARK_RED)
                .inBrackets("Reason")
                .color(ChatColor.YELLOW)
                .space()
                .text(cause)
                .color(ChatColor.WHITE).send();
        new MessageBuilder().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-",100).send();
        e.printStackTrace();
        new MessageBuilder().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-",100).send();
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
