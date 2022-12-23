package jw.fluent.api.files.implementation.yaml_reader.implementation;

import jw.fluent.api.files.implementation.yaml_reader.api.YamlModelMapper;
import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlModel;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import org.bukkit.configuration.file.YamlConfiguration;

public class SimpleYamlModelMapper implements YamlModelMapper {
    private final SimpleYamlValueResolver resolver;

    public SimpleYamlModelMapper()
    {
        resolver = new SimpleYamlValueResolver();
    }

    @Override
    public <T> YamlConfiguration mapToConfiguration(T data, YamlModel model, YamlConfiguration configuration) throws IllegalAccessException {
        for (var content : model.getContents())
        {
            if(content.isList())
            {
                resolver.setListContent(data, configuration, content);
                continue;
            }
            if(content.isObject())
            {
                resolver.setObject(data,configuration,content);
                continue;
            }
            resolver.setValue(data,configuration,content);
        }
        if (model.hasDescription())
        {
            var description = FluentMessage
                    .message()
                    .text(configuration.options().header())
                    .newLine()
                    .text(model.getDescription())
                    .toString();
            configuration.options().header(description);
        }
        return configuration;
    }

    @Override
    public Object mapFromConfiguration(Object object, YamlModel model, YamlConfiguration configuration) throws IllegalAccessException, InstantiationException {
        for (var content : model.getContents())
        {
            var field = content.getField();
            field.setAccessible(true);
            if(content.isList())
            {
                var list = resolver.getListContent(configuration, content);
                field.set(object, list);
                continue;
            }
            if(content.isObject())
            {
                var value = resolver.getObject(configuration,content);
                field.set(object, value);
                continue;
            }
            var value = resolver.getValue(configuration,content);
            field.set(object, value);
        }
        return object;
    }
}
