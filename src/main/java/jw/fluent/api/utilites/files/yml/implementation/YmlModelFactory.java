package jw.fluent.api.utilites.files.yml.implementation;

import jw.fluent.api.spigot.messages.FluentMessage;
import jw.fluent.api.utilites.files.yml.api.ModelFactory;
import jw.fluent.api.utilites.files.yml.api.annotations.YmlFile;
import jw.fluent.api.utilites.files.yml.api.annotations.YmlIgnore;
import jw.fluent.api.utilites.files.yml.api.annotations.YmlProperty;
import jw.fluent.api.utilites.files.yml.api.models.YmlContent;
import jw.fluent.api.utilites.files.yml.api.models.YmlModel;
import jw.fluent.api.utilites.java.StringUtils;

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
                content.setPath(globalPath  + content.getPath());
            }
            else
            {
                content.setPath(content.getPath());
            }
            result.addContent(content);
        }
        result.setDescription(generateDescription(result.getContents()));
        return result;
    }

    private String generateDescription(List<YmlContent> contents) {
        var content = contents.stream().filter(c -> !c.getDescription().isEmpty()).toList();
        if(content.size() == 0)
            return StringUtils.EMPTY_STRING;

        var description = FluentMessage.message();
        var maxDesc = Integer.MIN_VALUE;
        for (int i = 0; i < content.size(); i++) {
            var desc  = content.get(i).getDescription();
            description.text(content.get(i).getFullPath()).newLine()
                    .text("-> ").text(desc).newLine().newLine();
            if(desc.length() > maxDesc)
            {
                maxDesc = desc.length();
            }
        }
       // var bar = FluentMessage.message().bar("#",maxDesc).newLine();
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
            return StringUtils.EMPTY_STRING;
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
