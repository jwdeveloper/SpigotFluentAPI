package jw.fluent.plugin.implementation.config.config_sections;

import org.bukkit.configuration.file.FileConfiguration;

public interface FluentConfigSection
{
    void migrate(FileConfiguration oldVersion);
}
