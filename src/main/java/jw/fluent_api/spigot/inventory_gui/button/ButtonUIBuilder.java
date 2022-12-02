package jw.fluent_api.spigot.inventory_gui.button;
import jw.fluent_api.spigot.inventory_gui.InventoryUI;
import jw.fluent_api.spigot.inventory_gui.enums.ButtonType;
import jw.fluent_api.spigot.inventory_gui.events.ButtonUIEvent;
import jw.fluent_api.spigot.messages.message.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

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

    public SELF setDescription(List<String> description)
    {
        if(description == null)
        {
            return self();
        }

        button.setDescription(description);
        return self();
    }

    public SELF setDescription(String ... description)
    {
        setDescription(new ArrayList<>(List.of(description)));
        return self();
    }

    public SELF setDescription(MessageBuilder messageBuilder)
    {
        setDescription(messageBuilder.toArray());
        return self();
    }

    public SELF setTitle(String title)
    {
        button.setTitle(title);
        return self();
    }

    public SELF setTitle(MessageBuilder messageBuilder)
    {
        button.setTitle(messageBuilder.toString());
        return self();
    }

    public SELF setTitlePrimary(String title)
    {
       button.setTitle(new MessageBuilder().color(org.bukkit.ChatColor.AQUA).inBrackets(title).build());
        return self();
    }

    public SELF setTitleHeader(String title)
    {
        button.setTitle(new MessageBuilder().color(ChatColor.WHITE).inBrackets(title).build());
        return self();
    }

    public SELF setMaterial(Material material)
    {
        button.setMaterial(material);
        return self();
    }

    public SELF fromItemStack(ItemStack itemStack)
    {
        if(itemStack == null)
        {
            button.setMaterial(Material.AIR);
            return self();
        }
        setMaterial(itemStack.getType());
        setTitle(itemStack.getType().name());
        if(!itemStack.hasItemMeta())
        {
            return self();
        }
        var meta = itemStack.getItemMeta();
        setTitle(meta.getDisplayName());
        setDescription(meta.getLore());
        if(meta.hasCustomModelData())
        {
            button.setCustomMaterial(itemStack.getType(),meta.getCustomModelData());
        }
        return self();
    }

    public SELF setOnClick(ButtonUIEvent onClick)
    {
        button.setOnClick(onClick);
        return self();
    }
    public SELF setOnShiftClick(ButtonUIEvent onClick)
    {
        button.setOnShiftClick(onClick);
        return self();
    }
    public SELF setOnRightClick(ButtonUIEvent onClick)
    {
        button.setOnRightClick(onClick);
        return self();
    }
    public SELF setButtonType(ButtonType buttonType)
    {
        button.setButtonType(buttonType);
        return self();
    }
    public SELF setPermissions(String ... permissions)
    {
        button.setPermissions(new ArrayList<>(List.of(permissions)));
        return self();
    }
    public SELF setSound(Sound sound)
    {
        button.setSound(sound);
        return self();
    }
    public SELF setActive(boolean isActive)
    {
        button.setActive(isActive);
        return self();
    }

    public SELF setHighlighted()
    {
       button.setHighlighted(true);
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