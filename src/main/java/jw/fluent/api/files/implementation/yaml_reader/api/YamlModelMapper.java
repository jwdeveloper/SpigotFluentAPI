package jw.fluent.api.files.implementation.yaml_reader.api;

import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public interface YamlModelMapper {
    public <T> YamlConfiguration mapToConfiguration(T data, YamlModel model, YamlConfiguration configuration) throws IllegalAccessException;
    public Object mapFromConfiguration(Object object, YamlModel model, YamlConfiguration configuration) throws IllegalAccessException, InstantiationException;
}
