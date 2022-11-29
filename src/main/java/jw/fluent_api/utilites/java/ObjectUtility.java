package jw.fluent_api.utilites.java;

import jw.fluent_plugin.implementation.FluentApi;
import org.bukkit.Material;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


public class ObjectUtility {

    private static final Set<Class<?>> WRAPPER_TYPES = new HashSet(Arrays.asList(
            Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));

    public static boolean isPrimitiveType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    public static <T extends Enum<T>> T enumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                FluentApi.logger().error("Unable parse enum " + c + " from " + string, ex);
            }
        }
        return null;
    }

    public static Object castStringToPrimitiveType(String value, Class<?> type) {
        return switch (type.getName()) {
            case "java.lang.String" -> value;
            case "org.bukkit.Material" -> Material.valueOf(value);
            case "int", "java.lang.Number", "java.lang.Integer" -> Integer.parseInt(value);
            case "float" -> Float.parseFloat(value);
            case "double" -> Double.parseDouble(value);
            case "boolean", "java.lang.Boolean" -> Boolean.parseBoolean(value);
            default -> null;
        };
    }

    public static Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public static boolean copyToObject(Object obj, Object desination, Class type) {
        for (var field : type.getDeclaredFields()) {
            try {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                field.set(desination, field.get(obj));
            } catch (Exception ignored) {
                FluentApi.logger().error("Unable copy object" + obj + " to " + desination, ignored);
                return false;
            }
        }
        return true;
    }

    public static <T> boolean copyToObjectDeep(T obj, T destination) {
        if (!obj.getClass().equals(destination.getClass())) {
            FluentApi.logger().error("Unable deep copy object" + obj.getClass() + " to " + destination.getClass() + " this are different classes");
            return false;
        }

        var currentClass = obj.getClass();
        while (true) {
            var result = copyToObject(obj, destination, currentClass);
            if (!result) {
                break;
            }
            if (currentClass.getSuperclass().equals(Object.class)) {
                break;
            }
            currentClass = currentClass.getSuperclass();
        }
        return true;
    }

    public static Object copyObjectDeep(Object obj, Class type) throws InstantiationException, IllegalAccessException {
        var result = type.newInstance();
        if (!copyToObjectDeep(obj, result)) {
            return null;
        }
        return result;
    }

    public static Object copyObject(Object obj, Class type) throws InstantiationException, IllegalAccessException {
        var result = type.newInstance();
        if (!copyToObject(obj, result, type)) {
            return null;
        }
        return result;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            FluentApi.logger().error("Unable to set value", e);
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            result = field.get(obj);
            field.setAccessible(false);

        } catch (Exception e) {
            FluentApi.logger().error("Unable to get value", e);
        }
        return result;
    }

    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        var tempClass = type;
        while (tempClass != Object.class) {
            for (final Method method : tempClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    // Annotation annotInstance = method.getAnnotation(annotation);
                    methods.add(method);
                }
            }
            tempClass = tempClass.getSuperclass();
        }
        return methods;
    }
}
