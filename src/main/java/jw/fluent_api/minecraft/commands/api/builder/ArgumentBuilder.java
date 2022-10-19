package jw.fluent_api.minecraft.commands.api.builder;
import jw.fluent_api.minecraft.commands.api.enums.ArgumentDisplay;
import jw.fluent_api.minecraft.commands.api.enums.ArgumentType;
import jw.fluent_api.fluent_commands.implementation.validators.*;
import jw.fluent_api.minecraft.commands.implementation.validators.CommandArgumentValidator;

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
