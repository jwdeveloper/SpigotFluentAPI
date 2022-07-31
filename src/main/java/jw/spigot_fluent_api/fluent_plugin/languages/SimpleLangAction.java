package jw.spigot_fluent_api.fluent_plugin.languages;

import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentDisplay;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_plugin.languages.api.models.LangOptions;
import jw.spigot_fluent_api.fluent_plugin.languages.implementation.SimpleLangLoader;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.function.Consumer;

public class SimpleLangAction implements PluginPipeline {
    private Consumer<LangOptions> consumer;

    public SimpleLangAction(Consumer<LangOptions> consumer) {
        this.consumer = consumer;
        if (this.consumer == null) {
            this.consumer = new Consumer<LangOptions>() {
                @Override
                public void accept(LangOptions langOptions) {

                }
            };
        }
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var dto = new LangOptions();
        consumer.accept(dto);

        var basePath = FluentPlugin.getPath() + File.separator + "languages";
        var loader = new SimpleLangLoader();
        FileUtility.ensurePath(basePath);
        loader.generateFiles(basePath);
        var lang = getConfigLanguage(options.getConfigFile());
        var pluginLangs = loader.load(basePath,lang);
        Lang.setLanguages(pluginLangs);
        registerCommand(options);
    }

    public String getConfigLanguage(ConfigFile configFile)
    {
        if(configFile.config().isString("plugin.language"))
        {
            return configFile.config().getString("plugin.language");
        }
        FluentLogger.warning("Unable to load `plugin.language` from config");
        return "en";
    }

    public void registerCommand(PipelineOptions options) throws Exception {
        if (options.getDefaultCommand() == null) {
            throw new Exception("default command is required for Lang Action");
        }
        if (options.getDefaultPermissions() == null) {
            throw new Exception("default permission is required for Lang Action");
        }

        var permission = options.getDefaultPermissions().getName();
        options.getDefaultCommand().getBuilder().subCommandsConfig(subCommandConfig ->
        {
           subCommandConfig.addSubCommand(langCommand(permission));
        });
    }

    private CommandBuilder langCommand(String permission) {
        return FluentCommand.create("lang")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission);
                    propertiesConfig.addPermissions(permission+".language");
                })
                .argumentsConfig(argumentConfig ->
                {
                    argumentConfig.addArgument("nationality", argumentBuilder ->
                    {
                        argumentBuilder.setTabComplete(Lang.getLanguagesName());
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var languageName = commandEvent.getCommandArgs()[0];
                        var result = Lang.setLanguage(languageName);
                        if(!result)
                        {
                            FluentMessage.message()
                                    .color(ChatColor.RED)
                                    .inBrackets("info")
                                    .text(" Language ", ChatColor.GRAY)
                                    .text(languageName, ChatColor.RED)
                                    .text(" not found ", ChatColor.GRAY)
                                    .send(commandEvent.getSender());
                            return;
                        }

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
