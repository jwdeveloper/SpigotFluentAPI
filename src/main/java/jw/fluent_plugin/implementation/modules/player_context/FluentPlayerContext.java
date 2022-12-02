package jw.fluent_plugin.implementation.modules.player_context;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.player_context.implementation.PlayerContainerBuilderImpl;
import jw.fluent_plugin.implementation.FluentApi;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FluentPlayerContext {
    private final ConcurrentHashMap<UUID, Container> playerContainers;
    private final FluentContainer mainContainer;
    private final List<RegistrationInfo> registrationInfos;

    public FluentPlayerContext(FluentContainer mainContainer, List<RegistrationInfo> registrationInfos) {
        this.mainContainer = mainContainer;
        this.registrationInfos = registrationInfos;
        playerContainers = new ConcurrentHashMap<>();
    }

    public <T> T find(Class<T> injectionType, Player player) {
        return find(injectionType, player.getUniqueId());
    }
    public <T> T find(Class<T> injectionType, UUID uuid) {

        if (!playerContainers.containsKey(uuid)) {
            try {
                playerContainers.put(uuid, CreateContainer());
            } catch (Exception e) {
                FluentApi.logger().error("Unable register container for player " + uuid.toString(), e);
                return null;
            }
        }
        final var container = playerContainers.get(uuid);
        return (T) container.find(injectionType);
    }

    public <T> T clear(Class<T> injectionType, Player player) {
        //TODO this
        return null;
    }

    private Container CreateContainer() throws Exception {
        return new PlayerContainerBuilderImpl()
                .setParentContainer(mainContainer)
                .configure(containerConfiguration ->
                {
                    containerConfiguration.addRegistration(registrationInfos);
                }).build();
    }
}
