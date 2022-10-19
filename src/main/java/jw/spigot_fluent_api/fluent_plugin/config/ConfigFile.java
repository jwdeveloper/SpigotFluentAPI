package jw.spigot_fluent_api.fluent_plugin.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigFile {
    Object getRequired(String name) throws Exception;

    <T> T getOrCreate(String path, T defaultValue, String... description);

    <T> T getOrCreate(ConfigProperty<T> configProperty);

    <T> T get(String name);

    <T> T toObject(Class<T> clazz);

    void save();

    FileConfiguration config();

    boolean created();

    boolean updated();
}
