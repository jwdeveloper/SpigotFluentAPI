package jw.fluent.api.files.implementation.yaml_reader.implementation;

import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlContent;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SimpleYamlValueResolver {


    public <T> void setValue(T data, YamlConfiguration configuration, YamlContent content) throws IllegalAccessException {
        Object value = getFieldValue(data, content);
        configuration.set(content.getFullPath(), value);
    }


    private Object getFieldValue(Object object, YamlContent content) throws IllegalAccessException {
        var field = content.getField();
        field.setAccessible(true);
        Object value = field.get(object);
        if (value == null) {
            return getDefaultValue(content.getClazz());
        }
        field.setAccessible(false);
        if (value.getClass().equals(Material.class)) {
            var material = (Material) value;
            value = material.name();
        }
        if (value.getClass().equals(ChatColor.class)) {
            var color = (ChatColor) value;
            value = color.name();
        }
        if (field.getType().getName().equalsIgnoreCase("double")) {
            value = Double.parseDouble(value.toString());
        }
        if (field.getType().getName().equalsIgnoreCase("float")) {
            value = Float.parseFloat(value.toString());
        }
        return value;
    }

    private Object getDefaultValue(Class<?> type) {
        if (type.equals(String.class)) {
            return StringUtils.EMPTY;
        }
        if (type.equals(Integer.class)) {
            return 0;
        }
        if (type.equals(Float.class)) {
            return 0;
        }
        if (type.equals(Double.class)) {
            return 0;
        }
        if (type.equals(Boolean.class)) {
            return false;
        }
        if (type.equals(Material.class)) {
            return Material.DIRT;
        }
        if (type.equals(ChatColor.class)) {
            return ChatColor.WHITE;
        }

        return StringUtils.EMPTY;
    }

    public ConfigurationSection setObject(Object object, YamlConfiguration configuration, YamlContent content) {
        var section = configuration.createSection(content.getFullPath());
        try {
            content.getField().setAccessible(true);
            var instance = content.getField().get(object);
            if (instance == null) {
                instance = content.getClazz().newInstance();
            }

            for (var child : content.getChildren()) {
                var value = getFieldValue(instance, child);
                section.set(child.getFullPath(), value);
            }
            content.getField().setAccessible(false);
        } catch (Exception e) {
            FluentLogger.LOGGER.log("Setting nested object error", e);
        }
        return section;
    }

    public ConfigurationSection setListContent(Object object, YamlConfiguration configuration, YamlContent content) {

        ConfigurationSection section = null;
        if (configuration.isConfigurationSection(content.getFullPath())) {
            section = configuration.getConfigurationSection(content.getFullPath());
        } else {
            section = configuration.createSection(content.getFullPath());
        }
        try {
            content.getField().setAccessible(true);
            var objects = (List<?>) content.getField().get(object);
            var i = 1;
            for (var obj : objects) {
                var childPath = "value-" + i;
                var childSection = section.createSection(childPath);
                for (var child : content.getChildren()) {
                    var value = getFieldValue(obj, child);
                    childSection.set(child.getName(), value);
                }
                i++;
            }
            content.getField().setAccessible(false);
        } catch (Exception e) {
            FluentLogger.LOGGER.log("List mapping error", e);
        }
        return section;
    }


    public Object getValue(ConfigurationSection section, YamlContent content) {
        var value = section.get(content.getFullPath());
        if (value == null) {
            return getDefaultValue(content.getClazz());
        }
        return value;
    }


    public Object getObject(ConfigurationSection section, YamlContent content) throws InstantiationException, IllegalAccessException {
        var nestedObjectSection = section.getConfigurationSection(content.getFullPath());
        var instance = content.getClazz().newInstance();
        if (nestedObjectSection == null) {
            return instance;
        }
        try {
            for (var child : content.getChildren()) {
                var value = getValue(nestedObjectSection, child);
                var field = child.getField();
                field.setAccessible(true);
                field.set(instance, value);
                field.setAccessible(false);
            }

        } catch (Exception e) {
            FluentLogger.LOGGER.log("Nested object mapping error", e);
        }
        return instance;
    }


    public Object getListContent(ConfigurationSection configuration, YamlContent content) {
        List<?> result = new ArrayList<>();
        try {
            var listPath = content.getFullPath();
            var listSection = configuration.getConfigurationSection(listPath);
            if (listSection == null) {
                return result;
            }
            var field = content.getField();
            var arrayType = (ParameterizedType) field.getGenericType();
            var memberType = arrayType.getActualTypeArguments()[0];
            var memberClass = Class.forName(memberType.getTypeName());


            var sectionKeys = listSection.getKeys(false);
            var methodAdd = result.getClass().getDeclaredMethod("add", Object.class);
            for (var path : sectionKeys) {
                var temp = memberClass.newInstance();
                var propertiesPath = configuration.getConfigurationSection(listPath + "." + path).getKeys(false);
                for (var childContent : content.getChildren()) {
                    if (!propertiesPath.contains(childContent.getName())) {
                        continue;
                    }
                    var childPath2 = listPath + "." + path + "." + childContent.getName();
                    var value = configuration.get(childPath2);

                    childContent.getField().setAccessible(true);
                    childContent.getField().set(temp, value);
                    childContent.getField().setAccessible(false);
                }
                methodAdd.invoke(result, temp);
            }
            return result;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Could not load list configuration", e);
        }
        return result;
    }
}
