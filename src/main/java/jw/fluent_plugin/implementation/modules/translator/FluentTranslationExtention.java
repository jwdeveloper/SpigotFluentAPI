package jw.fluent_plugin.implementation.modules.translator;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_api.spigot.permissions.api.enums.Visibility;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_api.translator.implementation.SimpleLangLoader;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.commands.api.enums.ArgumentDisplay;
import jw.fluent_api.spigot.messages.FluentMessage;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.config.FluentConfig;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.ChatColor;

import java.io.File;

public class FluentTranslationExtention implements FluentApiExtention {
    private final String CONFIG_LANG_PATH = "plugin.language";
    private final String COMMAND_NAME = "lang";
    private FluentTranslatorImpl fluentTranslator;

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        fluentTranslator = new FluentTranslatorImpl();
        builder.container().register(FluentTranslator.class, LifeTime.SINGLETON,(x) -> fluentTranslator);

        var permission = createPermission(builder.permissions());
        builder.permissions().registerPermission(permission);
        builder.command().subCommandsConfig(subCommandConfig ->
        {
            subCommandConfig.addSubCommand(createCommand(permission,builder.command().getName() ,builder.config()));
        });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

        var basePath = FluentApi.path() + File.separator + "languages";
        var loader = new SimpleLangLoader(FluentApi.getPlugin());
        FileUtility.ensurePath(basePath);
        loader.generateFiles(basePath);
        var langName = getPluginLanguage(fluentAPI.getFluentConfig(),fluentAPI.getFluentLogger());
        var langDatas = loader.load(basePath, langName);
        fluentTranslator.setLanguages(langDatas, langName);
    }
    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }

    private String getPluginLanguage(FluentConfig configFile, FluentLogger logger)
    {
        if (configFile.config().isString(CONFIG_LANG_PATH)) {
            return configFile.config().getString(CONFIG_LANG_PATH);
        }
        logger.warning("Unable to load `"+CONFIG_LANG_PATH+"` from config");
        return "en";
    }

    private CommandBuilder createCommand(PermissionModel permission,String defaultPermissionName, FluentConfig configFile) {
        return FluentCommand.create(COMMAND_NAME)
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission.getName());
                    propertiesConfig.setDescription("Changes plugin languages, changes will be applied after server reload. Change be use both be player or console");
                    propertiesConfig.setUsageMessage("/"+defaultPermissionName+" "+COMMAND_NAME+" <language>");
                })
                .argumentsConfig(argumentConfig ->
                {
                    argumentConfig.addArgument("language", argumentBuilder ->
                    {
                        argumentBuilder.setTabComplete(FluentApi.translator().getLanguagesName());
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                        argumentBuilder.setDescription("select language");
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var languageName = commandEvent.getCommandArgs()[0];
                        if (!FluentApi.translator().langAvaliable(languageName)) {
                            FluentMessage.message()
                                    .warning()
                                    .text(" Language ", ChatColor.GRAY)
                                    .text(languageName, ChatColor.RED)
                                    .text(" not found ", ChatColor.GRAY)
                                    .send(commandEvent.getSender());
                            return;
                        }
                        configFile.config().set(CONFIG_LANG_PATH, languageName);
                        configFile.save();
                        FluentMessage.message()
                                .info()
                                .textSecondary(" Language has been changed to ")
                                .textPrimary(languageName)
                                .textSecondary(" use ")
                                .textPrimary("/reload")
                                .textSecondary(" to apply changes")
                                .send(commandEvent.getSender());
                    });
                });
    }

    private PermissionModel createPermission(FluentPermissionBuilder builder) {
        var permission = new PermissionModel();
        var pluginName = builder.getBasePermissionName();
        permission.setName(pluginName+".commands.lang");
        permission.setDescription("Allow player to change plugin language");
        permission.setVisibility(Visibility.Op);
        permission.getGroups().add(builder.defaultPermissionSections().commands().getParentGroup());
        return permission;
    }
}
