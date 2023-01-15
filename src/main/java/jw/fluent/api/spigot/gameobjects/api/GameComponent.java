package jw.fluent.api.spigot.gameobjects.api;

import org.bukkit.Location;

import java.util.List;

public interface GameComponent {

    void setVisible(boolean visible);

    <T extends GameComponent> T addGameComponent(T gameComponent);

    <T extends GameComponent> T addGameComponent(Class<T> tClass);

    String getName();

    Location getLocation();
    void setLocation(Location location);
    GameComponent getParent();

    void setParent(GameComponent parent);

    void addGameComponent(GameComponent... gameComponents);

    void addGameComponent(List<GameComponent> gameComponents);

    <T extends GameComponent> T getGameComponent(Class<T> _class);

    <T extends GameComponent> List<T> getGameComponents(Class<T> _class);
    void destroy();
}
