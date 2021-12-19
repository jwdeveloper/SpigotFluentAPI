package jw.spigot_fluent_api.gui_legacy.button;

import jw.spigot_fluent_api.gui_legacy.events.ButtonEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;

public class ButtonBuilder<SELF extends ButtonBuilder<SELF>>
{

    protected Button button;

    public ButtonBuilder()
    {
        button = new Button(Material.DIRT,ButtonActionsEnum.CLICK);
    }
    protected SELF self()
    {
        return (SELF)this;
    }
    public SELF setPosition(int height,int width)
    {
        this.button.setPosition(height,width);
        return self();
    }
    public SELF setDescription(String ... description)
    {
        this.button.setDescription(description);
        return self();
    }
    public SELF setName(String name)
    {
        this.button.setName(name);
        return self();
    }
    public SELF setBoldName(String name)
    {
        this.button.setName(ChatColor.BOLD+name);
        return self();
    }
    public SELF setMaterial(Material material)
    {
        this.button.setMaterial(material);
        return self();
    }
    public SELF setOnClick(ButtonEvent onClick)
    {
        this.button.setOnClick(onClick);
        return self();
    }
    public SELF setAcction(ButtonActionsEnum actionsEnum)
    {
        this.button.setAction(actionsEnum);
        return self();
    }
    public SELF setHighlighted(boolean highlighted)
    {
        this.button.setHighlighted(highlighted);
        return self();
    }
    public SELF setPermission(String permission)
    {
        this.button.setPermission(permission);
        return self();
    }
    public SELF setClickSound(Sound sound)
    {
         this.button.setSound(sound);
        return self();
    }
    public SELF setActive(boolean isActive)
    {
        this.button.setActive(isActive);
        return self();
    }

    public Button build()
    {
        return button;
    }
}
