package jw.spigot_fluent_api.utilites.files.yml;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.files.json.annotations.JsonIgnore;
import jw.spigot_fluent_api.utilites.files.yml.annotations.YmlIgnore;
import jw.spigot_fluent_api.utilites.files.yml.models.FileStatus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class YmlFileUtility implements FileUtility {
    public static boolean save(String fileName, Object data) {
        try {
            var file = ensureFile(fileName);
            var config = objectToConfiguration(data);
            config.save(file);
            return true;
        } catch (Exception e) {
            FluentLogger.error("Could not save YML " + fileName + " file", e);
            return false;
        }
    }

    public static <T> T load(String fileName, Object type) {
        var fileStatus = checkFile(fileName);
        try {
            if (fileStatus.isAlreadyCreated()) {
                save(fileName, type);
            }
            return configurationToObject(fileStatus.getFile(), (Class<T>) type.getClass());
        } catch (Exception e) {
            FluentLogger.error("Error while load YML ", e);
        }
        return null;
    }

    private static <T> FileConfiguration objectToConfiguration(T obj) throws IllegalAccessException {
        FileConfiguration configuration = new YamlConfiguration();
        var tClass = obj.getClass();
        var className = tClass.getSimpleName();
        configuration.createSection(className);
        for (var field : tClass.getDeclaredFields()) {

            if (field.getAnnotation(YmlIgnore.class) != null) {
                continue;
            }

            field.setAccessible(true);
            Object value = field.get(obj);
            field.setAccessible(false);
            if (value.getClass().equals(Material.class)) {
                var material = (Material) value;
                value = material.name();
            }
            if (value.getClass().equals(ChatColor.class)) {
                var color = (ChatColor) value;
                value = color.name();
            }
            configuration.set(className + "." + field.getName(), value);
        }
        return configuration;
    }

    private static <T> T configurationToObject(File file, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        var className = tClass.getSimpleName();
        var section = configuration.getConfigurationSection(className);
        T result = tClass.newInstance();
        if (section == null)
            return result;

        for (var field : tClass.getDeclaredFields()) {
            if (field.getAnnotation(YmlIgnore.class) != null) {
                continue;
            }
            if(!section.contains(field.getName()))
            {
                continue;
            }
            var value = section.get(field.getName());
            if (field.getType().equals(Material.class)) {
                value = Material.valueOf((String) value);
            }
            if (field.getType().equals(ChatColor.class)) {
                value = section.getColor(field.getName());
            }
            if (field.getType().equals(ItemStack.class)) {
                value = section.getItemStack(field.getName());
            }
            if (field.getType().getName().equalsIgnoreCase("double")) {
                value = Double.parseDouble(value.toString());
            }
            if (field.getType().getName().equalsIgnoreCase("float")) {
                value = Float.parseFloat(value.toString());
            }
            field.setAccessible(true);
            field.set(result, value);
            field.setAccessible(false);
        }
        return result;
    }

    private static File ensureFile(String name) {
        File file = new File(FluentPlugin.getPath(), File.separator + name + ".yml");
        if (!file.exists()) {
            try {
                FileConfiguration configuration = new YamlConfiguration();
                configuration.save(file);
            } catch (IOException exception) {
                FluentLogger.error("YML error", exception);
            }
        }
        return file;
    }

    private static FileStatus checkFile(String name) {
        File file = new File(FluentPlugin.getPath(), File.separator + name + ".yml");
        var result = new FileStatus(file);
        if (file.exists()) {
            return result;
        }
        try {
            result.setAlreadyCreated(true);
            FileConfiguration configuration = new YamlConfiguration();
            configuration.save(file);
            return result;
        } catch (IOException exception) {
            FluentLogger.error("YML error", exception);
        }
        return result;
    }

}
