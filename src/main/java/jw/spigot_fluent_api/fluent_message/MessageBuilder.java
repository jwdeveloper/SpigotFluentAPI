package jw.spigot_fluent_api.fluent_message;

import jw.spigot_fluent_api.utilites.Unimplemented;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessageBuilder {
    private StringBuilder stringBuilder;
    private Player[] receivers;

    public MessageBuilder() {
        stringBuilder = new StringBuilder();
    }

    public MessageBuilder(Player... players) {
        this();
        receivers = players;
    }

    public MessageBuilder text(String text) {
        stringBuilder.append(text);
        return this;
    }

    public MessageBuilder reset() {
        stringBuilder.append(ChatColor.RESET);
        return this;
    }

    public MessageBuilder field(String name, Object value) {
        return field(Emoticons.dot,name,value);
    }
    public MessageBuilder field(String prefix, String name, Object value) {
        return color(ChatColor.WHITE)
                .text(prefix)
                .space()
                .text(name)
                .space()
                .color(ChatColor.GRAY)
                .text(Emoticons.arrowRight)
                .space()
                .text(value)
                .space()
                .reset();
    }

    public <T> MessageBuilder addList(List<T> name, Consumer<T> action) {
        for (var value : name) {
            action.accept(value);
        }
        return this;
    }

    public MessageBuilder addList(ArrayList<String> name) {
        for (var value : name) {
            this.text(" -" + name).newLine();
        }
        return this;
    }

    public MessageBuilder text(Object text) {
        stringBuilder.append(text);
        return this;
    }

    public MessageBuilder bar(String bar, int length) {
        for (int i = 0; i < length; i++) {
            stringBuilder.append(bar);
        }
        return this;
    }

    public MessageBuilder text(Object text, ChatColor color) {
        stringBuilder.append(color).append(text);
        return this;
    }

    public MessageBuilder color(ChatColor chatColor) {
        stringBuilder.append(chatColor);
        return this;
    }

    public MessageBuilder color(int r, int g, int b) {
        stringBuilder.append(net.md_5.bungee.api.ChatColor.of(new java.awt.Color(r, g, b)));
        return this;
    }

    public MessageBuilder bold(Object text) {
        stringBuilder.append(ChatColor.BOLD).append(text);
        return this;
    }

    public MessageBuilder bold() {
        stringBuilder.append(ChatColor.BOLD);
        return this;
    }

    @Unimplemented
    public MessageBuilder color(String hexColor) {
        return this;
    }

    public MessageBuilder space(int count) {
        for (; count > 0; count--) {
            space();
        }
        return this;
    }

    public MessageBuilder space() {
        stringBuilder.append(" ");
        return this;
    }

    public MessageBuilder inBrackets(String message) {
        stringBuilder.append("[").append(message).append("]");
        return this;
    }

    public MessageBuilder withFix(String message, String fix) {
        stringBuilder.append(fix).append(message).append(fix);
        return this;
    }

    public MessageBuilder withFix(String message, String prefix, String endfix) {
        stringBuilder.append(prefix).append(message).append(endfix);
        return this;
    }


    public MessageBuilder withPrefix(String message, String prefix) {
        stringBuilder.append(prefix).append(message);
        return this;
    }

    public MessageBuilder withEndfix(String message, String endfix) {
        stringBuilder.append(message).append(endfix);
        return this;
    }

    public MessageBuilder underLine(String message) {
        stringBuilder.append(ChatColor.UNDERLINE).append(message).append(ChatColor.RESET);
        return this;
    }

    public MessageBuilder strikeThrough(String message) {
        stringBuilder.append(ChatColor.STRIKETHROUGH).append(message).append(ChatColor.RESET);
        return this;
    }

    public MessageBuilder number(int number) {
        stringBuilder.append(number);
        return this;
    }

    public MessageBuilder newLine() {
        stringBuilder.append(System.lineSeparator());
        return this;
    }

    public MessageBuilder number(float number) {
        stringBuilder.append(number);
        return this;
    }

    public String[] toArray() {

        return get().split(System.lineSeparator());
    }

    public String get() {
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public void send() {
        if (receivers == null) {
            Bukkit.getConsoleSender().sendMessage(get());
        } else {
            for (var reciever : receivers) {
                reciever.sendMessage(get());
            }
        }
    }

    public void send(CommandSender... receivers) {
        var message = toString();
        for (var rective : receivers)
        {
            rective.sendMessage(message);
        }
    }
    public void sendToConsole() {
        Bukkit.getConsoleSender().sendMessage(toString());
    }
    public void sendToAllPlayer()
    {
        var message = toString();
        for(var player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage(message);
        }
    }


}
