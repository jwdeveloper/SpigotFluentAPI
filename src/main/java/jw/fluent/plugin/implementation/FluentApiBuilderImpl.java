package jw.fluent.plugin.implementation;

import jw.fluent.api.files.api.SimpleFilesBuilder;
import jw.fluent.api.files.implementation.SimpleFileBuilderImpl;
import jw.fluent.api.logger.api.SimpleLoggerBuilder;
import jw.fluent.api.logger.implementation.SimpleLoggerBuilderImpl;
import jw.fluent.api.utilites.ClassTypesManager;
import jw.fluent.api.utilites.files.FileUtility;
import jw.fluent.api.utilites.java.ClassTypeUtility;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiContainerBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.api.extention.ExtentionPiority;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.plugin.implementation.config.FluentConfigImpl;
import jw.fluent.plugin.implementation.config.PluginConfigFactory;
import jw.fluent.plugin.implementation.extentions.FluentApiExtentionsManagerImpl;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjectionExtention;
import jw.fluent.plugin.implementation.modules.files.FluentFiles;
import jw.fluent.plugin.implementation.modules.files.FluentFilesExtention;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.logger.FluentLoggerExtention;
import jw.fluent.plugin.implementation.modules.mapper.FluentMapper;
import jw.fluent.plugin.implementation.modules.mapper.FluentMapperExtention;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediatorExtention;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import jw.fluent.plugin.implementation.modules.permissions.implementation.FluentPermissionBuilderImpl;
import jw.fluent.plugin.implementation.modules.permissions.implementation.FluentPermissionExtention;
import jw.fluent.plugin.implementation.modules.spigot.FluentApiSpigotExtention;
import jw.fluent.plugin.implementation.modules.spigot.commands.FluentApiCommandBuilder;
import jw.fluent.plugin.implementation.modules.spigot.commands.FluentApiDefaultCommandBuilder;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslationExtention;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
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
        extentionsManager.register(extention, ExtentionPiority.MEDIUM);
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
        extentionsManager.registerLow(new FluentLoggerExtention(simpleLoggerBuilder));
        extentionsManager.registerLow(new FluentPermissionExtention(fluentPermissionBuilder));
        extentionsManager.registerLow(new FluentMediatorExtention(typeManager));
        extentionsManager.registerLow(new FluentMapperExtention());
        extentionsManager.registerLow(new FluentFilesExtention(simpleFilesBuilder));
        extentionsManager.registerLow(new FluentTranslationExtention());
        extentionsManager.register(new FluentApiSpigotExtention(), ExtentionPiority.HIGH);

        extentionsManager.onConfiguration(this);

        containerBuilder.registerSigleton(FluentConfig.class, configFile);
        var injectionFactory = new FluentInjectionExtention(containerBuilder, typeManager);
        var result = injectionFactory.create();
        useExtention(injectionFactory);

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
