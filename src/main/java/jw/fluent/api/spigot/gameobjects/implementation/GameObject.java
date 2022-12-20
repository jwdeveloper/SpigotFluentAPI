package jw.fluent.api.spigot.gameobjects.implementation;

import jw.fluent.api.spigot.gameobjects.api.GameComponent;
import jw.fluent.api.spigot.gameobjects.api.GameComponentEvents;
import jw.fluent.plugin.implementation.FluentApi;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.*;


public class GameObject implements GameComponent, GameComponentEvents {

    private final Map<Class<?>, Set<GameObject>> children;
    private final UUID id;

    @Getter
    @Setter
    private GameComponent parent;

    @Getter
    protected boolean active = true;

    @Getter
    protected boolean visible = true;

    @Getter
    protected String name;

    @Getter
    protected Location location;

    public GameObject() {
        id = UUID.randomUUID();
        children = new HashMap<>();
        name = getClass().getSimpleName() + "_" + id.toString();
    }

    public void rotate(int degree) {
        var l = getLocation();
        l.setPitch(degree);

        onRotation(degree);
        for (var childSet : children.values()) {
            for (var child : childSet) {
                child.rotate(degree);
            }
        }
    }

    public final void destroy() {
        for (var childSet : children.values()) {
            for (var child : childSet) {
                child.destroy();
            }
        }
        onDestroy();
        children.clear();
    }




    @Override
    public void setLocation(Location location) {
        this.location = location;
        onLocationUpdated();
        for (var childSet : children.values()) {
            for (var child : childSet) {
                child.setLocation(location.clone());
            }
        }
    }

    public final void setVisibility(boolean visible) {
        for (var childSet : children.values()) {
            for (var child : childSet) {
                child.setVisibility(visible);
            }
        }
        this.visible = visible;
    }

    public final void create(Location location) {
        this.location = location;
        onCreate();
        for (var childSet : children.values()) {

            for (var child : childSet) {
                var loc = child.getLocation() == null ? location.clone() : child.getLocation();
                child.create(loc);
            }
        }
    }

    @Override
    public final <T extends GameComponent> T addGameComponent(T gameComponent) {
        var class_ = gameComponent.getClass();
        gameComponent.setParent(this);

        if (!children.containsKey(class_)) {
            children.put(class_, new HashSet<>());
        }

        var child = children.get(class_);
        var object = (GameObject) gameComponent;
        if (child.contains(object)) {
            return gameComponent;
        }
        child.add(object);
        return gameComponent;
    }

    @Override
    public final <T extends GameComponent> T addGameComponent(Class<T> tClass) {
        try {
            var obj = tClass.newInstance();
            return addGameComponent(obj);
        } catch (Exception e) {
            FluentApi.logger().error("Unable to add GameObject " + tClass.getSimpleName(), e);
            return null;
        }
    }

    @Override
    public final void addGameComponent(GameComponent... gameComponents) {
        for (var component : gameComponents) {
            addGameComponent(component);
        }
    }

    @Override
    public final void addGameComponent(List<GameComponent> gameComponents) {
        addGameComponent(gameComponents.toArray(new GameComponent[0]));
    }

    @Override
    public final <T extends GameComponent> T getGameComponent(Class<T> _class) {

        var result = getGameComponents(_class);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public final <T extends GameComponent> List<T> getGameComponents(Class<T> _class) {
        if (!children.containsKey(_class)) {
            return List.of();
        }
        var gameComponent = children.get(_class);
        return (List<T>) gameComponent.stream().toList();
    }
}
