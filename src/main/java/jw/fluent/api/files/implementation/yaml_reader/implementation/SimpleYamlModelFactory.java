package jw.fluent.api.files.implementation.yaml_reader.implementation;

import jw.fluent.api.files.implementation.yaml_reader.api.YamlModelFactory;
import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlFile;
import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlContent;
import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlModel;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SimpleYamlModelFactory implements YamlModelFactory {

    @Override
    public <T> YamlModel createModel(Class<T> clazz) throws ClassNotFoundException {
        var model = new YamlModel();
        var globalPath = getGlobalPath(clazz);
        var content = createContent(clazz, globalPath);
        model.setContents(content);
        model.setFileName(getFileName(clazz));
        model.setDescription(generateDescription(content));
        return model;
    }

    private List<YamlContent> createContent(Class<?> clazz, String path) throws ClassNotFoundException {
        var result = new ArrayList<YamlContent>();
        for (var field : clazz.getDeclaredFields()) {
            var annotation = field.getAnnotation(YamlProperty.class);
            if (annotation == null) {
                continue;
            }


            var content = getYamlContent(clazz, field, annotation);

            var fieldClazz = field.getType();
            var fieldPath = path.isEmpty() ? content.getPath() : path + "." + content.getPath();

            List<YamlContent> children = new ArrayList<YamlContent>();
            if (field.getType().isAssignableFrom(List.class)) {
                ParameterizedType arrayType = (ParameterizedType) field.getGenericType();
                var memberType = arrayType.getActualTypeArguments()[0];
                var memberClass = Class.forName(memberType.getTypeName());
                children = createContent(memberClass, fieldPath);
                content.setList(true);
            } else {
                children = createContent(fieldClazz, fieldPath);
            }

            content.setClazz(fieldClazz);
            content.setPath(fieldPath);
            content.setChildren(children);

            result.add(content);
        }
        return result;
    }

    private String generateDescription(List<YamlContent> contents) {
        if (contents.isEmpty())
            return StringUtils.EMPTY;


        var builder = FluentMessage.message();
        for (var ymlContent : contents) {
            var parentPath = ymlContent.getFullPath();


            if (StringUtils.isNotNullOrEmpty(ymlContent.getDescription()) && !ymlContent.getDescription().equals(" ")) {
                builder.newLine().text(parentPath).newLine()
                        .space().text(ymlContent.getDescription()).newLine().newLine();
            }


            for (var child : ymlContent.getChildren()) {
                var description = child.getDescription();
                if (description.isEmpty() || StringUtils.isNullOrEmpty(description) || description.equals(" ")) {
                    continue;
                }
                builder.newLine().text(parentPath + "." + child.getFullPath()).newLine()
                        .space().text(description).newLine();
            }
        }

        return builder.toString();
    }


    private <T> String getFileName(Class<T> clazz) {
        var defaultName = clazz.getSimpleName();
        var ymlFileAnnotation = clazz.getAnnotation(YamlFile.class);
        if (ymlFileAnnotation == null) {
            return defaultName;
        }
        if (ymlFileAnnotation.fileName().isEmpty()) {
            return defaultName;
        }
        return ymlFileAnnotation.fileName();
    }

    private <T> String getGlobalPath(Class<T> clazz) {
        var ymlFileAnnotation = clazz.getAnnotation(YamlFile.class);
        if (ymlFileAnnotation == null) {
            return StringUtils.EMPTY;
        }
        return ymlFileAnnotation.globalPath();
    }


    private YamlContent getYamlContent(Class<?> clazz, Field field, YamlProperty property) {
        var result = new YamlContent();
        result.setField(field);
        result.setName(field.getName());
        result.setClazz(clazz);

        result.setDescription(property.description());
        if (!property.name().isEmpty()) {
            result.setName(property.name());
        }
        if (!property.path().isEmpty()) {
            result.setPath(property.path());
        }
        return result;
    }
}
