package jw.spigot_fluent_api.utilites.messages;

import jw.spigot_fluent_api.utilites.Unimplemented;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public class MessageBuilder
{
    private StringBuilder stringBuilder;
    private Player[] receivers;
    public MessageBuilder()
    {
       stringBuilder = new StringBuilder();
    }
    public MessageBuilder(Player ... players)
    {
       this();
       receivers = players;
    }
    public MessageBuilder text(String text)
    {
       stringBuilder.append(text);
       return this;
    }
    public MessageBuilder text(Object text)
    {
        stringBuilder.append(text);
        return this;
    }

    public MessageBuilder text(Object text, Color color)
    {
        stringBuilder.append(color).append(text);
        return this;
    }

    public MessageBuilder color(ChatColor chatColor)
    {
        stringBuilder.append(chatColor);
      return this;
    }
    public MessageBuilder bold(Object texy)
    {
        stringBuilder.append(ChatColor.BOLD).append(texy);
        return this;
    }
    @Unimplemented
    public MessageBuilder color(String hexColor)
    {
        return this;
    }
    public MessageBuilder space()
    {
        stringBuilder.append(" ");
        return this;
    }

    public MessageBuilder inBrackets(String message)
    {
        stringBuilder.append("[").append(message).append("]");
        return this;
    }

    public MessageBuilder number(int number)
    {
        stringBuilder.append(number);
        return this;
    }
    public MessageBuilder number(float number)
    {
        stringBuilder.append(number);
        return this;
    }
    public String get()
    {
        return stringBuilder.toString();
    }
    public void send()
    {
        if(receivers == null)
        {
            Bukkit.getConsoleSender().sendMessage(get());
        }
        else
        {
            for(var reciever : receivers)
            {
                reciever.sendMessage(get());
            }
        }
    }


}
