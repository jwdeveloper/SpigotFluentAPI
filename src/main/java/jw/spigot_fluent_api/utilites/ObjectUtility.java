package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.examples.BoolenBindStrategy;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.examples.MaterialBindStrategy;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.examples.NumberBindStrategy;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.examples.TextBindStrategy;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                FluentPlugin.logError("Unable parse enum " + c + " from " + string);
            }
        }
        return null;
    }

    public static Object castStringToPrimitiveType(String value, Class<?> type)
    {
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

    public static void copyToObject(Object obj, Object obj2, Class type) {
        Field[] files = type.getFields();
        for (var file : files) {
            try {
                file.setAccessible(true);
                file.set(obj2, file.get(obj));
            } catch (Exception ignored) {
                FluentPlugin.logError("Unable copy object" + obj + " to " + obj2);
            }
        }
    }

    //Not implemented yet
    public static void copyObject(Object obj, Class type) {
        Field[] files = type.getFields();
        for (var file : files) {
            try {
                file.setAccessible(true);
                //file.set(obj2, file.get(obj));
            } catch (Exception ignored) {

            }
        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            FluentPlugin.logError("Unable to set value");
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
            FluentPlugin.logError("Unable to get value");
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
