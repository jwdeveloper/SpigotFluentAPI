package jw.fluent_api.spigot.gameobjects.api;

import jw.fluent_plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;


public class GameObject implements GameComponent {

    private GameComponent parent;
    private Map<Class<?>, Set<GameObject>> childs;
    private UUID id;
    protected boolean active = true;
    protected boolean visible = true;
    protected String name;
    protected Location location;
    protected Location globalLocation;
    protected Vector offset;

    public GameObject() {
        id = id.randomUUID();
        childs = new HashMap<>();
        name = getClass().getSimpleName()+"_"+id.toString();
        location = new Location(Bukkit.getWorlds().get(0),0,0,0);
        offset = new Vector(0,0,0);
    }


    public Location getGlobalLocation()
    {
        return location.clone().add(offset);
    }

    public String getName()
    {
        return name;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Vector getOffset() {
        return offset;
    }

    public void rotate(int degree)
    {
        var l = getLocation();
        l.setPitch(degree);

        onRotation(degree);
        for (var childSet : childs.values()) {
            for (var child : childSet) {
                child.rotate(degree);
            }
        }
    }

    public boolean handlePlayerInteraction(Player player, Location location, int range)
    {
        return false;
    }

    public void onPlayerInteraction(Player player, Location location)
    {

    }


    public void onRotation(int degree)
    {

    }

    public void setOffset(double x, double y, double z)
    {
       offset = new Vector(x,y,z);
       onLocationUpdated();
    }

    public void destory()
    {
        for (var childSet : childs.values()) {
            for (var child : childSet) {
                child.destory();
            }
        }
        FluentApi.logger().log("Gameobject destroyed",name);
        onDestroy();
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        onLocationUpdated();
        for (var childSet : childs.values()) {
            for (var child : childSet) {
                child.setLocation(location.clone().add(child.getOffset()));
            }
        }
    }
    public final void setVisibility(boolean visible) {
        for (var childSet : childs.values()) {
            for (var child : childSet) {
                child.setVisibility(visible);

            }
        }
        this.visible = visible;
    }
    public void create(Location location)
    {
        FluentApi.logger().log("Gameobject create",name);
        onCreated();
        for (var childSet : childs.values()) {

            for (var child : childSet) {

                child.onCreated();
                child.create(location);
            }
        }
        setLocation(location);
    }

    public final GameComponent getParent() {
        return parent;
    }

    public final void setParent(GameComponent parent) {
        this.parent = parent;
    }

    public void onVisibilityChange(boolean state)
    {
    }

    public void onCreated() {

    }

    public void onDestroy() {
    }

    public void onLocationUpdated()
    {
    }

    @Override
    public final <T extends GameComponent> T addGameComponent(T gameComponent) {
        var class_ = gameComponent.getClass();
        gameComponent.setParent(this);

        if (!childs.containsKey(class_)) {
            childs.put(class_, new HashSet<>());
        }

        var child = childs.get(class_);
        child.add((GameObject)gameComponent);
        ((GameObject) gameComponent).onCreated();
        return gameComponent;
    }

    @Override
    public <T extends GameComponent> T addGameComponent(Class<T> tClass)
    {
        try {
            var obj = tClass.newInstance();
            return  addGameComponent(obj);
        }
        catch (Exception e)
        {
            FluentApi.logger().error("Unable to add gameobject "+tClass.getSimpleName(),e);
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
