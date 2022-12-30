package jw.fluent.api.spigot.gameobjects.implementation;

import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Consumer;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class ArmorStandModel extends GameObject {

    @Getter
    private ArmorStand armorStand;

    @Getter
    private ItemStack itemStack;

    @Getter
    private String name;

    private UUID uuid;

    private NamespacedKey namespacedKey;

    private Color color;

    @Getter
    @Setter
    private Consumer<ArmorStandModel> onCreated = (e)->{};
    public void show()
    {
        if(armorStand == null)
            return;
        armorStand.setVisible(true);
        armorStand.setInvisible(false);
    }

    public void hide()
    {
        if(armorStand == null)
            return;
        armorStand.setVisible(false);
        armorStand.setInvisible(true);
    }

    public void setVisible(boolean isVisible)
    {
        if(isVisible)
            show();
        else
            hide();
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        updateModel();
    }

    public void setId(NamespacedKey namespacedKey, UUID uuid)
    {
        if(armorStand == null)
            return;

        if(namespacedKey == null || uuid == null)
            return;

        this.uuid = uuid;
        this.namespacedKey = namespacedKey;
        var container = armorStand.getPersistentDataContainer();
        container.set(namespacedKey, PersistentDataType.STRING, uuid.toString());
    }


    public void refresh()
    {
        var isSmall = false;
        if(armorStand != null)
        {
            isSmall = armorStand.isSmall();
            armorStand.remove();
        }


        armorStand = createArmorStand();
        armorStand.setHelmet(itemStack);
        armorStand.setSmall(isSmall);
        armorStand.teleport(location);
        setId(namespacedKey, uuid);
        updateModel();
    }


    public void setCustomModelId(int customModelId) {
        if(itemStack == null)
            return;
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        armorStand.getEquipment().setHelmet(itemStack);
    }

    public void setItemStack(Material material, int customModelId)
    {
        var itemStack = new ItemStack(material);
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        setItemStack(itemStack);
    }

    public void setName(String name)
    {
        this.name = name;
        updateModel();
    }

    @Override
    public void onRotation(int degree) {
        if (armorStand == null)
            return;

        location.setPitch(degree);
        armorStand.setHeadPose(new EulerAngle(0, Math.toRadians(degree), 0));
    }

    @Override
    public void onLocationUpdated() {
        if (armorStand == null) {
            return;
        }

         armorStand.teleport(location);
    }

    public void updateModel() {

        if (armorStand == null)
            return;
        if (!visible) {
            armorStand.getEquipment().setHelmet(null);
            return;
        }

        if(StringUtils.isNotNullOrEmpty(name))
        {
            armorStand.setCustomName(name);
            armorStand.setCustomNameVisible(true);
        }
        armorStand.getEquipment().setHelmet(itemStack);
    }

    public void onVisibilityChange(boolean state) {
        updateModel();
    }

    @Override
    public void onCreate() {
        if (getParent() == null)
            return;
        if (getLocation() == null) {
            return;
        }
        armorStand = createArmorStand();
        updateModel();

        onCreated.accept(this);
    }


    public void setColor(Color color)
    {
        this.color = color;

        if(itemStack == null)
        {

            return;
        }
        FluentLogger.LOGGER.log("A", color.toString());
        FluentLogger.LOGGER.log("B");
        var meta = itemStack.getItemMeta();
        if(meta instanceof LeatherArmorMeta horseMeta)
        {
            horseMeta.setColor(color);
            itemStack.setItemMeta(horseMeta);
            FluentLogger.LOGGER.log("C");
        }
        updateModel();
    }

    protected ArmorStand createArmorStand()
    {
        ArmorStand armorStand;
        armorStand = location.getWorld().spawn(getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setAI(false);
        armorStand.setMarker(true);
        armorStand.setNoDamageTicks(0);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.setRotation(0, 0);

        return armorStand;
    }


    @Override
    public void onDestroy() {
        if(armorStand == null)
            return;

        armorStand.remove();
    }
}
