package jw.spigot_fluent_api.gui_v2.button;

import jw.spigot_fluent_api.gui_v2.enums.ButtonType;
import jw.spigot_fluent_api.gui_v2.events.ButtonUIEvent;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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

    private ButtonUIEvent onClick = (player, button) -> {
    };

    private ButtonUIEvent onShiftClick = (player, button) -> {
    };

    public ButtonUI()
    {
        buttonType = ButtonType.CLICKABLE;
        itemStack = new ItemStack(Material.DIRT);
        location = new Vector(0, 0, 0);
        description = new ArrayList<>();
        permissions = new ArrayList<>();
    }

    public ButtonUI(Material material) {
        super();
        setMaterial(material);
    }

    public void setMaterial(Material material) {
        itemStack.setType(material);
    }

    public void setDescription(MessageBuilder messageBuilder)
    {
        setDescription(messageBuilder.toString());
    }

    public void setDescription(ArrayList<String> description)
    {
        this.description = description;
    }

    public void setTitle(String title)
    {
        this.title = title;
        setDisplayedName(title);
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

    public void click(Player player)
    {
        if(onClick == null)
            return;
        this.onClick.Execute(player,this);
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
    private void setDisplayedName(String name)
    {
        var meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    private void setDescription(String description)
    {
        var meta = itemStack.getItemMeta();
        meta.setLore(List.of(description));
        itemStack.setItemMeta(meta);
    }

    public static ButtonUIBuilder builder()
    {
        return new ButtonUIBuilder();
    }

}
