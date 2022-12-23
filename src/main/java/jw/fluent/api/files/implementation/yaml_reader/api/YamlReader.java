package jw.fluent.api.files.implementation.yaml_reader.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface YamlReader {
    <T> YamlConfiguration toConfiguration(T data) throws IllegalAccessException, ClassNotFoundException;

    <T> YamlConfiguration toConfiguration(T data, YamlConfiguration configuration) throws IllegalAccessException, ClassNotFoundException;
    <T> T fromConfiguration(File file, Class<T> clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException;

    <T> T fromConfiguration(YamlConfiguration configuration, Class<T> clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException;
    public <T> T fromConfiguration(YamlConfiguration configuration, T object) throws IllegalAccessException, InstantiationException, ClassNotFoundException;
}
