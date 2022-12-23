package jw.fluent.plugin.api.config.migrations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigMigration
{

    String version();
    void onPluginUpdate(YamlConfiguration config);
}
