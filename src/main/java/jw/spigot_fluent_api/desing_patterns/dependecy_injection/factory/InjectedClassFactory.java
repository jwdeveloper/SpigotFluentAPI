package jw.spigot_fluent_api.desing_patterns.dependecy_injection.factory;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.InjectedClass;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.utilites.Messages;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InjectedClassFactory {
    public static InjectedClass getFromClass(Class _class, LifeTime lifeTime) throws Exception {
        if (Modifier.isAbstract(_class.getModifiers())) {
            throw new Exception("Abstract class can't be register to Injection "+_class.getName());
        }
        if (Modifier.isInterface(_class.getModifiers())) {
            throw new Exception("Interface can't be register to Injection");
        }

        var result = new InjectedClass(_class);
        var fields = getInjectedFields(_class);
        var constructorOptional = getInjectedConstructor(_class);

        if (constructorOptional.isPresent()) {
            var constructor = constructorOptional.get();
            fields = removeRepeatedInjections(constructor, fields);
            result.setInjectedConstructor(constructor);
        }

        result.setType(_class);
        result.setLifeTime(lifeTime);
        result.setInjectedFields(fields);
        return result;
    }


    private static List<Field> getInjectedFields(Class _class) {
        var fields = _class.getDeclaredFields();
        var injectedFields = new ArrayList<Field>(fields.length);
        for (var field : fields) {
            if (!field.isAnnotationPresent(Inject.class))
                continue;

            if (field.getType().equals(_class)) {
                throw new StackOverflowError(String.format(Messages.INFINITE_LOOP, field.getName(), _class.getSimpleName()));
            }
            injectedFields.add(field);
        }
        return injectedFields;
    }

    private static Optional<Constructor> getInjectedConstructor(Class _class) {
        var consturctors = _class.getConstructors();
        for (var consturctor : consturctors) {
            if (!consturctor.isAnnotationPresent(Inject.class))
                continue;

            return Optional.of(consturctor);
        }
        return Optional.empty();
    }


    private static List<Field> removeRepeatedInjections(Constructor constructor, List<Field> fields) {
        if (fields.isEmpty())
            return fields;

        for (var parameter : constructor.getParameterTypes()) {
            var fieldContainsType = fields.stream().filter(c -> c.getType().equals(parameter)).findAny();
            if (fieldContainsType.isEmpty())
                continue;

            fields.remove(fieldContainsType.get());
        }
        return fields;
    }


}
