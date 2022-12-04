package jw.fluent.api.spigot.documentation.implementation.decorator;

import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.spigot.permissions.api.PermissionGeneratorDto;
import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.api.spigot.permissions.api.PermissionSection;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionGroup;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionProperty;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionTitle;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;

import java.io.IOException;
import java.util.*;

public class PermissionDocumentationDecorator extends DocumentationDecorator {

    private final PermissionGeneratorDto permissionGeneratorDto;

    public PermissionDocumentationDecorator(PermissionGeneratorDto permissionGeneratorDto) {
        this.permissionGeneratorDto = permissionGeneratorDto;
    }

    @Override
    public void decorate(Documentation documentation) {

        List<PermissionSection> sections  = new ArrayList<PermissionSection>();
        try {
            sections = loadPermissionSections();
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to generate permissions", e);
            return;
        }


        addTitle("Permissions",documentation);

        var defaultOffset = 2;
        var propertyOffset = 4;
        var listOffset = 6;

        var builder= createYmlBuilder();
        builder.addSection("permissions");
        builder.newLine();
        for (var section : sections) {
            if (section.hasTitle()) {
                builder.addComment(section.getModel().getTitle());
            }
            var model = section.getModel();
            builder.addSection(model.getName(),defaultOffset);
            if (section.hasDescription()) {
                builder.addProperty("description",model.getDescription(),propertyOffset);
            }
            if (section.hasVisibility()) {
                builder.addProperty("default",model.getVisibility().name().toLowerCase(),propertyOffset);
            }
            if (section.hasChildren()) {
                builder.addSection("children",propertyOffset);
                for (var child : section.getChildren()) {
                    builder.addListProperty(child.getName(),listOffset);
                }
            }
            builder.newLine();
        }
        var yml = builder.build();
        addYml(yml,documentation);

    }


    private List<PermissionSection> loadPermissionSections() throws IllegalAccessException, IOException {
        var models = permissionGeneratorDto.permissionModels();
        if (permissionGeneratorDto._class() != null) {
            var loaded = loadModels(permissionGeneratorDto._class());
            models.addAll(loaded);
        }
        var sections = createSections(models);
        return sortSectionsByGroup(sections);
    }

    private List<PermissionModel> loadModels(Class<?> _clazz) throws IllegalAccessException {
        var result = new ArrayList<PermissionModel>();
        for (var field : _clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(PermissionProperty.class)) {
                continue;
            }
            field.setAccessible(true);
            var annotation = field.getAnnotation(PermissionProperty.class);
            var value = (String) field.get(null);

            var model = new PermissionModel();
            model.setName(value);
            model.setDescription(annotation.description());
            model.setParentGroup(annotation.group());
            model.setParent(annotation.isParent());

            var permissionGroups = field.getAnnotationsByType(PermissionGroup.class);
            for (var permissionGroup : permissionGroups) {
                model.getGroups().add(permissionGroup.group());
            }

            if (field.isAnnotationPresent(PermissionTitle.class)) {
                var permissionTitle = field.getAnnotation(PermissionTitle.class);
                model.setTitle(permissionTitle.title());
            }

            result.add(model);
        }
        return result;
    }

    private List<PermissionSection> createSections(List<PermissionModel> models) throws IllegalAccessException {
        var result = new ArrayList<PermissionSection>();
        for (var model : models) {
            var children = new ArrayList<PermissionModel>();
            if (!model.isParent()) {
                result.add(new PermissionSection(model, children));
                continue;
            }
            var parentGroup = model.getParentGroup();
            if (parentGroup.equals(StringUtils.EMPTY_STRING)) {
                result.add(new PermissionSection(model, children));
                continue;
            }

            for (var child : models) {
                if (child.equals(model)) {
                    continue;
                }
                if (!child.getGroups().contains(parentGroup)) {
                    continue;
                }
                children.add(child);
            }
            result.add(new PermissionSection(model, children));
        }
        return result;
    }

    private List<PermissionSection> sortSectionsByGroup(List<PermissionSection> sections) throws IllegalAccessException {
        var result = new LinkedList<PermissionSection>();
        var sorted = new LinkedHashMap<String, List<PermissionSection>>();
        sorted.put("plugin", new LinkedList<PermissionSection>());
        for (var section : sections) {
            var group = StringUtils.EMPTY_STRING;
            if (section.getModel().isParent()) {
                group = section.getModel().getParentGroup();
            }
            if (section.hasGroup() && !section.getModel().isParent()) {
                group = section.getModel().getGroups().get(0);
            }
            if (StringUtils.nullOrEmpty(group)) {
                group = "plugin";
                section.getModel().getGroups().add(group);
            }

            if (!sorted.containsKey(group)) {
                sorted.put(group, new LinkedList<PermissionSection>());
            }

            sorted.get(group).add(section);
        }


        for (var entry : sorted.entrySet()) {
            var hasParent = entry.getValue().stream().filter(c -> c.getModel().isParent()).findFirst();
            if (hasParent.isEmpty()) {
                result.addAll(entry.getValue());
                continue;
            }

            var parent = hasParent.get();
            result.add(parent);
            entry.getValue().remove(parent);
            result.addAll(entry.getValue());
        }

        return result;
    }
}
