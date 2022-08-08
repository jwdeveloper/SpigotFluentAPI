package jw.spigot_fluent_api.fluent_gameobjects.api;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public interface GameComponent {
    GameComponent getParent();

    void setVisibility(boolean visible);

      <T extends GameComponent> T addGameComponent(T gameComponent);

      String getName();

    Location getLocation();

     Vector getOffset();

    void setLocation(Location location);

    public Location getGlobalLocation();

    void setParent(GameComponent parent);

    void addGameComponent(GameComponent... gameComponents);

    void addGameComponent(List<GameComponent> gameComponents);

    <T extends GameComponent> T getGameComponent(Class<T> _class);

    <T extends GameComponent> List<T> getGameComponents(Class<T> _class);

    void destory();
}
