package jw.fluent_api.spigot.gameobjects.implementation;

import jw.fluent_api.spigot.events.EventBase;
import jw.fluent_api.spigot.gameobjects.api.GameComponent;
import jw.fluent_api.spigot.gameobjects.api.GameObject;
import jw.fluent_plugin.implementation.FluentApi;
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
            gameObject.create(location.clone());
            return true;
        } catch (Exception e) {
            FluentApi.logger().error("unable to create Gameobject" + gameObject.getClass().getSimpleName(), e);
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
            FluentApi.logger().error("unable to destroy Gameobject" + gameObject.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (var gameObject : gameObjects) {
            try {
                gameObject.destory();
            } catch (Exception e) {
                FluentApi.logger().error("unable to destroy gameobject" + gameObject.getClass().getSimpleName(), e);
            }
        }
    }
}
