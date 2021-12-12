package jw.spigot_fluent_api.gui_v2.button;
import jw.spigot_fluent_api.gui_v2.InventoryUI;
import jw.spigot_fluent_api.gui_v2.enums.ButtonType;
import jw.spigot_fluent_api.gui_v2.events.ButtonUIEvent;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class ButtonUIBuilder<SELF extends ButtonUIBuilder<SELF, B>, B extends ButtonUI>
{
    protected B button;

    public ButtonUIBuilder()
    {
        button = createButton();
    }

    protected B createButton()
    {
       return (B)new ButtonUI();
    }


    protected SELF self()
    {
        return (SELF)this;
    }

    public SELF setLocation(int height,int width)
    {
        button.setLocation(height,width);
        return self();
    }
    public SELF setDescription(String ... description)
    {
        this.button.setDescription(new ArrayList(List.of(description)));
        return self();
    }

    public SELF setDescription(MessageBuilder messageBuilder)
    {
        this.button.setDescription(new ArrayList<>(List.of(messageBuilder.toString())));
        return self();
    }

    public SELF setTitle(String title)
    {
        this.button.setTitle(title);
        return self();
    }

    public SELF setTitle(MessageBuilder messageBuilder)
    {
        this.button.setTitle(messageBuilder.toString());
        return self();
    }

    public SELF setMaterial(Material material)
    {
        this.button.setMaterial(material);
        return self();
    }
    public SELF setOnClick(ButtonUIEvent onClick)
    {
        this.button.setOnClick(onClick);
        return self();
    }
    public SELF setButtonType(ButtonType buttonType)
    {
        this.button.setButtonType(buttonType);
        return self();
    }
    public SELF setPermissions(String ... permissions)
    {
        this.button.setPermissions(new ArrayList<>(List.of(permissions)));
        return self();
    }
    public SELF setSound(Sound sound)
    {
        this.button.setSound(sound);
        return self();
    }
    public SELF setActive(boolean isActive)
    {
        this.button.setActive(isActive);
        return self();
    }

    public B build()
    {
        return button;
    }

    public B buildAndAdd(InventoryUI inventoryUI)
    {
        var btn = build();
        inventoryUI.addButton(btn);
        return btn;
    }
}