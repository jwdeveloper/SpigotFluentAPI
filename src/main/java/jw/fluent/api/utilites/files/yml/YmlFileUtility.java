package jw.fluent.api.utilites.files.yml;

import jw.fluent.api.utilites.files.yml.implementation.YmlConfigurationImpl;
import jw.fluent.api.utilites.files.FileUtility;
import jw.fluent.api.utilites.files.yml.api.models.FileStatus;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;
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
            FluentApi.logger().error("Could not save YML " + fileName + " path", e);
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
            FluentApi.logger().error("Error while load YML ", e);
        }
        return null;
    }

    private static File ensureFile(String name) {
        File file = new File(FluentApi.path(), File.separator + name + ".yml");
        if (!file.exists()) {
            try {
                FileConfiguration configuration = new YamlConfiguration();
                configuration.save(file);
            } catch (IOException exception) {
                FluentApi.logger().error("YML error", exception);
            }
        }
        return file;
    }

    private static FileStatus checkFile(String name) {
        File file = new File(FluentApi.path(), File.separator + name + ".yml");
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
            FluentApi.logger().error("YML error", exception);
        }
        return result;
    }

}
