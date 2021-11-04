package jw.spigot_fluent_api.dependency_injection;


import jw.spigot_fluent_api.utilites.ClassTypeUtility;

import java.util.*;

public class InjectionManager {

    private InjectionContainer serviceContainer;
    private static InjectionManager instance;
    private HashMap<UUID,HashMap<Class<?>,Object>> playerObjects;

    public static InjectionManager Instance() {
        if (instance == null) {
            instance = new InjectionManager();
        }
        return instance;
    }
    private InjectionManager() {
        serviceContainer = new InjectionContainer();
        playerObjects = new HashMap<>();
    }

    public static <T> List<T> getObjectByType(Class<T> searchType)
    {
        List<T> result = new ArrayList<>();
        instance.serviceContainer.getInjections().forEach((type, b)->
        {
            if(ClassTypeUtility.isClassContainsType(type, searchType))
            {
                result.add(instance.serviceContainer.getObject(type));
            }
        });
      return result;
    }

    public static <T> T getObject(Class<T> tClass)
    {
        return instance.serviceContainer.getObject(tClass);
    }

    public static <T> T getObjectPlayer(Class<T> tClass,UUID uuid)
    {
        if(!instance.playerObjects.containsKey(uuid))
        {
             instance.playerObjects.put(uuid,new HashMap<>());
        }
        HashMap<Class<?>,Object> objectHashMap = instance.playerObjects.get(uuid);
        if(!objectHashMap.containsKey(tClass))
        {
         objectHashMap.put(tClass,getObject(tClass));
        }
        return (T)objectHashMap.get(tClass);
    }
    public static Set<Class<?>> getInjectedTypes()
    {
      return instance.serviceContainer.getInjections().keySet();
    }

    public static <T> void register(InjectionType serviceType, Class<T> type) {
        instance.serviceContainer.register(serviceType, type);
    }

    public static <T> void registerSingletone(Class<T> type) {
        instance.serviceContainer.register(InjectionType.SINGLETON, type);
    }

    public static <T> void registerTransient(Class<T> type) {
        instance.serviceContainer.register(InjectionType.TRANSIENT, type);
    }

    public static void registerAllFromPackage(Package packName) {
        var types = ClassTypeUtility.getClassesInPackage(packName.getName());
        var toInstantiate = new ArrayList<Class<?>>();

        for (Class<?> type : types) {
            SpigotBean serviceAttribute = type.getAnnotation(SpigotBean.class);
            if (serviceAttribute == null)
                continue;
            register(serviceAttribute.injectionType(), type);

            if(serviceAttribute.autoInit())
            {
                toInstantiate.add(type);
            }
        }
        for(Class<?> type:toInstantiate)
        {
            getObject(type);
        }
    }
}
