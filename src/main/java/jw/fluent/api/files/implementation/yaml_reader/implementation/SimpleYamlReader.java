package jw.fluent.api.files.implementation.yaml_reader.implementation;

import jw.fluent.api.files.implementation.yaml_reader.api.YamlReader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SimpleYamlReader implements YamlReader {
    private final SimpleYamlModelFactory factory;
    private final SimpleYamlModelMapper mapper;

    public SimpleYamlReader() {
        factory = new SimpleYamlModelFactory();
        mapper = new SimpleYamlModelMapper();
    }

    @Override
    public <T> YamlConfiguration toConfiguration(T data) throws IllegalAccessException, ClassNotFoundException {
        var configuration = new YamlConfiguration();
        return toConfiguration(data, configuration);
    }

    @Override
    public <T> YamlConfiguration toConfiguration(T data, YamlConfiguration configuration) throws IllegalAccessException, ClassNotFoundException {
        var model = factory.createModel(data.getClass());
        return mapper.mapToConfiguration(data, model, configuration);
    }

    @Override
    public <T> T fromConfiguration(File file, Class<T> clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        var configuration = YamlConfiguration.loadConfiguration(file);
        return fromConfiguration(configuration, clazz);
    }

    @Override
    public <T> T fromConfiguration(YamlConfiguration configuration, Class<T> clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        var instance = clazz.newInstance();
        return fromConfiguration(configuration, instance);
    }

    @Override
    public <T> T fromConfiguration(YamlConfiguration configuration, T object) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        var model = factory.createModel(object.getClass());
        return (T)mapper.mapFromConfiguration(object,model, configuration);
    }
}
