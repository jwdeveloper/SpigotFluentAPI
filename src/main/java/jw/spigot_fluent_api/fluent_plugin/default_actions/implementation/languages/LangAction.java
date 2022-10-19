package jw.spigot_fluent_api.fluent_plugin.default_actions.implementation.languages;

import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentDisplay;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_plugin.default_actions.implementation.languages.implementation.SimpleLangLoader;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import org.bukkit.ChatColor;

import java.io.File;

public class LangAction implements PluginPipeline {
    private PluginOptionsImpl options;
    private final String CONFIG_LANG_PATH = "plugin.language";

    public LangAction(PluginOptionsImpl options) {
        this.options = options;
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var basePath = FluentPlugin.getPath() + File.separator + "languages";
        var loader = new SimpleLangLoader();
        FileUtility.ensurePath(basePath);
        loader.generateFiles(basePath);
        var langName = getPluginLanguage(options.getConfigFile());
        var langDatas = loader.load(basePath, langName);
        Lang.setLanguages(langDatas, langName);
        registerCommand(options);
    }

    public String getPluginLanguage(ConfigFile configFile) {
        if (configFile.config().isString(CONFIG_LANG_PATH)) {
            return configFile.config().getString(CONFIG_LANG_PATH);
        }
        FluentLogger.warning("Unable to load `"+CONFIG_LANG_PATH+"` from config");
        return "en";
    }

    public void registerCommand(PipelineOptions options) throws Exception {
        if (!options.getDefaultCommand().hasDefaultCommand()) {
            throw new Exception("default command is required for Lang Action");
        }
        var permission = options.getPluginOptions().getPermissionName();
        options.getDefaultCommand().getBuilder().subCommandsConfig(subCommandConfig ->
        {
            subCommandConfig.addSubCommand(langCommand(permission, options.getConfigFile()));
        });
    }

    private CommandBuilder langCommand(String permission, ConfigFile configFile) {
        return FluentCommand.create("lang")
                .propertiesConfig(propertiesConfig ->
                {

                    propertiesConfig.addPermissions(permission + "commands.language");
                    propertiesConfig.setShortDescription("set the plugin name");
                })
                .argumentsConfig(argumentConfig ->
                {
                    argumentConfig.addArgument("nationality", argumentBuilder ->
                    {
                        argumentBuilder.setTabComplete(Lang.getLanguagesName());
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                        argumentBuilder.setDescription("change the language of plugin");
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var languageName = commandEvent.getCommandArgs()[0];
                        if (!Lang.langAvaliable(languageName)) {
                            FluentMessage.message()
                                    .color(ChatColor.RED)
                                    .inBrackets("info")
                                    .text(" Language ", ChatColor.GRAY)
                                    .text(languageName, ChatColor.RED)
                                    .text(" not found ", ChatColor.GRAY)
                                    .send(commandEvent.getSender());
                            return;
                        }
                        configFile.config().set(CONFIG_LANG_PATH, languageName);
                        configFile.save();
                        FluentMessage.message()
                                .color(ChatColor.AQUA)
                                .inBrackets("info")
                                .text(" Language has been changed to ", ChatColor.GRAY)
                                .text(languageName, ChatColor.AQUA)
                                .text(" use ", ChatColor.GRAY)
                                .text("/reload", ChatColor.AQUA).color(ChatColor.GRAY)
                                .text(" to apply changes").send(commandEvent.getSender());

                    });
                });
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }

}