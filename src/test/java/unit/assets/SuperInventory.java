package unit.assets;


import unit.assets.annotations.TestingAnnotation;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

@TestingAnnotation
@Injection
@Getter
public class SuperInventory
{

    private SomeRepo someRepo;

    private final PlayerStats playerStats;

    @Inject
    public SuperInventory(PlayerStats playerStats, SomeRepo someRepo)
    {
        this.playerStats = playerStats;
        this.someRepo = someRepo;
    }

    @Setter
    private String name = "Invenotry name";


    @Setter
    private int size = 36;

    private ChatColor color = ChatColor.AQUA;
}
