package jw.fluent.api.spigot.messages.title;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import org.bukkit.entity.Player;

public class SimpleTitleBuilder {

    private String title ="";
    private String subTitle ="";
    private int fadeIn =10;
    private int stay =10;
    private int fadeOut =10;

    public SimpleTitleBuilder withTitle(MessageBuilder title)
    {
        this.title = title.toString();
        return this;
    }
    public SimpleTitleBuilder withSubTitle(MessageBuilder subTitle)
    {
        this.subTitle = subTitle.toString();
        return this;
    }
    public SimpleTitleBuilder withTitle(String title)
    {
        this.title = title;
        return this;
    }
    public SimpleTitleBuilder withSubTitle(String subTitle)
    {
        this.subTitle = subTitle;
        return this;
    }

    public SimpleTitleBuilder setFadeInTicks(int fadeIn)
    {
        this.fadeIn = fadeIn;
        return this;
    }

    public SimpleTitleBuilder setFadeOutTicks(int fadeOut)
    {
        this.fadeOut = fadeOut;
        return this;
    }
    public SimpleTitleBuilder setStayTicks(int stay)
    {
        this.stay = stay;
        return this;
    }

    public void buildAndSend(Player ... players) {
        for(var player: players)
        {
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        }
    }
}
