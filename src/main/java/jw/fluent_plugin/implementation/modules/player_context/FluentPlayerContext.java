package jw.fluent_plugin.implementation.modules.player_context;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.ContainerEvents;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FluentPlayerContext
{
    private final ConcurrentHashMap<UUID, Container> playerContainers;
    private final FluentContainer mainContainer;
    private final List<RegistrationInfo> registrationInfos;

    public FluentPlayerContext(FluentContainer mainContainer, List<RegistrationInfo> registrationInfos)
    {
        this.mainContainer = mainContainer;
        this.registrationInfos = registrationInfos;
        playerContainers = new ConcurrentHashMap<>();
    }

    public <T> T find(Class<T> injectionType, Player player) {
        return find(injectionType, player.getUniqueId());
    }
    public <T> T find(Class<T> injectionType, UUID uuid) {

        if (mainContainer == null)
            return null;

        if (!playerContainers.containsKey(uuid)) {
            try {
                playerContainers.put(uuid, CreateContainer());
            } catch (Exception e) {
                FluentApi.logger().error("Unable register container for player "+uuid.toString(),e);
                return null;
            }
        }
        final var container = playerContainers.get(uuid);
        return (T) container.find(injectionType);
    }

    public <T> T clear(Class<T> injectionType, Player player)
    {
        //TODO this
        return null;
    }

    private Container CreateContainer() throws Exception {
        var builder = new PlayerContainerBuilderImpl();
        builder.configure(containerConfiguration ->
        {
            containerConfiguration.onInjection(onInjectionEvent ->
            {
                for(var i : onInjectionEvent.injectionInfoMap().entrySet())
                {
                    FluentLogger.LOGGER.log("Injectia gracza ", i.getKey().getSimpleName());
                }

                FluentLogger.LOGGER.log("U gracza nie znalziono ", onInjectionEvent.input());
                if(onInjectionEvent.result() == null)
                {
                    FluentLogger.LOGGER.log("W maine szykamy ", onInjectionEvent.input());
                    var res = mainContainer.find(onInjectionEvent.input());
                    FluentLogger.LOGGER.log("Result", res);
                    return res;
                }
                return onInjectionEvent.result();
            });
            containerConfiguration.addRegistration(registrationInfos);
        });
        return builder.buildDefault();
    }
}
