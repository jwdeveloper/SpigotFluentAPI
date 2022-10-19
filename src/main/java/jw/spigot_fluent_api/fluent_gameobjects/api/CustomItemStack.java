package jw.spigot_fluent_api.fluent_gameobjects.api;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItemStack
{
    protected abstract NamespacedKey getNameSpaceKey();

    protected abstract Material getMaterial();

    public abstract ItemStack getItemStack();

    public abstract ItemStack getItemStack(int customModelId);

    public abstract boolean isValid(ItemStack itemStack);
}
