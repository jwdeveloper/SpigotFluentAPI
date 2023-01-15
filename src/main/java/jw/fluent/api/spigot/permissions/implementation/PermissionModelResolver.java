package jw.fluent.api.spigot.permissions.implementation;

import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionGroup;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionProperty;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermissionModelResolver
{


    public List<PermissionModel> merge(PermissionModel root, List<PermissionModel> models)
    {
          var result = new ArrayList<PermissionModel>();
          for(var model : models)
          {
              if(model.getName().equals(root.getName()))
              {
                  continue;
              }
              var duplication = findParent(root, model);
              if(duplication.isEmpty())
              {
                  root.addChild(model);
                  continue;
              }
              var parent = duplication.get();
              var isChildDuplciated = parent.getChildren().stream().filter(s -> s.getName().equals(model.getName())).findFirst();
              if(isChildDuplciated.isEmpty())
              {
                  parent.addChild(model);
                  continue;
              }
              var duplicated  = isChildDuplciated.get();
              merge(duplicated, model.getChildren());
          }
          result.add(root);
          return result;
    }

    public Optional<PermissionModel> findParent(PermissionModel target, PermissionModel model)
    {
        if(model.getParent() == null)
        {
            return Optional.empty();
        }

        if(target.getName().equals(model.getParent().getName()))
        {
            return Optional.of(target);
        }
        if(!target.hasChildren())
        {
            return Optional.empty();
        }
        for(var child : target.getChildren())
        {
            var result = findParent(child, model);
            if(result.isPresent())
            {
                return result;
            }
        }
        return Optional.empty();
    }

    public PermissionModel createModels(Class<?> clazz) throws IllegalAccessException {
        return getPermissionModelFromClass(clazz);
    }


    private PermissionModel getPermissionModelFromClass(Class<?> _clazz) throws IllegalAccessException {
        var root = new PermissionModel();
        if(!_clazz.isAnnotationPresent(PermissionGroup.class))
        {
            root.setName("ANNOTATION PermissionGroup NOT PRESENT");
            return root;
        }
        var annotation = _clazz.getAnnotation(PermissionGroup.class);
        root.setTitle(annotation.title());
        root.setName(annotation.group());
        for (var field : _clazz.getDeclaredFields())
        {
            var model = getPermissionModelFromField(field);
            root.addChild(model);
        }
        for (var clazz : _clazz.getDeclaredClasses())
        {
            var model = getPermissionModelFromClass(clazz);
            root.addChild(model);
        }
        return root;
    }
    private PermissionModel getPermissionModelFromField(Field field) throws IllegalAccessException {
        var result = new PermissionModel();
        field.setAccessible(true);
        var name = (String) field.get(null);
        result.setName(name);
        if (!field.isAnnotationPresent(PermissionProperty.class))
        {
            return result;
        }
        var annotation = field.getAnnotation(PermissionProperty.class);
        result.setDescription(annotation.description());
        field.setAccessible(false);
        return result;
    }
}
