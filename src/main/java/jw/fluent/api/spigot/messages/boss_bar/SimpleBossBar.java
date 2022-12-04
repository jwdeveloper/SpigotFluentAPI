package jw.fluent.api.spigot.messages.boss_bar;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

@Getter
@Setter
public class SimpleBossBar
{
    private BossBar bossBar;

    public SimpleBossBar(BossBar bossBar)
    {
        this.bossBar = bossBar;
    }

    public void setTitle(String title)
    {
        bossBar.setTitle(title);
    }

    public void setColor(BarColor color)
    {
        bossBar.setColor(color);
    }

    public void setProgress(double progress)
{
    bossBar.setProgress(progress);
}

    public void setStyle(BarStyle style)
    {
        bossBar.setStyle(style);
    }

    public void disable()
    {
        bossBar.removeAll();
    }
}
