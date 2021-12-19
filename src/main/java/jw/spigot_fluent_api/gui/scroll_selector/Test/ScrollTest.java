package jw.spigot_fluent_api.gui.scroll_selector.Test;

import jw.spigot_fluent_api.gui.scroll_selector.ScrollSelector;
import org.bukkit.entity.Player;

public class ScrollTest extends ScrollSelector {
    public ScrollTest() {
        var scroll = ScrollSelector.builder()
                .onScroll(event ->
                {

                }).onClickItem(buttonUI ->
                {

                }).onClose(player ->
                {

                }).onOpen(player ->
                {
                }).build();
        Player p = null;

        scroll.open(p);
        scroll.close();
    }
}
