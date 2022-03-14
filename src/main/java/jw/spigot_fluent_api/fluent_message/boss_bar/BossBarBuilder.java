package jw.spigot_fluent_api.fluent_message.boss_bar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarBuilder
{
    private BossBar bossBar;

    public BossBarBuilder()
    {
       bossBar = Bukkit.createBossBar("[Example bar]", BarColor.WHITE, BarStyle.SOLID);
    }

    public BossBarBuilder setTitle(String title)
    {
        bossBar.setTitle(title);
        return this;
    }

    public BossBarBuilder setVisible(boolean visible)
    {
        bossBar.setVisible(visible);
        return this;
    }

    public BossBarBuilder setProgress(double progress)
    {
        bossBar.setProgress(progress);
        return this;
    }

    public BossBarBuilder setColor(BarColor color)
    {
        bossBar.setColor(color);
        return this;
    }

    public BossBarBuilder setStyle(BarStyle style)
    {
        bossBar.setStyle(style);
        return this;
    }

    public BossBarBuilder setFlag(BarFlag ... flags)
    {
        for(var flag: flags)
        {
            bossBar.addFlag(flag);
        }
        return this;
    }

    public SimpleBossBar build()
    {
        return new SimpleBossBar(bossBar);
    }

    public SimpleBossBar buildAndShow(Player ... players)
    {
        var result = build();
        for(var player:players)
        {
            bossBar.addPlayer(player);
        }
        return result;
    }
}
