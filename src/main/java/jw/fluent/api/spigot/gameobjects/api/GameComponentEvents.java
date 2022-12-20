package jw.fluent.api.spigot.gameobjects.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface GameComponentEvents
{
    default void onPlayerInteraction(Player player, Location location) {
    }


    default void onRotation(int degree) {
    }

    default void onCreate() {

    }

    default void onDestroy() {
    }

    default void onLocationUpdated() {
    }
}
