package jw.fluent.api.files.implementation.file_handlers;

import jw.fluent.api.files.api.FileHandler;
import jw.fluent.api.files.implementation.yaml_reader.api.YamlReader;
import jw.fluent.api.files.implementation.yaml_reader.implementation.SimpleYamlReader;
import jw.fluent.api.utilites.java.ObjectUtility;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileHandler implements FileHandler {

    private final List<Object> configSections = new ArrayList<>();
    private final YamlReader reader;
    public ConfigFileHandler()
    {
        reader = new SimpleYamlReader();
    }

    public void load() throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        var pluginConfig =  (YamlConfiguration) FluentApi.plugin().getConfig();
        for (var configSection: configSections)
        {
            var result = reader.fromConfiguration(pluginConfig,configSection.getClass());
            ObjectUtility.copyToObject(result, configSection, result.getClass());
        }
        pluginConfig.save(FluentApi.path()+ File.separator+"config.yml");
    }


    @Override
    public void save()
    {

    }

    @Override
    public void addObject(Object object) {
        configSections.add(object);
    }
    @Override
    public void removeObject(Object object) {
        configSections.remove(object);
    }
}
