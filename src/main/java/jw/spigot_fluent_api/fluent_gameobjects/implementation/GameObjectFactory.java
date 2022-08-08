package jw.spigot_fluent_api.fluent_gameobjects.implementation;

import jw.spigot_fluent_api.fluent_gameobjects.api.GameComponent;
import jw.spigot_fluent_api.fluent_gameobjects.api.GameObject;
import jw.spigot_fluent_api.fluent_gameobjects.api.ModelRenderer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class GameObjectFactory
{
    public GameComponent CreateDefault(Location location)
    {
        var result = new GameObject();
        var modelRenderer = new ModelRenderer();
        result.addGameComponent(modelRenderer);
        GameObjectManager.register(result, location);
        return result;
    }

    public GameComponent CreateCustomModel(Location location, ItemStack itemStack)
    {
        var result = CreateDefault(location);
        var renderer = result.getGameComponent(ModelRenderer.class);
        renderer.setCustomModel(itemStack);
        return result;
    }
}
