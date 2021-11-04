package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;


public class ObjectUtility
{
    public static <T extends Enum<T>> T enumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex)
            {
                FluentPlugin.logError("Unable parse enum "+c+" from "+string);
            }
        }
        return null;
    }

    public static void copyToObject(Object obj, Object obj2, Class type) {
        Field[] files = type.getFields();
        for (var file : files) {
            try {
                file.setAccessible(true);
                file.set(obj2, file.get(obj));
            } catch (Exception ignored) {
                FluentPlugin.logError("Unable copy object"+obj+" to "+obj2);
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
        } catch (Exception e)
        {
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

        } catch (Exception e)
        {
            FluentPlugin.logError("Unable to get value");
        }
        return result;
    }
}
