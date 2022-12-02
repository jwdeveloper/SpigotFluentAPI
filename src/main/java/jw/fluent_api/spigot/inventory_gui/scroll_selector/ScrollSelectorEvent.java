package jw.fluent_api.spigot.inventory_gui.scroll_selector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ScrollSelectorEvent
{
    private Player player;
    private int previousIndex;
    private int currentIndex;

    public boolean isIncrease()
    {
        var start = ScrollSelector.startScrollIndex;
        var end = ScrollSelector.startScrollIndex;

        if(previousIndex == start && currentIndex == end)
            return false;

        if(previousIndex == end && currentIndex == start)
        {
            return true;
        }
        if(previousIndex < currentIndex)
            return true;

        return false;
    }

    public boolean isDecrase()
    {
        return !isIncrease();
    }
}
