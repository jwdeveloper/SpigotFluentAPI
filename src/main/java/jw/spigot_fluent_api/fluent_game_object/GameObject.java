package jw.spigot_fluent_api.fluent_game_object;

import java.util.*;

public class GameObject implements GameComponent {

    private GameComponent parent;
    private HashMap<Class<?>, Set<GameObject>> childs;
    private UUID uuid;

    public GameObject() {
        uuid = uuid.randomUUID();
        childs = new HashMap<>();
    }

    public final GameComponent getParent() {
        return parent;
    }

    public final void setParent(GameComponent parent) {
        this.parent = parent;
    }


    @Override
    public final GameComponent addGameComponent(GameComponent gameComponent) {
        var class_ = gameComponent.getClass();
        var gameObject = (GameObject) gameComponent;
        gameObject.setParent(this);

        if (!childs.containsKey(class_)) {
            childs.put(class_, new HashSet<>());
        }

        var child = childs.get(class_);
        child.add(gameObject);
        return gameObject;
    }

    @Override
    public final void addGameComponent(GameComponent... gameComponents) {
        for (var component : gameComponents) {
            addGameComponent(component);
        }
    }

    @Override
    public final void addGameComponent(List<GameComponent> gameComponents) {
        addGameComponent(gameComponents.toArray(new GameComponent[gameComponents.size()]));
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
        if (!childs.containsKey(_class)) {
            return List.of();
        }
        var gameComponent = childs.get(_class);
        return (List<T>) gameComponent.stream().toList();
    }
}
