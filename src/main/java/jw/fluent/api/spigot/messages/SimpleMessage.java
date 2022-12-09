package jw.fluent.api.spigot.messages;

import jw.fluent.api.spigot.messages.boss_bar.BossBarBuilder;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.messages.title.SimpleTitleBuilder;

public class SimpleMessage {
    public SimpleTitleBuilder title() {
        return new SimpleTitleBuilder();
    }

    public MessageBuilder chat() {
        return new MessageBuilder();
    }

    public BossBarBuilder bossBar() {
        return new BossBarBuilder();
    }
}
