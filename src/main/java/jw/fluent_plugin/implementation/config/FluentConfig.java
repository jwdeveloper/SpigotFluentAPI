package jw.fluent_plugin.implementation.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface FluentConfig {
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
