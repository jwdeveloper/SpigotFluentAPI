/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.fluent.api.utilites;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.api.FluentTranslations;
import jw.fluent.plugin.implementation.FluentApi;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LinkMessageUtility
{
    public static void send(Player player, String link, String title)
    {
        var lang = FluentApi.translator();
        var resoucePack = new MessageBuilder().info().text(title);
        var component =  new MessageBuilder().color(ChatColor.AQUA)
            .color(ChatColor.BOLD)
            .text(Emoticons.arrowRight)
            .space()
            .text(lang.get(FluentTranslations.COPY.TO_CLIPBOARD))
            .toTextComponent();


        component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, link));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(lang.get(FluentTranslations.COPY.INFO))));

        var component2 =  new MessageBuilder().color(ChatColor.AQUA)
                .color(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(lang.get(FluentTranslations.COPY.TO_CHAT))
                .toTextComponent();
        component2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, link));
        component2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(lang.get(FluentTranslations.COPY.INFO))));

        resoucePack.send(player);
        player.getPlayer().sendMessage(" ");
        player.getPlayer().spigot().sendMessage(component);
        player.getPlayer().spigot().sendMessage(component2);
    }
}
