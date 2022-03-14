package jw.spigot_fluent_api.desing_patterns.dependecy_injection;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerCatchConteiner {
    private final ConcurrentHashMap<UUID, HashMap<Class<?>, Object>> playerCatchedObjects;
    private Container container;

    public PlayerCatchConteiner() {
        this.playerCatchedObjects = new ConcurrentHashMap<>();
    }

    public void setContainer(Container container)
    {
          this.container = container;
    }

    public <T> T getPlayerCatchedObject(Class<T> injectionType, UUID uuid) {

        if(container == null)
            return null;

        if (!playerCatchedObjects.containsKey(uuid)) {
            playerCatchedObjects.put(uuid, new HashMap<>());
        }
        final var playerCatch = playerCatchedObjects.get(uuid);
        if (playerCatch.containsKey(injectionType)) {
            return (T) playerCatch.get(injectionType);
        }

        final var object = container.get(injectionType);
        playerCatch.put(injectionType, object);
        return (T) object;
    }
}
