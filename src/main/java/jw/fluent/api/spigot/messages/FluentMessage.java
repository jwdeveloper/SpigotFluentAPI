package jw.fluent.api.spigot.messages;

import jw.fluent.api.spigot.messages.boss_bar.BossBarBuilder;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.messages.title.SimpleTitleBuilder;

public class FluentMessage
{
    public static SimpleTitleBuilder title()
    {
        return new SimpleTitleBuilder();
    }

    public static MessageBuilder message()
    {
        return new MessageBuilder();
    }

    public static BossBarBuilder bossBar()
    {
        return new BossBarBuilder();
    }
}
