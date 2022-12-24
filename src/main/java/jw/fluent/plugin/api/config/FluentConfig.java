package jw.fluent.plugin.api.config;

import jw.fluent.plugin.api.config.ConfigProperty;
import org.bukkit.configuration.file.FileConfiguration;

public interface FluentConfig {
    Object getRequired(String name) throws Exception;

    <T> T getOrCreate(String path, T defaultValue, String... description);

    <T> T getOrCreate(ConfigProperty<T> configProperty);

    <T> T get(String name);

    <T> T toObject(Class<T> clazz);

    void save();

    void save(Object object);

    FileConfiguration configFile();

    boolean created();

    boolean updated();
}
