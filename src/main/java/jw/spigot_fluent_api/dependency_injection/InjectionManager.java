package jw.spigot_fluent_api.dependency_injection;


import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.ClassTypeUtility;
import jw.spigot_fluent_api.utilites.disposing.Disposable;

import java.lang.annotation.Annotation;
import java.util.*;

public class InjectionManager {

    private static InjectionManager instance;
    private final InjectionContainer serviceContainer;
    private final HashMap<UUID, HashMap<Class<?>, Object>> playerObjects;

    private static InjectionManager instance() {
        if (instance == null) {
            instance = new InjectionManager();
        }
        return instance;
    }

    private InjectionManager() {
        serviceContainer = new InjectionContainer();
        playerObjects = new HashMap<>();
    }

    public static <T> List<T> getObjectsByParentType(Class<T> searchType) {
        List<T> result = new ArrayList<>();
        for (var type : instance().serviceContainer.getInjections().keySet()) {
            if (ClassTypeUtility.isClassContainsType(type, searchType)) {
                result.add(instance().serviceContainer.getObject(type));
            }
        }
        return result;
    }

    public static List<Object> getObjectsByAnnotation(Class<? extends Annotation> searchType) {
        List<Object> result = new ArrayList<>();
        instance().serviceContainer.getInjections().forEach((type, bean) ->
        {
            if (type.isAnnotationPresent(searchType)) {
                result.add(instance().serviceContainer.getObject(type));
            }
        });
        return result;
    }

    public static <T> List<T> getObjectsByInterface(Class<T> _interface) {
        List<T> result = new ArrayList<>();
        for (var type : instance().serviceContainer.getInjections().keySet()) {
            if (ClassTypeUtility.isClassContainsInterface(type, _interface)) {
                result.add(instance().serviceContainer.getObject(type));
            }
        }
        return result;
    }

    public static <T> T getObject(Class<T> tClass) {
        return instance().serviceContainer.getObject(tClass);
    }

    public static <T> T getObjectPlayer(Class<T> tClass, UUID uuid) {
        if (!instance().playerObjects.containsKey(uuid)) {
            instance().playerObjects.put(uuid, new HashMap<>());
        }
        HashMap<Class<?>, Object> objectHashMap = instance().playerObjects.get(uuid);
        if (!objectHashMap.containsKey(tClass)) {
            objectHashMap.put(tClass, getObject(tClass));
        }
        return (T) objectHashMap.get(tClass);
    }

    public static Set<Class<?>> getInjectedTypes() {
        return instance().serviceContainer.getInjections().keySet();
    }

    public static <T> void register(InjectionType serviceType, Class<T> type) {
        instance().serviceContainer.register(serviceType, type);
    }

    public static <T> void registerSingletone(Class<T> type) {
        instance().serviceContainer.register(InjectionType.SINGLETON, type);
    }

    public static <T> void registerTransient(Class<T> type) {
        instance().serviceContainer.register(InjectionType.TRANSIENT, type);
    }

    public static void dispose() {
        instance().playerObjects.clear();
        instance().serviceContainer.dispose();
    }

    public static void registerAll(List<Class<?>> classes)
    {
        var toInstantiate = new ArrayList<Class<?>>();
        for (Class<?> type : classes) {
            SpigotBean spigotBean = type.getAnnotation(SpigotBean.class);
            if (spigotBean == null)
                continue;

            register(spigotBean.injectionType(), type);

            if (!spigotBean.lazyLoad()) {
                toInstantiate.add(type);
            }
        }
        for (Class<?> type : toInstantiate) {
            getObject(type);
        }
    }
}
