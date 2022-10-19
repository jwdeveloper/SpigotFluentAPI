package unit.assets;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Injection
public class PlayerStats extends BaseClassTesting
{
   private Integer score;

   private String playerName;

   private Boolean isActive;

   private Number points;

   private List<String> permissions;
}
