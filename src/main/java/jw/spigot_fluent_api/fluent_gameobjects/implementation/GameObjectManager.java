package jw.spigot_fluent_api.fluent_gameobjects.implementation;

import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_gameobjects.api.GameComponent;
import jw.spigot_fluent_api.fluent_gameobjects.api.GameObject;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import org.bukkit.Location;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.ArrayList;
import java.util.List;

public class GameObjectManager extends EventBase {
    private static GameObjectManager instance;
    private final List<GameComponent> gameObjects = new ArrayList();

    private static GameObjectManager getInstance() {
        if (instance == null) {
            instance = new GameObjectManager();
        }
        return instance;
    }


    public static boolean register(GameObject gameObject, Location location) {
        if (getInstance().gameObjects.contains(gameObject)) {
            return false;
        }
        getInstance().gameObjects.add(gameObject);
        try {
            gameObject.create(location);
            return true;
        } catch (Exception e) {
            FluentLogger.error("unable to create gameobject" + gameObject.getClass().getSimpleName(), e);
        }
        return false;
    }

    public static void unregister(GameObject gameObject) {
        if (!getInstance().gameObjects.contains(gameObject)) {
            return;
        }
        getInstance().gameObjects.remove(gameObject);

        try {
            gameObject.destory();
        } catch (Exception e) {
            FluentLogger.error("unable to destroy gameobject" + gameObject.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (var gameObject : gameObjects) {
            try {
                gameObject.destory();
            } catch (Exception e) {
                FluentLogger.error("unable to destroy gameobject" + gameObject.getClass().getSimpleName(), e);
            }
        }
    }
}
