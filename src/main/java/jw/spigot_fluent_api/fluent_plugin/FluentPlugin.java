package jw.spigot_fluent_api.fluent_plugin;

import jw.spigot_fluent_api.data.DataContext;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.actions.ConfigAction;
import jw.spigot_fluent_api.utilites.messages.LogUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.List;

public abstract class FluentPlugin extends JavaPlugin {

    private FluentPluginConfiguration configuration;
    private static FluentPlugin instance;
    private static JavaPlugin plugin;
    private static boolean isInitialized;
    private List<ConfigAction> configActions;

    public FluentPlugin() {
        super();
    }

    protected FluentPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    protected abstract void OnConfiguration(PluginConfiguration configuration);

    protected abstract void OnFluentPluginEnable();

    protected abstract void OnFluentPluginDisable();

    @Override
    public void onLoad()
    {
        instance = this;
        plugin = this;
        configuration = new FluentPluginConfiguration();
    }

    @Override
    public final void onEnable() {
        OnConfiguration(configuration);
        configActions = configuration.getConfigurationActions();
        for(var configAction:configActions)
        {
            try
            {
                configAction.pluginEnable(this);
            }
            catch (Exception e)
            {
                isInitialized = false;
                FluentPlugin.logException("Plugin can not be loaded since ",e);
                return;
            }
        }
        OnFluentPluginEnable();
        isInitialized =true;
    }

    @Override
    public final void onDisable()
    {
        if(!isInitialized)
            return;

        OnFluentPluginDisable();
        for(var configAction:configActions)
        {
            try
            {
                configAction.pluginDisable(this);
            }
            catch (Exception e)
            {
                isInitialized = false;
                FluentPlugin.logException("Error while plugin disable ",e);
                return;
            }
        }
    }

    public FluentPluginConfiguration configuration() {
        return configuration;
    }

    public static void setPlugin(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static String getPath() {
        return FluentPlugin.getPlugin().getDataFolder().getAbsoluteFile().toString();
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

        var cause = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();

        logFormat(LogUtility.error(), message)
                .newLine()
                .color(ChatColor.DARK_RED)
                .inBrackets("Reason")
                .color(ChatColor.YELLOW)
                .space()
                .text(cause)
                .color(ChatColor.WHITE).send();
        new MessageBuilder().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-", 100).send();
        e.printStackTrace();
        new MessageBuilder().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-", 100).send();
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
