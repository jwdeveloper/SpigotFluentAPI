package jw.fluent_api.spigot.documentation.implementation;

import jw.fluent_api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent_api.spigot.messages.FluentMessage;
import jw.fluent_api.spigot.permissions.api.PermissionGeneratorDto;
import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_api.spigot.permissions.api.PermissionSection;
import jw.fluent_api.spigot.permissions.api.annotations.PermissionGroup;
import jw.fluent_api.spigot.permissions.api.annotations.PermissionProperty;
import jw.fluent_api.spigot.permissions.api.annotations.PermissionTitle;
import jw.fluent_api.utilites.java.StringUtils;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

import java.io.IOException;
import java.util.*;

public class PermissionDocumentationRenderer extends DocumentationRenderer {


    private final PermissionGeneratorDto permissionGeneratorDto;

    public PermissionDocumentationRenderer(Class<?> _class, List<PermissionModel> models) {
        this(new PermissionGeneratorDto(_class, StringUtils.EMPTY_STRING, models));
    }

    public PermissionDocumentationRenderer(Class<?> _class) {
        this(new PermissionGeneratorDto(_class, StringUtils.EMPTY_STRING, new ArrayList<>()));
    }

    public PermissionDocumentationRenderer(PermissionGeneratorDto permissionGeneratorDto) {
        this.permissionGeneratorDto = permissionGeneratorDto;
    }

    @Override
    public String render() {
        try {
            return tryGenerate(permissionGeneratorDto);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to generate permissions", e);
        }
        return StringUtils.EMPTY_STRING;
    }

    private String tryGenerate(PermissionGeneratorDto permissionGeneratorDto) throws IllegalAccessException, IOException {
        var models = permissionGeneratorDto.permissionModels();
        if (permissionGeneratorDto._class() != null) {
            var loaded = loadModels(permissionGeneratorDto._class());
            models.addAll(loaded);
        }


        FluentLogger.LOGGER.success("Found", models.size(), "Permissions");
        var sections = createSections(models);
        sections = sortSectionsByGroup(sections);
        FluentLogger.LOGGER.success("Found", sections.stream().filter(PermissionSection::hasChildren).toList().size(), "Permission Sections");
        sections.forEach(e ->
        {
            if (e.hasChildren() && e.getModel().isParent()) {
                FluentLogger.LOGGER.success(e.getModel().getName(), e.getModel().getParentGroup());
            }
        });
        var content = createFileContent(sections);
        var path = permissionGeneratorDto.path() + "\\permissions.yml";
        // save(content, path);
        FluentLogger.LOGGER.success("Permission generated to path", path);
        return content;
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

    private String createFileContent(List<PermissionSection> sections) {
        renderTitle("Permissions");
        builder.text("permissions:").newLine();
        for (var section : sections) {
            if (section.hasTitle()) {
                smallTitle(section.getModel().getTitle());
            }
            var model = section.getModel();
            builder.space(defaultOffset).text(model.getName()).text(":").newLine();
            if (section.hasDescription()) {
                builder.space(propertyOffset).text("description:").space().text(model.getDescription()).newLine();
            }
            if (section.hasVisibility()) {
                builder.space(propertyOffset).text("default:").space().text(model.getVisibility().name().toLowerCase()).newLine();
            }
            if (section.hasChildren()) {
                builder.space(propertyOffset).text("children:").newLine();
                for (var child : section.getChildren()) {
                    builder.space(listOffset).text("-").space().text(child.getName()).newLine();
                }
            }
            builder.newLine();
        }
        return builder.build();
    }


}
