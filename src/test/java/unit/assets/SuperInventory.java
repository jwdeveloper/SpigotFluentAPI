package unit.assets;


import unit.assets.annotations.TestingAnnotation;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

@TestingAnnotation
@Injection
@Getter
public class SuperInventory
{
    @Inject
    private SomeRepo someRepo;

    private final PlayerStats playerStats;

    @Inject
    public SuperInventory(PlayerStats playerStats)
    {
        this.playerStats = playerStats;
    }

    @Setter
    private String name = "Invenotry name";


    @Setter
    private int size = 36;

    private ChatColor color = ChatColor.AQUA;
}
