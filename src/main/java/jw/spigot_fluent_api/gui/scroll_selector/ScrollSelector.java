package jw.spigot_fluent_api.gui.scroll_selector;

import jw.spigot_fluent_api.gui.button.ButtonUI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ScrollSelector
{
    public static int startScrollIndex = 36;
    public static int endScrollIndex = 44;

    private boolean isOpen;
    private Player player;
    private final ItemStack[] playerRealInventory;

    private final ButtonUI[] playerFakeInventory;
    private Consumer<Player> onOpen;
    private Consumer<Player> onClose;
    private Consumer<ScrollSelectorEvent> onScroll;
    private Consumer<ButtonUI> onClickItem;
    private String onOpenMessage = "press Q to exit";

    public ScrollSelector()
    {
        playerRealInventory = new ItemStack[8];
        playerFakeInventory = new ButtonUI[8];
    }
    public final void onSlotIndexChanged(ScrollSelectorEvent event) {
        if (onScroll == null)
            return;
        onScroll.accept(event);
    }

    public final void onClick(ScrollSelectorEvent event)
    {
        var button = playerFakeInventory[event.getCurrentIndex()];
        if(onClickItem != null)
        {
            onClickItem.accept(button);
        }
        if(button == null)
            return;

        button.click(player);
    }

    public final void open(Player player)
    {
        var manager = ScrollSelectorManager.getInstance();
        manager.register(this);

        this.player = player;
        getPlayerRealInventory(player);
        setPlayerFakeInventory(player,playerFakeInventory);
        this.isOpen = true;
        if (onOpen == null)
            return;
        onOpen.accept(player);
    }

    public final void close()
    {
        var manager = ScrollSelectorManager.getInstance();
        manager.unregister(this);

        this.isOpen = false;
        setPlayerInventory(player,playerRealInventory);
        if (onClose == null || player == null)
            return;

        onClose.accept(player);
    }

    public ScrollSelector addFakeItem(ButtonUI buttonUI)
    {
        var index = (startScrollIndex-buttonUI.getWidth())%startScrollIndex;
        playerFakeInventory[index] = buttonUI;
        return this;
    }

    private ItemStack[] getPlayerRealInventory(Player player)
    {
        for (int i = startScrollIndex; i <= endScrollIndex; i++)
        {
            this.playerRealInventory[i - startScrollIndex] = player.getInventory().getItem(i);
        }
        return this.playerRealInventory;
    }

    private void setPlayerFakeInventory(Player player, ButtonUI[] buttons)
    {
        for (int i = startScrollIndex; i <= endScrollIndex; i++)
        {
            var button = buttons[startScrollIndex-i];
            if(button == null)
                continue;
            player.getInventory().setItem(i,button.getItemStack());
        }
    }

    private void setPlayerInventory(Player player, ItemStack[] itemStacks)
    {
        for (int i = startScrollIndex; i <= endScrollIndex; i++)
        {
           player.getInventory().setItem(i,itemStacks[startScrollIndex-i]);
        }
    }
}
