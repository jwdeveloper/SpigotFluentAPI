package jw.spigot_fluent_api.fluent_commands.api.builder;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentDisplay;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.implementation.validators.*;

import java.util.List;

public interface ArgumentBuilder
{
    ArgumentBuilder setType(ArgumentType type);

    ArgumentBuilder setTabComplete(List<String> tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete, int index);

    ArgumentBuilder setValidator(CommandArgumentValidator validator);

    ArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode);

    ArgumentBuilder setDescription(String description);

}
