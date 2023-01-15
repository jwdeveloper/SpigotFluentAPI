package jw.fluent.api.files.implementation.yaml_reader.api;

import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.InvocationTargetException;

public interface YamlModelMapper {
    public <T> YamlConfiguration mapToConfiguration(T data, YamlModel model, YamlConfiguration configuration, boolean overrite) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    public Object mapFromConfiguration(Object object, YamlModel model, YamlConfiguration configuration) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException;
}
