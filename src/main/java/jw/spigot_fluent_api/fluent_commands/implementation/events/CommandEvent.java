package jw.spigot_fluent_api.fluent_commands.implementation.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
public class CommandEvent
{
   private CommandSender sender;
   private String[] commandArgs;
   private String[] args;
   private Object[] values;

   @Setter
   private boolean result;

   public boolean getResult()
   {
       return result;
   }

   public <T> T getArgumentValue(int index)
   {
      return (T)(values[index]);
   }
}
