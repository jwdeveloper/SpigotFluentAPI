package jw.fluent_api.desing_patterns.dependecy_injection.implementation.factory;

import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.utilites.java.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InjectionInfoFactoryImpl implements InjectionInfoFactory {

    public Pair<Class<?>,InjectionInfo> create(RegistrationInfo info) throws Exception {
        return switch (info.registrationType()) {
            case InterfaceAndIml -> InterfaceAndImlStrategy(info);
            case OnlyImpl -> OnlyImplStrategy(info);
            case InterfaceAndProvider -> InterfaceAndProviderStrategy(info);
        };

    }

    private Pair<Class<?>,InjectionInfo> OnlyImplStrategy(RegistrationInfo info) throws Exception {
        var impl = info.implementation();
        if (Modifier.isAbstract(impl.getModifiers())) {
            throw new Exception("Abstract class can't be register to Injection " + impl.getName());
        }
        if (Modifier.isInterface(impl.getModifiers())) {
            throw new Exception("Implementation must be class, not Interface");
        }

        var result = new InjectionInfo();
        var constructor = getConstructor(impl);
        var extentedTypes = getExtentedTypes(impl);
        var implementedTypes = getImplementedTypes(impl, extentedTypes);
        var annotations = getAnnotations(impl,extentedTypes);
        result.setSuperClasses(extentedTypes);
        result.setInterfaces(implementedTypes);
        result.setAnnotations(annotations);

        result.setInjectedConstructor(constructor);
        result.setConstructorTypes(constructor.getParameterTypes());
        result.setRegistrationInfo(info);
        result.setInjectionKeyType(impl);
        return new Pair<>(impl,result);
    }

    private Pair<Class<?>,InjectionInfo> InterfaceAndImlStrategy(RegistrationInfo info) throws Exception {
        var impl = info.implementation();
        var _interface = info._interface();
        if (Modifier.isAbstract(impl.getModifiers())) {
            throw new Exception("Abstract class can't be register to Injection " + impl.getName());
        }
        if (Modifier.isInterface(impl.getModifiers())) {
            throw new Exception("Implementation must be class, not Interface");
        }

        var result = new InjectionInfo();
        var constructor = getConstructor(impl);
        var extentedTypes = getExtentedTypes(impl);
        var implementedTypes = getImplementedTypes(impl, extentedTypes);
        var annotations = getAnnotations(impl,extentedTypes);
        result.setSuperClasses(extentedTypes);
        result.setInterfaces(implementedTypes);
        result.setAnnotations(annotations);

        result.setInjectedConstructor(constructor);
        result.setConstructorTypes(constructor.getParameterTypes());
        result.setRegistrationInfo(info);
        result.setInjectionKeyType(_interface);
        return new Pair<>(_interface,result);
    }


    private Pair<Class<?>,InjectionInfo> InterfaceAndProviderStrategy(RegistrationInfo info) {
        var _interface = info._interface();
        var result = new InjectionInfo();
        result.setRegistrationInfo(info);
        result.setInjectionKeyType(_interface);
        return new Pair<>(_interface,result);
    }


    private static Set<Class<?>> getImplementedTypes(Class<?> type, Set<Class<?>> parentTypes) {
        var interfaces = new HashSet<>(Arrays.stream(type.getInterfaces()).toList());
        for (var parent : parentTypes) {
            interfaces.addAll(Arrays.stream(parent.getInterfaces()).toList());
        }
        return interfaces;
    }

    private static Set<Class<? extends Annotation>> getAnnotations(Class<?> type, Set<Class<?>> parentTypes)
    {
        var annotations = new HashSet<Class<? extends Annotation>>();
        for(var annotation : type.getAnnotations()) {
            annotations.add(annotation.annotationType());
        }

        for (var parent : parentTypes) {
            for(var annotation : parent.getAnnotations())
            {
                annotations.add(annotation.getClass());
            }
        }
        return annotations;
    }


    private static Set<Class<?>> getExtentedTypes(Class<?> type) {
        var superClassTypes = new HashSet<Class<?>>();
        var subClass = type.getSuperclass();
        while (subClass != null && !subClass.equals(Object.class)) {
            superClassTypes.add(subClass);
            subClass = subClass.getSuperclass();
        }
        return superClassTypes;
    }

    private static Constructor getConstructor(Class<?> _class) throws Exception {
        var consturctors = _class.getConstructors();
        if (consturctors.length == 1) {
            return consturctors[0];
        }

        for (var consturctor : consturctors) {
            if (!consturctor.isAnnotationPresent(Inject.class))
                continue;

            return consturctor;
        }
        throw new Exception("You need to use addnotatin Inject.class while there is more then one constructor");
    }

}
