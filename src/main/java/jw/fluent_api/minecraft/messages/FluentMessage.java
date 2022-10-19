package jw.fluent_api.minecraft.messages;

import jw.fluent_api.minecraft.messages.boss_bar.BossBarBuilder;
import jw.fluent_api.minecraft.messages.message.MessageBuilder;
import jw.fluent_api.minecraft.messages.title.SimpleTitleBuilder;

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
