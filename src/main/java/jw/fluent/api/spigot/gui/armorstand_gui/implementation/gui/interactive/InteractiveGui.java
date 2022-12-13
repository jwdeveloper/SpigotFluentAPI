package jw.fluent.api.spigot.gui.armorstand_gui.implementation.gui.interactive;

import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events.StandCloseEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events.StandOpenEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.api.gui.events.StandUpdateEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.StandGui;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class InteractiveGui extends StandGui
{
    protected InteractiveButtonManager manager;

    @Override
    protected void onInitialize() {
        manager = new InteractiveButtonManager();
        for(var i =0;i<10;i++)
        {
           var button = StandButton.builder()
                    .icon(new ItemStack(Material.DIAMOND))
                    .tag("buttonX")
                    .hitBox(new Vector(0.5,0.5,0.5))
                    .offset(new Vector(i,5,3))
                    .title("Button #"+i)
                    .onLeftClick(standButtonEvent ->
                    {

                        FluentApi.messages().chat().warning().text("Kliknie to w guziczek").color(ChatColor.AQUA).text(standButtonEvent.button().getTitle())
                                        .send(standButtonEvent.player());

                    })
                    .onRightClick(standButtonEvent ->
                    {
                        FluentApi.messages().chat().warning().text("Kliknie proawy to w guziczek").color(ChatColor.AQUA).text(standButtonEvent.button().getTitle())
                                .send(standButtonEvent.player());
                    })
                    .description("OTO JEST GUZIOR")
                    .build();
            addButton(button);
        }
    }

    @Override
    protected void onOpen(StandOpenEvent event) {
           manager.flyAwayFromPlayer(event.player());
    }

    @Override
    protected void onUpdate(StandUpdateEvent event)
    {
         switch (event.action())
         {
             case CREATED -> manager.register(event.button(), event.player());
             case REMOVED -> manager.unregister(event.button());
         }
    }

    @Override
    protected void onClose(StandCloseEvent event) {
        manager.flyToPlayer(event.player());
    }

}
