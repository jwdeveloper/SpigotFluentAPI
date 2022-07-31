package jw.spigot_fluent_api.data.implementation.file_handlers;

import jw.spigot_fluent_api.data.interfaces.FileHandler;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.yml.implementation.YmlConfigurationImpl;
import jw.spigot_fluent_api.utilites.java.ObjectUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();
    public void load() throws IllegalAccessException, InstantiationException, IOException {

        var yalConfig  = new YmlConfigurationImpl();
        var pluginConfig =  FluentPlugin.getPlugin().getConfig();
        for (var file: files)
        {
            var data = yalConfig.fromConfiguration(pluginConfig,file.getClass());
            ObjectUtility.copyToObject(data, file, data.getClass());
        }
        pluginConfig.save(FluentPlugin.getPath()+ File.separator+"config.yml");
    }


    @Override
    public void save() {

    }

    @Override
    public void addObject(Object object) {
        files.add(object);
    }
    @Override
    public void removeObject(Object object) {
        files.remove(object);
    }
}
