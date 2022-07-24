package jw.spigot_fluent_api.utilites.files.yml.implementation;

import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.yml.api.ModelFactory;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlIgnore;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlProperty;
import jw.spigot_fluent_api.utilites.files.yml.api.models.YmlContent;
import jw.spigot_fluent_api.utilites.files.yml.api.models.YmlModel;
import jw.spigot_fluent_api.utilites.java.JavaUtils;

import java.lang.reflect.Field;
import java.util.List;

public class YmlModelFactory<T> implements ModelFactory<T> {

    @Override
    public YmlModel createModel(T object) {
        var result = new YmlModel();
        var clazz = (Class<T>) object.getClass();
        result.setFileName(getFileName(clazz));
        var globalPath = getGlobalPath(clazz);

        for (var field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(YmlIgnore.class) != null) {
                continue;
            }
            var content = getContent(field);
            content.setObject(object);
            if(!globalPath.isEmpty())
            {
                content.setPath(globalPath + "." + content.getPath());
            }
            else
            {
                content.setPath(content.getPath());
            }
            result.addContent(content);
        }
        result.setDescription(generateDescription(result.getContents()));
        System.out.println(result.getDescription());
        return result;
    }

    private String generateDescription(List<YmlContent> contents) {
        var content = contents.stream().filter(c -> !c.getDescription().isEmpty()).toList();
        if(content.size() == 0)
            return JavaUtils.EMPTY_STRING;

        var description = FluentMessage.message();
        description.bar("*", 40).newLine();
        for (int i = 0; i < content.size(); i++) {
            description.text(content.get(i).getFullPath()).newLine()
                    .text("-> ").text(content.get(i).getDescription()).newLine();
            if (i < content.size() - 1) {
                description.bar("*", 40).newLine();
            }

        }
        description.bar("*", 40).newLine();
        return description.toString();
    }


    private String getFileName(Class<T> clazz) {
        var result = clazz.getSimpleName();
        var ymlFileAnnotation = clazz.getAnnotation(YmlFile.class);
        if (ymlFileAnnotation == null) {
            return result;
        }
        if (ymlFileAnnotation.fileName().isEmpty()) {
            return result;
        }
        return ymlFileAnnotation.fileName();
    }

    private String getGlobalPath(Class<T> clazz) {
        var ymlFileAnnotation = clazz.getAnnotation(YmlFile.class);
        if (ymlFileAnnotation == null) {
            return JavaUtils.EMPTY_STRING;
        }
        return ymlFileAnnotation.globalPath();
    }


    private YmlContent getContent(Field field) {
        var result = new YmlContent();
        result.setField(field);
        result.setName(field.getName());

        var annotation = field.getAnnotation(YmlProperty.class);
        if (annotation == null) {
            return result;
        }
        result.setDescription(annotation.description());
        if (!annotation.name().isEmpty()) {
            result.setName(annotation.name());
        }

        if (!annotation.path().isEmpty()) {
            result.setPath(annotation.path());
        }
        result.setRequired(annotation.required());

        return result;
    }
}