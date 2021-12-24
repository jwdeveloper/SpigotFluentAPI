package jw.spigot_fluent_api.simple_commands.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
public class SimpleCommandEvent
{
   private CommandSender sender;
   private String[] commandArgs;
   private String[] args;
   @Setter
   private boolean result;

   public boolean getResult()
   {
       return result;
   }
}
