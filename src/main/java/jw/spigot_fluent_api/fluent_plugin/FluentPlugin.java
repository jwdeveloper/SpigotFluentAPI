package jw.spigot_fluent_api.fluent_plugin;

import jw.spigot_fluent_api.data.DataManager;
import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.utilites.messages.LogUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTestsRunner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public abstract class FluentPlugin extends JavaPlugin {

    private FluentPluginConfiguration configuration;
    private DataManager dataManager;
    private static FluentPlugin instance;
    private static JavaPlugin plugin;
    public FluentPlugin()
    {
        super();
    }

    protected FluentPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    protected abstract void OnConfiguration(FluentPluginConfiguration configuration);

    protected abstract void OnFluentPluginEnable();

    protected abstract void OnFluentPluginDisable();

    @Override
    public final void onEnable() {
        instance = this;
        plugin =this;
        configuration = new FluentPluginConfiguration(this);
        OnConfiguration(configuration);
        dataManager = new DataManager(configuration.pluginPath);
        if (configuration.dependencyInjectionEnable) {
            InjectionManager.instance();
            InjectionManager.registerAllFromPackage(getClass().getPackage());
            dataManager.addSaveableObject(InjectionManager.getObjectByType(Saveable.class));
        }
        if (configuration.dataManagerConsumerConfiguration != null) {
            configuration.dataManagerConsumerConfiguration.accept(dataManager);
        }
        dataManager.load();
        if(configuration().runWithIntegrationTests)
        {
            SpigotIntegrationTestsRunner.loadTests();
        }
        if(configuration().displayHelloMessage)
        {
            var message = new MessageBuilder()
                    .color(ChatColor.DARK_GREEN)
                    .bar("=",40)
                    .color(ChatColor.WHITE)
                    .newLine()
                    .text(getPlugin().getName())
                    .space()
                    .color(ChatColor.GREEN)
                    .inBrackets("enabled")
                    .color(ChatColor.WHITE)
                    .newLine()
                    .withEndfix("Version ","->")
                    .space()
                    .color(ChatColor.GREEN)
                    .text(getPlugin().getDescription().getVersion())
                    .color(ChatColor.WHITE)
                    .newLine()
                    .color(ChatColor.DARK_GREEN)
                    .bar("=",40)
                    .color(ChatColor.WHITE)
                    .toString();
            Bukkit.getConsoleSender().sendMessage(message);
        }
        OnFluentPluginEnable();
    }

    @Override
    public final void onDisable() {
        OnFluentPluginDisable();
        dataManager.save();
    }

    public FluentPluginConfiguration configuration() {
        return configuration;
    }

    public static void setPlugin(JavaPlugin javaPlugin)
    {
        plugin = javaPlugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static String getPath() {
        return FluentPlugin.getPlugin().getDataFolder().getAbsoluteFile().toString();
    }

    public static DataManager getDataManager() {
        return instance.dataManager;
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
