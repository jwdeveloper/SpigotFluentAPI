package jw.spigot_fluent_api.desing_patterns.dependecy_injection;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.utilites.Messages;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

@Getter
public class InjectedClass {
    private LifeTime lifeTime;
    private Class<?> type;
    private Field[] injectedFields;

    private Object[] constructorPayLoad;
    private Type[] constructorTypes;
    private Constructor injectedConstructor;

    private Set<Class<?>> parentTypes;
    private Set<Class<?>> interfaces;

    private Object instnace;


    public InjectedClass(Class<?> type) {
        setType(type);
    }


    public Object getInstnace(HashMap<Class<?>, InjectedClass> injections) throws Exception {
        if (lifeTime == LifeTime.SINGLETON && instnace != null)
            return instnace;

        Object result = null;
        InjectedClass handler = null;

        if (hasInjectedConstructor()) {
            int i = 0;
            for (var parameter : constructorTypes) {
                if (!injections.containsKey(parameter)) {
                    throw new Exception(String.format(Messages.INJECTION_NOT_FOUND, parameter.getTypeName(), type.getSimpleName()));
                }
                handler = injections.get(parameter);
                constructorPayLoad[i] = handler.getInstnace(injections);
                i++;
            }
            result = injectedConstructor.newInstance(constructorPayLoad);
        } else {
            result = type.newInstance();
        }

        if (hasInjectedFields()) {
            for (var field : injectedFields) {
                if (!injections.containsKey(field.getType())) {
                    throw new Exception(String.format(Messages.INJECTION_FIELD_NOT_FOUND, field.getName(), type.getSimpleName()));
                }
                handler = injections.get(field.getType());
                field.set(result, handler.getInstnace(injections));
            }
        }

        instnace = result;
        return instnace;
    }


    public void setType(Class<?> type) {
        this.type = type;

        parentTypes = new HashSet<>();
        var subClass = type.getSuperclass();
        while (subClass != null && !subClass.equals(Object.class)) {
            parentTypes.add(subClass);
            subClass = subClass.getSuperclass();
        }

        interfaces = new HashSet<>();
        interfaces.addAll(Arrays.stream(type.getInterfaces()).toList());
        for (var parent : parentTypes)
            interfaces.addAll(Arrays.stream(parent.getInterfaces()).toList());
    }

    public void setLifeTime(LifeTime lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void setInjectedFields(List<Field> injectedFields) {
        this.injectedFields = injectedFields.toArray(new Field[0]);
        for (var field : this.injectedFields)
            field.setAccessible(true);
    }

    public void setInjectedConstructor(Constructor constructor) {
        if (constructor == null)
            return;

        this.injectedConstructor = constructor;
        constructor.setAccessible(true);
        constructorTypes = constructor.getParameterTypes();
        constructorPayLoad = new Object[constructorTypes.length];
    }

    public boolean hasInjectedFields() {
        return injectedFields != null;
    }

    public boolean hasInjectedConstructor() {
        return injectedConstructor != null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> _annotation) {
        return this.type.isAnnotationPresent(_annotation);
    }

    public boolean hasParentType(Class<?> parent) {
        return this.parentTypes.contains(parent);
    }

    public boolean hasInterface(Class<?> parent) {
        return this.interfaces.contains(parent);
    }
}
