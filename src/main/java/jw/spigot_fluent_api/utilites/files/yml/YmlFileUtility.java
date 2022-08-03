package jw.spigot_fluent_api.utilites.files.yml;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.files.yml.api.models.FileStatus;
import jw.spigot_fluent_api.utilites.files.yml.implementation.YmlConfigurationImpl;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YmlFileUtility implements FileUtility {
    public static boolean save(String fileName, Object data) {
        try {
            var file = ensureFile(fileName);
            var config =   new YmlConfigurationImpl().toConfiguration(data);
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
            return new YmlConfigurationImpl().fromConfiguration(fileStatus.getFile(), (Class<T>) type.getClass());
        } catch (Exception e) {
            FluentLogger.error("Error while load YML ", e);
        }
        return null;
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
