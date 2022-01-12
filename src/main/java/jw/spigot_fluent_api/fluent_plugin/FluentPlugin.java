package jw.spigot_fluent_api.fluent_plugin;

import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFile;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFileImpl;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;
import jw.spigot_fluent_api.utilites.messages.LogUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
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
        instance = this;
        plugin = this;
        configuration = new FluentPluginConfiguration();
    }

    @Override
    public final void onEnable() {
        OnConfiguration(configuration,new ConfigFileImpl(getConfig()));
        pluginPipeline = configuration.getConfigurationActions();
        for (var action : pluginPipeline) {
            try {
                action.pluginEnable(this);
            } catch (Exception e) {
                isInitialized = false;
                FluentPlugin.logException("Plugin can not be loaded since ", e);
                return;
            }
        }
        OnFluentPluginEnable();
        isInitialized = true;
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
                FluentPlugin.logException("Error while plugin disable ", e);
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

    public static File getPluginFile()
    {
        try
        {
            var plugin = getPlugin();
            var fileField = JavaPlugin.class.getDeclaredField("file");
            fileField.setAccessible(true);
            return (File)fileField.get(plugin);
        }
        catch (Exception e)
        {
            FluentPlugin.logException("Can not laod plugin file",e);
        }
        return null;
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

        logFormat(LogUtility.exception(), ChatColor.RED + message + ChatColor.RESET)
                .newLine()
                .color(ChatColor.DARK_RED)
                .inBrackets("Reason")
                .color(ChatColor.YELLOW)
                .space()
                .text(cause)
                .color(ChatColor.RESET)
                .newLine()
                .color(ChatColor.DARK_RED)
                .inBrackets("Exception type")
                .color(ChatColor.YELLOW)
                .space()
                .text(e.getClass().getSimpleName())
                .color(ChatColor.RESET)
                .send();
        new MessageBuilder().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-", 100).send();
        var stackTrace = new MessageBuilder();
        stackTrace.color(ChatColor.WHITE);
        for (var trace : e.getStackTrace()) {
            int offset = 6;
            offset = offset - (trace.getLineNumber()+"").length();
            stackTrace
                    .newLine()
                    .color(ChatColor.WHITE)
                    .text("at line", ChatColor.WHITE)
                    .space(2)
                    .text(trace.getLineNumber(), ChatColor.AQUA)
                    .space(offset)
                    .text("in", ChatColor.WHITE)
                    .space()
                    .text(trace.getClassName(), ChatColor.GRAY)
                    .text("."+trace.getMethodName()+"()", ChatColor.AQUA)
                    .space()

                    .color(ChatColor.RESET);
        }
        stackTrace.send();
        new MessageBuilder().color(ChatColor.BOLD).color(ChatColor.DARK_RED).bar("-", 100).send();
    }

    public static void logSuccess(String message) {
        logFormat(LogUtility.success(), message).send();
    }

    private static MessageBuilder logFormat(MessageBuilder messageBuilder, String message) {
        return new MessageBuilder()
                .color(ChatColor.WHITE)
                .inBrackets(getPlugin().getName())
                .space()
                .text(messageBuilder.toString())
                .color(ChatColor.RESET)
                .text(message);
    }


}
