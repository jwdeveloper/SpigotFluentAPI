package jw.fluent.api.files.implementation.yml.api;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface YmlConfiguration
{

     <T> FileConfiguration toConfiguration(T obj) throws IllegalAccessException;

      <T> T fromConfiguration(File file, Class<T> tClass) throws IllegalAccessException, InstantiationException;
}
