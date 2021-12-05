package jw.spigot_fluent_api.gui.button;

import jw.spigot_fluent_api.gui.events.ButtonEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Button extends ItemStack {
    private Vector position = new Vector(-1, -1, 0);
    private ButtonEvent onClick = (player, button) -> {
    };
    private ButtonEvent onShiftClick = (player, button) -> {
    };
    private boolean isActive = true;
    private boolean isHighLighted = false;
    private String permission = "";
    private ButtonActionsEnum action = ButtonActionsEnum.EMPTY;
    private Object objectHolder;
    private Sound sound;

    public Button(Material material) {
        super(material);
        this.hideAttributes();
    }

    public Button(Material material, ButtonActionsEnum action) {
        this(material);
        this.action = action;
    }

    public Button(Material material, String title) {
        this(material, ButtonActionsEnum.CLICK);
        this.setName(title);
    }

    public Button(Material material, String title, ButtonActionsEnum action) {
        this(material, title);
        this.action = action;
    }

    public Button(Material material, String title, String description) {
        this(material, title);
        this.setDescription(description);
    }

    public Button(Material material, String name, ButtonEvent onClick) {
        this(material, name);
        this.onClick = onClick;
    }

    public Button(Material material, String name, int height, int width, ButtonEvent onClick) {
        this(material, name, onClick);
        setPosition(height, width);
    }

    public void hideAttributes() {
        ItemMeta meta = this.getItemMeta();
        if (meta == null)
            return;
        Arrays.asList(ItemFlag.values()).forEach(i -> meta.addItemFlags(i));
        this.setItemMeta(meta);
    }

    public boolean canPlayerUse(Player player) {
        if (this.permission != null && !player.hasPermission(permission)) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return false;
        }
        return true;
    }

    public void setHighlighted(boolean value) {
        ItemMeta meta = this.getItemMeta();
        isHighLighted = value;
        if (value)
            meta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        else
            meta.removeEnchant(Enchantment.ARROW_FIRE);
        this.setItemMeta(meta);
    }

    public void setCustomModelData(int id) {
        ItemMeta meta = this.getItemMeta();
        meta.setCustomModelData(id);
        this.setItemMeta(meta);
    }

    public void setName(String name) {
        ItemMeta meta = this.getItemMeta();
        if (meta == null)
            return;

        meta.setDisplayName(ChatColor.WHITE + name.replace("&", "§"));
        this.setItemMeta(meta);
    }


    public void displayHeldObject() {
        if (objectHolder == null)
            return;

        Class _class = objectHolder.getClass();
        List<String> description = new ArrayList<String>();

        description.add("Object name: " + objectHolder);
        for (Field field : _class.getFields()) {
            try {
                description.add(field.getName() + ": " + field.get(objectHolder));
            } catch (Exception e) {
                description.add(ChatColor.WHITE + field.getName() + ": " + ChatColor.DARK_RED + "Can not get value");
            }
        }
        this.setDescription(description);
    }

    public void setDescription(String... description) {
        this.setDescription(Arrays.asList(description));
    }

    public void setDescription(List<String> description) {
        if (description == null)
            return;
        ItemMeta meta = this.getItemMeta();
        if (meta == null)
            return;

        for (int i = 0; i < description.size(); i++) {
            description.set(i, ChatColor.WHITE + description.get(i));
        }

        meta.setLore(description);
        this.setItemMeta(meta);
    }

    public void addDescription(String... description) {
        if (description == null || description.length == 0)
            return;
        var meta = this.getItemMeta();
        var lore = meta.getLore();
        for (int i = 0; i < description.length; i++) {
            description[i]  =  ChatColor.WHITE + description[i];
        }
        lore.addAll(List.of(description));
        meta.setLore(lore);
        this.setItemMeta(meta);
    }
    public Material getMaterial() {
        return this.getType();
    }

    public void setMaterial(Material material) {
        this.setType(material);
    }

    public <T> T getHoldingObject() {
        return (T) this.objectHolder;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setPosition(int height, int width) {
        this.position = new Vector(width, height, 0);
    }

    public void setHeight(int height) {
        this.position.setY(height);
    }

    public void setWidth(int width) {
        this.position.setX(width);
    }

    public void setAction(ButtonActionsEnum action) {
        this.action = action;
    }

    public void setObjectHolder(Object objectHolder) {
        this.objectHolder = objectHolder;
    }

    public void setOnClick(ButtonEvent onClick) {
        this.onClick = onClick;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setOnShiftClick(ButtonEvent onShiftClick) {
        this.onShiftClick = onShiftClick;
    }

    public Sound getSound() {
        return this.sound;
    }

    public ButtonEvent getOnClick() {
        return onClick;
    }

    public ButtonEvent getOnShiftClick() {
        return onShiftClick;
    }

    public ButtonActionsEnum getAction() {
        return this.action;
    }

    public int getHeight() {
        return position.getBlockY();
    }

    public int getWidth() {
        return position.getBlockX();
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isHighlighted() {
        return this.isHighLighted;
    }

    public boolean hasSound() {
        return this.sound != null;
    }

    public boolean checkPermission(Player player) {
        return player.hasPermission(permission);
    }

}