package jw.fluent.plugin.implementation.config.migrations;

import jw.fluent.plugin.api.config.migrations.ConfigMigration;
import jw.fluent.plugin.api.config.migrations.ConfigMigrator;
import jw.fluent.plugin.implementation.assembly_scanner.AssemblyScanner;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class FluentConfigMigrator implements ConfigMigrator {
    private final AssemblyScanner assemblyScanner;
    private final JavaPlugin plugin;

    private final String VERSION_PATH = "plugin.version";

    public FluentConfigMigrator(AssemblyScanner assemblyScanner, JavaPlugin plugin) {
        this.assemblyScanner = assemblyScanner;
        this.plugin = plugin;
    }


    public boolean isPluginUpdated(YamlConfiguration yamlConfig) {
        return VersionCompare.isHigher(getCurrentPluginVersion(), getConfigVersion(yamlConfig));
    }

    @Override
    public void makeMigration(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException {
        var migrations = new ArrayList<ConfigMigration>();
        var migrationsClasses = assemblyScanner.findByInterface(ConfigMigration.class);
        for (var clazz : migrationsClasses) {
            var instance = (ConfigMigration) clazz.newInstance();
            migrations.add(instance);
        }
        var currentVersion = getCurrentPluginVersion();
        var configVersion = getConfigVersion(configuration);
        var sorted = getMigrationsBetween(migrations, configVersion, currentVersion);
        FluentLogger.LOGGER.log("Migration from",configVersion,"to",currentVersion,"has started");
        for(var migration : sorted)
        {
            FluentLogger.LOGGER.log("Migrating config to plugin version",migration.version());
            try {
                migration.onPluginUpdate(configuration);
            }
            catch (Exception e)
            {
                 FluentLogger.LOGGER.warning("Error while migration to"+migration.version(),e.getMessage());
            }

        }
        configuration.set(VERSION_PATH, currentVersion);
    }


    private List<ConfigMigration> getMigrationsBetween(List<ConfigMigration> migrations, String lastVersion, String currentVersion)
    {
        var sorted = sortMigrations(migrations);;
        var result = new ArrayList<ConfigMigration>();
        for (var migration : sorted)
        {
           var equalCurrent = currentVersion.equals(migration.version());

           var isLowerThenCurrent = VersionCompare.isLower(migration.version(), currentVersion);
           var isLowerThenOld = VersionCompare.isLower(lastVersion, migration.version());
           if((equalCurrent || isLowerThenCurrent) && isLowerThenOld)
           {
               result.add(migration);
           }
        }
        return result;
    }


    private List<ConfigMigration> sortMigrations(List<ConfigMigration> migrations) {
        var result = new ArrayList<ConfigMigration>();
        var versionNames = new ArrayList<>(migrations.stream().map(ConfigMigration::version).toList());
        versionNames.sort(VersionNumberComparator.getInstance());

        for (var version : versionNames) {
            var optional = migrations.stream().filter(c -> c.version().equals(version)).findFirst();
            if (optional.isEmpty()) {
                FluentLogger.LOGGER.warning("Problem with sort migration for version", version);
                continue;
            }
            result.add(optional.get());
        }
        return result;
    }

    private String getCurrentPluginVersion() {
        return plugin.getDescription().getVersion();
    }

    private String getConfigVersion(YamlConfiguration configuration) {
        return configuration.getString(VERSION_PATH);
    }
}
