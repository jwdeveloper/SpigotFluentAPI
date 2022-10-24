package jw.fluent_api.spigot.gameobjects.api;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ModelRenderer extends GameObject {
    private ArmorStand armorStand;
    private ItemStack customModelData;

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void setCustomModel(ItemStack itemStack) {
        customModelData = itemStack;
        updateModel();
    }

    public void setCustomModel(Material model, int customModelId) {
        setCustomModel(new ItemStack(model, customModelId));
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
            armorStand.setHelmet(null);
            return;
        }
        armorStand.setHelmet(customModelData);
    }

    @Override
    public void onVisibilityChange(boolean state) {
        updateModel();
    }

    @Override
    public void onCreated() {
        if (getParent() == null)
            return;
        if (getLocation() == null) {
            return;
        }
        armorStand = getLocation().getWorld().spawn(getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setAI(false);
        armorStand.setMarker(true);
        armorStand.setNoDamageTicks(0);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.setRotation(0, 0);
        updateModel();
    }

    @Override
    public void onDestroy() {
        if(armorStand == null)
            return;

        armorStand.remove();
    }
}
