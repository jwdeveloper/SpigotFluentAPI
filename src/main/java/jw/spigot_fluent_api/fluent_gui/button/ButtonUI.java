package jw.spigot_fluent_api.fluent_gui.button;

import jw.spigot_fluent_api.fluent_gui.enums.ButtonType;
import jw.spigot_fluent_api.fluent_gui.enums.PermissionType;
import jw.spigot_fluent_api.fluent_gui.events.ButtonUIEvent;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ButtonUI {
    @Setter(value = AccessLevel.NONE)
    private ItemStack itemStack;

    protected UUID uuid;

    protected String title;

    protected ArrayList<String> description;

    protected Material material;

    protected Object dataContext;

    protected boolean isActive = true;

    protected boolean isHighLighted = false;

    protected Vector location;

    protected ButtonType buttonType;

    protected Sound sound;

    protected ArrayList<String> permissions;

    protected PermissionType permissionType;

    private ButtonUIEvent onClick = (player, button) -> {
    };

    private ButtonUIEvent onShiftClick = (player, button) -> {
    };

    public ButtonUI() {
        buttonType = ButtonType.CLICKABLE;
        itemStack = new ItemStack(Material.DIRT);
        location = new Vector(0, 0, 0);
        description = new ArrayList<>();
        permissions = new ArrayList<>();
        permissionType = PermissionType.ONE_OF;
        hideAttributes();
    }

    public ButtonUI(Material material) {
        super();
        setMaterial(material);
    }

    public void setPermissions(List<String> permissions)
    {
        for(var p : permissions)
        {
            this.permissions.add(p);
        }
    }

    public void setPermissions(String ... permissions)
    {
        for(var p : permissions)
        {
            this.permissions.add(p);
        }
    }

    public void setMaterial(Material material) {
        itemStack.setType(material);
    }

    public void setCustomMaterial(Material material, int id)
    {
        itemStack.setType(material);
        final var meta = itemStack.getItemMeta();
        meta.setCustomModelData(id);
        itemStack.setItemMeta(meta);
    }

    public void setDescription(MessageBuilder messageBuilder) {
        setDescription(messageBuilder.toString());
    }

    public void setDescription(String... description) {
        setDescription(new ArrayList(Arrays.asList(description)));
    }

    public void setDescription(ArrayList<String> description) {
        for (int i = 0; i < description.size(); i++) {
            description.set(i, ChatColor.WHITE + description.get(i));
        }
        this.description = description;
        var meta = ensureMeta(itemStack);
        meta.setLore(description);
        itemStack.setItemMeta(meta);
    }

    public void setTitle(String title) {
        this.title = title;
        setDisplayedName(title);
    }

    public void setTitlePrimary(String title)
    {
      setTitle(new MessageBuilder().color(org.bukkit.ChatColor.AQUA).inBrackets(title).build());
    }

    public int getHeight() {
        return location.getBlockY();
    }

    public int getWidth() {
        return location.getBlockX();
    }

    public boolean hasSound() {
        return sound != null;
    }

    public void setLocation(int height, int width) {
        setLocation(new Vector(width, height, 0));
    }

    public void location(int height, int width) {
        setLocation(new Vector(width, height, 0));
    }

    public void click(Player player) {
        if (onClick == null)
            return;
        this.onClick.execute(player, this);
    }

    public <T> T getDataContext() {
        if (dataContext == null)
            return null;
        try {
            return (T) dataContext;
        } catch (Exception e) {
            FluentPlugin.logError("Can not cast DataContext value " + dataContext.toString() + " in button " + title);
        }
        return null;
    }

    private void setDisplayedName(String name) {
        var meta = ensureMeta(itemStack);
        if (meta == null)
            return;
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    public void setHighlighted(boolean value)
    {
        ItemMeta meta = ensureMeta(itemStack);
        if(meta == null)
            return;
        if (value)
            meta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        else
            meta.removeEnchant(Enchantment.ARROW_FIRE);
        isHighLighted = value;
        itemStack.setItemMeta(meta);
    }

    private void hideAttributes()
    {
        ItemMeta meta = ensureMeta(itemStack);
        if(meta == null)
            return;
        Arrays.asList(ItemFlag.values()).forEach(i -> meta.addItemFlags(i));
        itemStack.setItemMeta(meta);
    }

    public static ButtonUIFactory factory() {
        return new ButtonUIFactory();
    }
    public static ButtonUIBuilder builder() {
        return new ButtonUIBuilder();
    }

    private ItemMeta ensureMeta(ItemStack itemStack)
    {
       return  itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

}
