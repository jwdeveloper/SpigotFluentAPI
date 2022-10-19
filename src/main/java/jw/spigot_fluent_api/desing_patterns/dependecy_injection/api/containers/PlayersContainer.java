package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayersContainer extends Container
{
     <T> T find(Class<T> injectionType, UUID uuid);
     <T> T find(Class<T> injectionType, Player player);

     boolean register(RegistrationInfo registrationInfo, UUID uuid) throws Exception;
}
