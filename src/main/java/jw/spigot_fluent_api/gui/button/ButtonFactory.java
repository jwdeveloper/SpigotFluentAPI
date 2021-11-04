package jw.spigot_fluent_api.gui.button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public  class ButtonFactory {


    public static Button fromItemStack(ItemStack itemStack) {
        Button Button = new Button(itemStack.getType(), itemStack.getType().name());
        Button.setData(itemStack.getData());
        Button.setItemMeta(itemStack.getItemMeta());
        Button.setAmount(itemStack.getAmount());
        return Button;
    }
    public static ItemStack toItemStack(Button Button) {
        ItemStack itemStack = new ItemStack(Button.getType(), Button.getAmount());
        itemStack.setAmount(Button.getAmount());
        itemStack.setData(Button.getData());
        itemStack.setItemMeta(Button.getItemMeta());
        return itemStack;
    }
    public static ArrayList<Button> fromStringArray(ArrayList<String> list) {

        ArrayList<Button> result = new ArrayList<>();

        list.forEach(l ->
        {
            result.add(new Button(Material.PAPER, l));
        });
        return result;
    }

    public static Button getBackground(Material material) {
        Button gui_icon = new Button(material);
        gui_icon.setName(" ");
        gui_icon.setAction(ButtonActionsEnum.BACKGROUND);
        return gui_icon;
    }

    public static Button exitButton() {
        Button itemstack = new Button(Material.TIPPED_ARROW, "Back", ButtonActionsEnum.EXIT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();

        PotionData data = new PotionData(PotionType.STRENGTH, false, false);
        meta.setBasePotionData(data);

        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static Button emptyButton() {
        return new Button(Material.SUNFLOWER, " ", ButtonActionsEnum.EMPTY);
    }

    public static Button deleteButton() {
        return new Button(Material.BARRIER, "Delete", ButtonActionsEnum.DELETE);
    }

    public static Button insertButton() {
        return new Button(Material.LEGACY_BOOK_AND_QUILL, "Insert", ButtonActionsEnum.INSERT);
    }
    public static Button editButton() {
        return new Button(Material.BOOKSHELF, "Edit", ButtonActionsEnum.EDIT);
    }

    public static Button copyButton() {
        return new Button(Material.SLIME_SPAWN_EGG, "Copy", ButtonActionsEnum.COPY);
    }

    public static Button searchButton() {
        return new Button(Material.SPYGLASS, "Search", ButtonActionsEnum.SEARCH);
    }
    public static Button cancelButton() {
        return new Button(Material.WITHER_SKELETON_SKULL, "Cancel", ButtonActionsEnum.CANCEL);
    }
    public static Button deleteALLButton() {
        return new Button(Material.TNT, "Delete all", ButtonActionsEnum.DELETE);
    }

    public static Button nextButton(PotionType color) {
        Button itemstack = new Button(Material.TIPPED_ARROW, ChatColor.GREEN + "Next", ButtonActionsEnum.RIGHT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionData(new PotionData(color));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static Button BackButton(PotionType color) {
        Button itemstack = new Button(Material.TIPPED_ARROW, ChatColor.GREEN + "previous", ButtonActionsEnum.LEFT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionData(new PotionData(color));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

}