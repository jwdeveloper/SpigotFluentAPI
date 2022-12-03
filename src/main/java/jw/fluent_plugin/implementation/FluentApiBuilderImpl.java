package jw.fluent_plugin.implementation;

import jw.fluent_api.files.api.SimpleFilesBuilder;
import jw.fluent_api.files.implementation.SimpleFileBuilderImpl;
import jw.fluent_api.logger.api.SimpleLoggerBuilder;
import jw.fluent_api.logger.implementation.SimpleLoggerBuilderImpl;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.commands.implementation.builder.CommandBuilderImpl;
import jw.fluent_api.utilites.ClassTypesManager;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.java.ClassTypeUtility;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiContainerBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.config.FluentConfig;
import jw.fluent_plugin.implementation.config.FluentConfigImpl;
import jw.fluent_plugin.implementation.config.PluginConfigFactory;
import jw.fluent_plugin.implementation.modules.commands.CommandExtention;
import jw.fluent_plugin.implementation.modules.commands.FluentApiCommandBuilder;
import jw.fluent_plugin.implementation.modules.commands.FluentApiDefaultCommandBuilder;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionExtention;
import jw.fluent_plugin.implementation.modules.files.FluentFiles;
import jw.fluent_plugin.implementation.modules.files.FluentFilesExtention;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import jw.fluent_plugin.implementation.modules.logger.FluentLoggerExtention;
import jw.fluent_plugin.implementation.modules.mapper.FluentMapper;
import jw.fluent_plugin.implementation.modules.mapper.FluentMapperExtention;
import jw.fluent_plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent_plugin.implementation.modules.mediator.FluentMediatorExtention;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import jw.fluent_plugin.implementation.modules.permissions.implementation.FluentPermissionBuilderImpl;
import jw.fluent_plugin.implementation.modules.permissions.implementation.FluentPermissionExtention;
import jw.fluent_plugin.implementation.modules.spigot.FluentApiSpigotExtention;
import jw.fluent_plugin.implementation.modules.translator.FluentTranslationExtention;
import jw.fluent_plugin.implementation.modules.translator.FluentTranslator;
import jw.fluent_plugin.implementation.extentions.FluentApiExtentionsManagerImpl;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FluentApiBuilderImpl implements FluentApiBuilder {
    private final SimpleFileBuilderImpl simpleFilesBuilder;
    private final FluentApiContainerBuilderImpl containerBuilder;
    private final SimpleLoggerBuilderImpl simpleLoggerBuilder;
    private final FluentApiDefaultCommandBuilder commandBuilder;
    private final FluentApiExtentionsManagerImpl extentionsManager;
    private final FluentPermissionBuilderImpl fluentPermissionBuilder;
    private final FluentConfigImpl configFile;
    private final JavaPlugin plugin;
    private final ClassTypesManager typeManager;


    @SneakyThrows
    public FluentApiBuilderImpl(JavaPlugin plugin) {

        this.plugin = plugin;

        extentionsManager = new FluentApiExtentionsManagerImpl();
        containerBuilder = new FluentApiContainerBuilderImpl(extentionsManager, plugin);
        simpleFilesBuilder = new SimpleFileBuilderImpl();
        simpleLoggerBuilder = new SimpleLoggerBuilderImpl(plugin.getLogger());
        commandBuilder = new FluentApiDefaultCommandBuilder();
        fluentPermissionBuilder = new FluentPermissionBuilderImpl(plugin);
        typeManager = new ClassTypesManager(ClassTypeUtility.findClassesInPlugin(plugin));

        var configPath = FileUtility.pluginPath(plugin) + File.separator + "config.yml";
        configFile = new PluginConfigFactory(typeManager, plugin).create(configPath, typeManager);
    }

    public FluentApiCommandBuilder command() {
        return commandBuilder;
    }

    public FluentApiContainerBuilder container() {
        return containerBuilder;
    }

    @Override
    public FluentApiBuilder useExtention(FluentApiExtention extention)
    {
        extentionsManager.register(extention);
        return this;
    }

    public SimpleLoggerBuilder logger() {
        return simpleLoggerBuilder;
    }

    public SimpleFilesBuilder files() {
        return simpleFilesBuilder;
    }

    public FluentConfig config() {
        return configFile;
    }

    public FluentPermissionBuilder permissions() {
        return fluentPermissionBuilder;
    }

    @Override
    public JavaPlugin plugin() {
        return plugin;
    }

    public FluentApi build() throws Exception {
        useExtention(new FluentLoggerExtention(simpleLoggerBuilder));
        useExtention(new FluentPermissionExtention(fluentPermissionBuilder));
        useExtention(new FluentMediatorExtention(typeManager));
        useExtention(new FluentMapperExtention());
        useExtention(new FluentFilesExtention(simpleFilesBuilder));
        useExtention(new FluentTranslationExtention());

        useExtention(new FluentApiSpigotExtention());

        extentionsManager.onConfiguration(this);

        containerBuilder.registerSigleton(FluentConfig.class, configFile);
        var injectionFactory = new FluentInjectionExtention(containerBuilder, typeManager);
        var result = injectionFactory.create();
        useExtention(injectionFactory);
        useExtention(new CommandExtention(commandBuilder));

        var injection = result.fluentInjection();
        var mapper= injection.findInjection(FluentMapper.class);
        var mediator =  injection.findInjection(FluentMediator.class);
        var files =   injection.findInjection(FluentFiles.class);
        var translator = injection.findInjection(FluentTranslator.class);
        var _logger = injection.findInjection(FluentLogger.class);
        var permissions = injection.findInjection(FluentPermission.class);
        var fluentAPISpigot = injection.findInjection(FluentApiSpigot.class);

        return new FluentApi(
                plugin,
                injection,
                mapper,
                mediator,
                files,
                translator,
                _logger,
                fluentAPISpigot,
                extentionsManager,
                configFile,
                permissions);
    }
}
