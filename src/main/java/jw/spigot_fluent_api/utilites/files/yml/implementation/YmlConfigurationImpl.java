package jw.spigot_fluent_api.utilites.files.yml.implementation;

import jw.spigot_fluent_api.utilites.files.yml.api.YmlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YmlConfigurationImpl implements YmlConfiguration
{
    public  <T> FileConfiguration toConfiguration(T obj) throws IllegalAccessException {
        var configuration = new YamlConfiguration();
        var model = new YmlModelFactory<T>().createModel(obj);

        for(var content : model.getContents())
        {
            var value = content.getValue();
            configuration.set(content.getFullPath(),value);
        }
        if(model.getDescription().length() != 0)
        {
            configuration.options().header(model.getDescription());
        }
        return configuration;
    }

    public <T> T fromConfiguration(File file, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        var configuration = YamlConfiguration.loadConfiguration(file);
        T result = tClass.newInstance();
        var model = new YmlModelFactory<T>().createModel(result);
        for (var content : model.getContents()) {
            try
            {
                content.setValue(configuration);
            }
            catch (Exception e)
            {
                if(content.isRequired())
                {
                    throw e;
                }
            }
        }
        return result;
    }
}
