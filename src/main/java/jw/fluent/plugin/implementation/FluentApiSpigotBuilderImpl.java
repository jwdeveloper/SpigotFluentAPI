package jw.fluent.plugin.implementation;

import jw.fluent.api.files.api.SimpleFilesBuilder;
import jw.fluent.api.files.implementation.SimpleFileBuilderImpl;
import jw.fluent.api.logger.api.SimpleLoggerBuilder;
import jw.fluent.api.logger.implementation.SimpleLoggerBuilderImpl;
import jw.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import jw.fluent.plugin.implementation.assembly_scanner.AssemblyScanner;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiContainerBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.extention.ExtentionPiority;
import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.config.FluentConfigImpl;
import jw.fluent.plugin.implementation.config.FluentConfigLoader;
import jw.fluent.plugin.implementation.extentions.FluentApiExtentionsManagerImpl;
import jw.fluent.plugin.implementation.modules.command.FluentDefaultCommandExtension;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjectionExtention;
import jw.fluent.plugin.implementation.modules.files.FluentFiles;
import jw.fluent.plugin.implementation.modules.files.FluentFilesExtention;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLoggerExtention;
import jw.fluent.plugin.implementation.modules.mapper.FluentMapper;
import jw.fluent.plugin.implementation.modules.mapper.FluentMapperExtention;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediatorExtention;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import jw.fluent.plugin.implementation.modules.permissions.implementation.FluentPermissionBuilderImpl;
import jw.fluent.plugin.implementation.modules.permissions.implementation.FluentPermissionExtention;
import jw.fluent.plugin.implementation.modules.player_context.implementation.FluentPlayerContext;
import jw.fluent.plugin.implementation.modules.command.FluentApiCommandBuilder;
import jw.fluent.plugin.implementation.modules.command.FluentApiDefaultCommandBuilder;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslationExtention;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

public class FluentApiSpigotBuilderImpl implements FluentApiSpigotBuilder {
    private final SimpleFileBuilderImpl simpleFilesBuilder;
    private final FluentApiContainerBuilderImpl containerBuilder;
    private final SimpleLoggerBuilderImpl simpleLoggerBuilder;
    private final FluentApiDefaultCommandBuilder commandBuilder;
    private final FluentApiExtentionsManagerImpl extensionsManager;
    private final FluentPermissionBuilderImpl fluentPermissionBuilder;
    private final FluentConfigImpl configFile;
    private final JavaPlugin plugin;
    private final AssemblyScanner assemblyScanner;


    @SneakyThrows
    public FluentApiSpigotBuilderImpl(JavaPlugin plugin) {
        this.plugin = plugin;

        extensionsManager = new FluentApiExtentionsManagerImpl();
        containerBuilder = new FluentApiContainerBuilderImpl(extensionsManager, plugin);
        simpleFilesBuilder = new SimpleFileBuilderImpl();
        simpleLoggerBuilder = new SimpleLoggerBuilderImpl(plugin.getLogger());
        commandBuilder = new FluentApiDefaultCommandBuilder();
        fluentPermissionBuilder = new FluentPermissionBuilderImpl(plugin);
        assemblyScanner = new AssemblyScanner(plugin);
        configFile = FluentConfigLoader.loadConfig(plugin, assemblyScanner);
    }

    public FluentApiCommandBuilder defaultCommand() {
        return commandBuilder;
    }

    public FluentApiContainerBuilder container() {
        return containerBuilder;
    }

    @Override
    public FluentApiSpigotBuilder useExtension(FluentApiExtension extension) {
        extensionsManager.register(extension, ExtentionPiority.MEDIUM);
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

    @Override
    public FluentAssemblyScanner assemblyScanner() {
        return assemblyScanner;
    }

    public FluentApiSpigot build() throws Exception {
        extensionsManager.registerLow(new FluentLoggerExtention(simpleLoggerBuilder));
        extensionsManager.registerLow(new FluentPermissionExtention(fluentPermissionBuilder));
        extensionsManager.registerLow(new FluentMediatorExtention(assemblyScanner));
        extensionsManager.registerLow(new FluentMapperExtention());
        extensionsManager.registerLow(new FluentFilesExtention(simpleFilesBuilder));
        extensionsManager.registerLow(new FluentTranslationExtention());
        extensionsManager.register(new FluentDefaultCommandExtension(commandBuilder), ExtentionPiority.HIGH);

        extensionsManager.onConfiguration(this);

        containerBuilder.registerSigleton(FluentConfig.class, configFile);
        containerBuilder.registerSigleton(JavaPlugin.class, plugin);
        containerBuilder.registerSigleton(FluentAssemblyScanner.class, assemblyScanner);
        final var injectionFactory = new FluentInjectionExtention(containerBuilder, assemblyScanner);
        final var result = injectionFactory.create();
        useExtension(injectionFactory);

        final var injection = result.fluentInjection();
        final var mapper = injection.findInjection(FluentMapper.class);
        final var mediator = injection.findInjection(FluentMediator.class);
        final var files = injection.findInjection(FluentFiles.class);
        final var translator = injection.findInjection(FluentTranslator.class);
        final var permissions = injection.findInjection(FluentPermission.class);
        final var playerContext = injection.findInjection(FluentPlayerContext.class);

        return new FluentApiSpigot(
                plugin,
                injection,
                mapper,
                mediator,
                files,
                translator,
                configFile,
                permissions,
                playerContext,
                extensionsManager);
    }
}
