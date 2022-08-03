package jw.spigot_fluent_api.fluent_message;

import jw.spigot_fluent_api.fluent_message.boss_bar.BossBarBuilder;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import jw.spigot_fluent_api.fluent_message.title.SimpleTitleBuilder;

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
