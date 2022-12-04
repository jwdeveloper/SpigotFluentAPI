package jw.fluent.api.utilites;

import jw.fluent.api.spigot.inventory_gui.InventoryUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtility {
    public static boolean giveToEmptySlot(ItemStack itemStack, Inventory inventory, boolean includeFirstRow) {
        var start = includeFirstRow ? 0 : InventoryUI.INVENTORY_WIDTH - 1;
        for (var i = start; i < inventory.getSize(); i++) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                    continue;
            }

            var item = inventory.getItem(i);
            if (item != null) {
                continue;
            }


            inventory.setItem(i, itemStack);
            return true;
        }
        return false;
    }

    public static void giveToEmptySlotOrDrop(Player player, ItemStack itemStack, boolean includeFirstRow) {
       if(giveToEmptySlot(itemStack,player.getInventory(),includeFirstRow))
       {
          return;
       }

       var loc = player.getLocation();
        loc.getWorld().dropItem(loc.clone().add(0,1,0),itemStack);
    }
}
