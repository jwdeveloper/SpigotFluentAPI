package jw.fluent.plugin.implementation;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.implementation.builder.CommandBuilderImpl;
import jw.fluent.api.spigot.events.FluentEvents;
import jw.fluent.api.spigot.messages.SimpleMessage;
import jw.fluent.api.spigot.particles.implementation.ParticleSettings;
import jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline.FluentParticlebuilder;
import jw.fluent.api.spigot.tasks.SimpleTasks;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent.plugin.implementation.modules.files.FluentFiles;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.mapper.FluentMapper;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent.plugin.implementation.modules.player_context.implementation.FluentPlayerContext;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.fluent.plugin.api.extention.FluentApiExtensionsManager;
import jw.fluent.plugin.api.config.FluentConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class FluentApiSpigot {

    private final FluentInjection fluentInjection;
    private final FluentMapper fluentMapper;
    private final FluentMediator fluentMediator;
    private final FluentFiles fluentFiles;
    private final FluentTranslator fluentTranslator;
    private final FluentConfig fluentConfig;
    private final FluentPermission fluentPermission;
    private final FluentPlayerContext playerContext;
    private final JavaPlugin plugin;

    private final FluentEvents fluentEvents;

    private final SimpleMessage simpleMessages;

    private final SimpleTasks simpleTasks;

    private final FluentApiExtensionsManager extensionsManager;

    public FluentApiSpigot(
            JavaPlugin plugin,
            FluentInjection injection,
            FluentMapper mapper,
            FluentMediator mediator,
            FluentFiles files,
            FluentTranslator translator,
            FluentConfig fluentConfig,
            FluentPermission permission,
            FluentPlayerContext playerContext,
            FluentApiExtensionsManager extensionsManager) {
        this.plugin = plugin;
        fluentInjection = injection;
        fluentMapper = mapper;
        fluentMediator = mediator;
        fluentFiles = files;
        fluentTranslator = translator;
        fluentPermission = permission;
        this.fluentConfig = fluentConfig;
        this.playerContext = playerContext;
        this.extensionsManager = extensionsManager;
        fluentEvents = new FluentEvents(plugin);
        simpleMessages = new SimpleMessage();
        simpleTasks = new SimpleTasks();
    }

    public void enable() {
        extensionsManager.onEnable(this);
    }

    public void disable() {
        extensionsManager.onDisable(this);
    }

    public FluentPlayerContext playerContext() {
        return playerContext;
    }

    public FluentPermission permission() {
        return fluentPermission;
    }

    public FluentInjection container() {
        return fluentInjection;
    }

    public FluentMediator mediator() {
        return fluentMediator;
    }

    public FluentMapper mapper() {
        return fluentMapper;
    }

    public FluentFiles files() {
        return fluentFiles;
    }

    public FluentTranslator translator() {
        return fluentTranslator;
    }

    public FluentConfig config() {
        return fluentConfig;
    }

    public FluentLogger logger() {

        return FluentLogger.LOGGER;
    }

    public String path() {

        return FileUtility.pluginPath(plugin);
    }

    public FluentEvents events() {
        return fluentEvents;
    }

    public SimpleMessage messages() {
        return simpleMessages;
    }

    public SimpleTasks tasks() {
        return simpleTasks;
    }

    public CommandBuilder commands(String commandName) {
        return new CommandBuilderImpl(commandName);
    }

    public static FluentParticlebuilder particles() {
        return new FluentParticlebuilder(new ParticleSettings());
    }

    public JavaPlugin plugin() {

        return plugin;
    }

}

