package jw.fluent_plugin.implementation;

import jw.fluent_api.logger.implementation.SimpleLoggerImpl;
import jw.fluent_api.spigot.events.FluentEvent;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_plugin.api.extention.FluentApiExtentionsManager;
import jw.fluent_plugin.implementation.config.FluentConfig;
import jw.fluent_plugin.implementation.modules.files.FluentFiles;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import jw.fluent_plugin.implementation.modules.logger.FluentLoggerImpl;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent_plugin.implementation.modules.translator.FluentTranslator;
import jw.fluent_plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent_plugin.implementation.modules.mapper.FluentMapper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class FluentApi {

    private static FluentApi INSTANCE;
    @Getter
    private final FluentInjection fluentInjection;

    @Getter
    private final FluentMapper fluentMapper;

    @Getter
    private final FluentMediator fluentMediator;

    @Getter
    private final FluentFiles fluentFiles;

    @Getter
    private final FluentTranslator fluentTranslator;

    @Getter
    private final FluentLogger fluentLogger;

    @Getter
    private final FluentConfig fluentConfig;

    @Getter
    private final FluentPermission fluentPermission;
    @Getter
    private final FluentApiSpigot spigotFluentApi;

    @Getter
    static JavaPlugin plugin;

    private final FluentApiExtentionsManager extentionsManager;

    FluentApi(
            JavaPlugin plugin,
            FluentInjection injection,
            FluentMapper mapper,
            FluentMediator mediator,
            FluentFiles files,
            FluentTranslator translator,
            FluentLogger logger,
            FluentApiSpigot fluentAPISpigot,
            FluentApiExtentionsManager extentionsManager,
            FluentConfig fluentConfig,
            FluentPermission permission) {
        this.plugin = plugin;
        fluentInjection = injection;
        fluentMapper = mapper;
        fluentMediator = mediator;
        fluentFiles = files;
        fluentTranslator = translator;
        fluentLogger = logger;
        spigotFluentApi = fluentAPISpigot;
        fluentPermission = permission;
        this.fluentConfig = fluentConfig;
        this.extentionsManager = extentionsManager;
    }

    public void enable() {
        INSTANCE = this;
        extentionsManager.onEnable(this);
    }

    public void disable() {
        extentionsManager.onDisable(this);
        INSTANCE = null;
    }

    private static FluentApi getInstance() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE;
    }

    public static FluentInjection injection() {
        return getInstance().fluentInjection;
    }

    public static FluentMediator mediator() {
        return getInstance().fluentMediator;
    }

    public static FluentMapper mapper() {
        return getInstance().fluentMapper;
    }

    public static FluentFiles files() {
        return getInstance().fluentFiles;
    }

    public static FluentLogger logger() {

        if(getInstance() == null)
        {
          return FluentLogger.LOGGER;
        }
        return getInstance().fluentLogger;
    }

    public static String  path() {

        return FileUtility.pluginPath(plugin);
    }

    public static JavaPlugin plugin() {

        return plugin;
    }

    public static FluentTranslator translator() {
        return getInstance().fluentTranslator;
    }

    public static FluentApiSpigot spigot() {
        return getInstance().spigotFluentApi;
    }

}

