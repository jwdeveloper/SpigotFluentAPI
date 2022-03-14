package jw.spigot_fluent_api.desing_patterns.dependecy_injection;

import org.bukkit.entity.Player;

import java.util.UUID;

public class FluentInjection {

    static
    {
        instance = new FluentInjection();
    }

    private static FluentInjection instance;
    private Container container;
    private PlayerCatchConteiner catchConteiner;

    public FluentInjection()
    {
       container = new Container();
       catchConteiner = new PlayerCatchConteiner();
    }

    public static <T> T getInjection(Class<T> injectionType)
    {
        return instance.container.get(injectionType);
    }

    public static <T> T getPlayerInjection(Class<T> injectionType, UUID uuid)
    {
        return instance.catchConteiner.getPlayerCatchedObject(injectionType,uuid);
    }

    public static <T> T getPlayerInjection(Class<T> injectionType, Player player)
    {
        return instance.catchConteiner.getPlayerCatchedObject(injectionType,player.getUniqueId());
    }

    public static Container getInjectionContainer() {
        return instance.container;
    }

    public static void setInjectionContainer(Container container) {
        if(container == null)
            return;

        instance.container = container;
        instance.catchConteiner.setContainer(container);
    }
}
