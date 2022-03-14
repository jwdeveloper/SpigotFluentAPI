package jw.spigot_fluent_api.fluent_game_object;

import java.util.List;

public interface GameComponent {
    public  GameComponent getParent();

    GameComponent addGameComponent(GameComponent gameComponent);

    void addGameComponent(GameComponent... gameComponents);

    void addGameComponent(List<GameComponent> gameComponents);

    <T extends GameComponent> T getGameComponent(Class<T> _class);

    <T extends GameComponent> List<T> getGameComponents(Class<T> _class);
}
