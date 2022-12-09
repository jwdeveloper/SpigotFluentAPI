package jw.fluent.plugin.implementation.modules.messages;

import jw.fluent.api.spigot.messages.SimpleMessage;
import jw.fluent.api.spigot.messages.boss_bar.BossBarBuilder;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.messages.title.SimpleTitleBuilder;

public final class FluentMessage
{
    private static SimpleMessage simpleMessage = new SimpleMessage();

    public static SimpleTitleBuilder title()
    {
        return simpleMessage.title();
    }

    public static MessageBuilder message()
    {
        return simpleMessage.chat();
    }

    public static BossBarBuilder bossBar()
    {
        return simpleMessage.bossBar();
    }
}
