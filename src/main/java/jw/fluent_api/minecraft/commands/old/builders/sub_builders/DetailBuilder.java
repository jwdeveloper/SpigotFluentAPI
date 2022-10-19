package jw.fluent_api.minecraft.commands.old.builders.sub_builders;

import jw.fluent_api.minecraft.commands.api.models.CommandModel;
import jw.fluent_api.desing_patterns.builder.NextStep;

import java.util.Arrays;

public class DetailBuilder implements NextStep<ArgumentBuilder>
{

    private final CommandModel commandModel;

    public DetailBuilder(CommandModel commandModel)
    {
        this.commandModel = commandModel;
    }

    public DetailBuilder setUsageMessage(String usageMessage)
    {
        commandModel.setUsageMessage(usageMessage);
        return this;
    }

    public DetailBuilder setPermissionMessage(String permissionMessage)
    {
        commandModel.setPermissionMessage(permissionMessage);
        return this;
    }
    public DetailBuilder setLabel(String label)
    {
        commandModel.setLabel(label);
        return this;
    }

    public DetailBuilder setShortDescription(String shortDescription)
    {
        commandModel.setShortDescription(shortDescription);
        return this;
    }

    public DetailBuilder setDescription(String description)
    {
        commandModel.setDescription(description);
        return this;
    }

    public DetailBuilder addOpPermission()
    {
        commandModel.getPermissions().add("op");
        return this;
    }
    public DetailBuilder addPermissions(String ... permisson)
    {
        commandModel.getPermissions().addAll(Arrays.asList(permisson));
        return this;
    }


    @Override
    public ArgumentBuilder nextStep() {
        return new ArgumentBuilder(commandModel);
    }
}
