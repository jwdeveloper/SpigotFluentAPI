package jw.spigot_fluent_api.gui_v2.utility;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.nms.NmsUtilites;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NFT_Utility_name
{
    public static void TEST(Player player, String title)
    {
        try
        {
            EntityPlayer ep = ((CraftPlayer)player).getHandle();
            Field titleFiled = Container.class.getDeclaredField("title");
            titleFiled.setAccessible(true);
            titleFiled.set(ep.bV,new ChatMessage(title));
        }
        catch (Exception e)
        {
            FluentPlugin.logError(e.toString());
            FluentPlugin.logError(e.getLocalizedMessage());
        }

       // PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(;, "minecraft:chest", new ChatMessage(title), player.getOpenInventory().getTopInventory().getSize());
       // ep.playerConnection.sendPacket(packet);
       //ep.updateInventory(ep.activeContainer);
    }

    public void update(Player p, String title)
    {
        try
        {
            EntityPlayer ep = ((CraftPlayer)p).getHandle();
        }
        catch (Exception e)
        {
            FluentPlugin.logError(e.toString());
            FluentPlugin.logError(e.getLocalizedMessage());
        }

    }

}
