package jw.spigot_fluent_api.fluent_plugin.managers;

import jw.spigot_fluent_api.utilites.java.ClassTypeUtility;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.*;


public class TypeManager {
    @Getter
    private final List<Class<?>> classes;

    private final HashMap<Class<?>, List<Class<?>>> byInterfaceCatch;

    private final HashMap<Class<?>, List<Class<?>>> byParentCatch;

    private final HashMap<Class<? extends Annotation>, List<Class<?>>> byAnnotationCatch;

    private final HashMap<Package, List<Class<?>>> byPackageCatch;


    public TypeManager(List<Class<?>> classes) {
        this.classes = classes;
        byInterfaceCatch = new HashMap<>();
        byParentCatch = new HashMap<>();
        byPackageCatch = new HashMap<>();
        byAnnotationCatch = new HashMap<>();
    }

    public Collection<Class<?>> getByAnnotation(Class<? extends Annotation> annotation) {
        if (byAnnotationCatch.containsKey(annotation)) {
            return byAnnotationCatch.get(annotation);
        }
        var result = new ArrayList<Class<?>>();
        for(var _class : classes)
        {
            if(_class.isAnnotationPresent(annotation))
            {
                result.add(_class);
            }
        }

        return result;
    }

    public Collection<Class<?>> getByInterface(Class<?> _interface) {
        if (byInterfaceCatch.containsKey(_interface)) {
            return byInterfaceCatch.get(_interface);
        }
        var result = new ArrayList<Class<?>>();
        for(var _class : classes)
        {
            for(var _classInterface : _class.getInterfaces())
            {
                if(_classInterface.equals(_interface))
                {
                    result.add(_class);
                    break;
                }
            }
        }
        return result;
    }

    public Collection<Class<?>> getByParentClass(Class<?> parentClass)
    {
        if (byParentCatch.containsKey(parentClass)) {
            return byParentCatch.get(parentClass);
        }
        var result = new ArrayList<Class<?>>();
        for(var _class : classes)
        {
            if(ClassTypeUtility.isClassContainsType(_class, parentClass))
            {
                result.add(_class);
            }
        }
        return result;
    }

    public Collection<Class<?>> getByPackage(Package _package) {
        if (byPackageCatch.containsKey(_package)) {
            return byPackageCatch.get(_package);
        }
        var result = new ArrayList<Class<?>>();
        for(var _class : classes)
        {
            for(var _classInterface : _class.getInterfaces())
            {
                if(_classInterface.getPackage().equals(_package))
                {
                    result.add(_class);
                    break;
                }
            }
        }
        return result;
    }

}
