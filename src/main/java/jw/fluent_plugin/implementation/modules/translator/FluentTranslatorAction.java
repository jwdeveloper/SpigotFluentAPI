package jw.fluent_plugin.implementation.modules.translator;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_api.translator.implementation.SimpleLangLoader;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.commands.api.enums.ArgumentDisplay;
import jw.fluent_api.spigot.messages.FluentMessage;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.config.ConfigFile;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
import jw.fluent_api.utilites.files.FileUtility;
import org.bukkit.ChatColor;

import java.io.File;

public class FluentTranslatorAction implements PluginAction {
    private PluginOptionsImpl options;
    private final String CONFIG_LANG_PATH = "plugin.language";

    public FluentTranslatorAction(PluginOptionsImpl options) {
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
        FluentAPI.lang().setLanguages(langDatas, langName);
        registerCommand(options);
    }

    public String getPluginLanguage(ConfigFile configFile) {
        if (configFile.config().isString(CONFIG_LANG_PATH)) {
            return configFile.config().getString(CONFIG_LANG_PATH);
        }
        OldLogger.warning("Unable to load `"+CONFIG_LANG_PATH+"` from config");
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
                        argumentBuilder.setTabComplete(FluentAPI.lang().getLanguagesName());
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                        argumentBuilder.setDescription("change the language of plugin");
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var languageName = commandEvent.getCommandArgs()[0];
                        if (!FluentAPI.lang().langAvaliable(languageName)) {
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
