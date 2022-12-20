package jw.fluent.api.spigot.gameobjects.implementation;

import jw.fluent.api.spigot.gameobjects.api.GameComponent;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class GameObjectFactory
{
    public GameComponent CreateDefault(Location location)
    {
        var result = new GameObject();
        var modelRenderer = new ArmorStandModel();
        result.addGameComponent(modelRenderer);
        GameObjectManager.register(result, location);
        return result;
    }

    public GameComponent CreateCustomModel(Location location, ItemStack itemStack)
    {
        var result = CreateDefault(location);
        var renderer = result.getGameComponent(ArmorStandModel.class);
        renderer.setItemStack(itemStack);
        return result;
    }
}
