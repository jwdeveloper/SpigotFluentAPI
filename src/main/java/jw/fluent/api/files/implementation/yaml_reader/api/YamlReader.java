package jw.fluent.api.files.implementation.yaml_reader.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public interface YamlReader {
    <T> YamlConfiguration toConfiguration(T data) throws IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;

    <T> YamlConfiguration toConfiguration(T data, YamlConfiguration configuration) throws IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    <T> T fromConfiguration(File file, Class<T> clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;

    <T> T fromConfiguration(YamlConfiguration configuration, Class<T> clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    public <T> T fromConfiguration(YamlConfiguration configuration, T object) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
