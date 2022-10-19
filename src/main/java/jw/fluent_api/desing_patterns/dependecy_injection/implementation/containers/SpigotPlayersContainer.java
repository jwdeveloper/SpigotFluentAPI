package jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.PlayersContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SpigotPlayersContainer implements PlayersContainer {
    private final ConcurrentHashMap<UUID, Container> playerContainers;
    private final Container mainContainer;

    public SpigotPlayersContainer(Container container, ConcurrentHashMap<UUID, Container> playerContainers) {
        this.mainContainer = container;
        this.playerContainers = playerContainers;
        mainContainer.clone();
    }

    public <T> T find(Class<T> injectionType, UUID uuid) {

        if (mainContainer == null)
            return null;

        if (!playerContainers.containsKey(uuid)) {
            var clonedContainer = mainContainer.clone();
            playerContainers.put(uuid, clonedContainer);
        }
        final var container = playerContainers.get(uuid);
        return (T) container.find(injectionType);
    }

    @Override
    public <T> T find(Class<T> injectionType, Player player) {
        return find(injectionType, player.getUniqueId());
    }

    @Override
    public boolean register(RegistrationInfo registrationInfo) throws Exception {
        for (var set : playerContainers.entrySet()) {
            var result = set.getValue().register(registrationInfo);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean register(RegistrationInfo registrationInfo, UUID uuid) throws Exception {
        if (mainContainer == null)
            return false;
        if (!playerContainers.containsKey(uuid)) {
            var clonedContainer = mainContainer.clone();
            playerContainers.put(uuid, clonedContainer);
        }

        var container = playerContainers.get(uuid);
        return container.register(registrationInfo);
    }

    @Override
    public Object find(Class<?> type) {
        List<Object> result = new ArrayList<>();
        for (var set : playerContainers.entrySet()) {
            var instance = set.getValue().find(type);
            result.add(instance);
        }
        return result;
    }

    @Override
    public Container clone() {
        var clonedContainers = new ConcurrentHashMap<UUID, Container>();
        for (var playerContainer : playerContainers.entrySet()) {
            clonedContainers.put(playerContainer.getKey(), playerContainer.getValue().clone());
        }
        return new SpigotPlayersContainer(mainContainer, clonedContainers);
    }
}
