package jw.fluent.api.files.implementation.yml.implementation;

import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.files.implementation.yml.api.YmlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YmlConfigurationImpl implements YmlConfiguration {

    public <T> FileConfiguration toConfiguration(T obj, FileConfiguration configuration) throws IllegalAccessException {
        var model = new YmlModelFactory<T>().createModel(obj);

        for (var content : model.getContents()) {
            var value = content.getValue();
            configuration.set(content.getFullPath(), value);
        }
        if (model.getDescription().length() != 0) {
            var desc = FluentMessage.message()
                    .text(configuration.options().header())
                    .newLine()
                    .text(model.getDescription())
                    .toString();
            configuration.options().header(desc);
        }
        return configuration;
    }

    public <T> FileConfiguration toConfiguration(T obj) throws IllegalAccessException {
        var configuration = new YamlConfiguration();
        return toConfiguration(obj, configuration);
    }

    public <T> T fromConfiguration(File file, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        var configuration = YamlConfiguration.loadConfiguration(file);
        return fromConfiguration(configuration, tClass);
    }

    public <T> T fromConfiguration(FileConfiguration configuration, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        T result = tClass.newInstance();
        var model = new YmlModelFactory<T>().createModel(result);
        for (var content : model.getContents()) {
            try {
                content.setValue(configuration);
            } catch (Exception e) {
                if (content.isRequired()) {
                    throw e;
                }
            }
        }
        return result;
    }
}
