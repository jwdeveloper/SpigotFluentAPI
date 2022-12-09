package jw.fluent.api.files.implementation.file_handlers;

import jw.fluent.api.files.api.FileHandler;
import jw.fluent.api.files.implementation.yml.implementation.YmlConfigurationImpl;
import jw.fluent.api.utilites.java.ObjectUtility;
import jw.fluent.plugin.implementation.FluentApi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();
    public void load() throws IllegalAccessException, InstantiationException, IOException {

        var yalConfig  = new YmlConfigurationImpl();
        var pluginConfig =  FluentApi.plugin().getConfig();
        for (var file: files)
        {
            var data = yalConfig.fromConfiguration(pluginConfig,file.getClass());
            ObjectUtility.copyToObject(data, file, data.getClass());
        }
        pluginConfig.save(FluentApi.path()+ File.separator+"config.yml");
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
