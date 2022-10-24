package jw.fluent_api.spigot.gameobjects.implementation;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.spigot.events.EventBase;
import jw.fluent_api.spigot.gameobjects.api.GameComponent;
import jw.fluent_api.spigot.gameobjects.api.GameObject;
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
            OldLogger.error("unable to create gameobject" + gameObject.getClass().getSimpleName(), e);
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
            OldLogger.error("unable to destroy gameobject" + gameObject.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (var gameObject : gameObjects) {
            try {
                gameObject.destory();
            } catch (Exception e) {
                OldLogger.error("unable to destroy gameobject" + gameObject.getClass().getSimpleName(), e);
            }
        }
    }
}
