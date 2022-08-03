package jw.spigot_fluent_api.utilites.files.yml.api;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface YmlConfiguration
{

     <T> FileConfiguration toConfiguration(T obj) throws IllegalAccessException;

      <T> T fromConfiguration(File file, Class<T> tClass) throws IllegalAccessException, InstantiationException;
}
