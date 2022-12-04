package jw.fluent.api.spigot.documentation.implementation.decorator;

import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.spigot.documentation.implementation.builders.YmlBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.Collection;

public class CommandsDocumentationDecorator extends DocumentationDecorator {

    private Collection<SimpleCommand> commands;
    public CommandsDocumentationDecorator(Collection<SimpleCommand> commands)
    {
        this.commands = commands;
    }

    @Override
    public void decorate(Documentation documentation) {
        addTitle("Commands",documentation,"yml-title");
        addImage("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png",documentation);

        var builder = createYmlBuilder();
        builder.newLine();
        builder.newLine();
        builder.addSection("commands");
        for (var command : commands) {
            renderCommandInfo(builder, command);
        }
        var yml = builder.build();
        addYml(yml, documentation);
    }






    private void renderCommandInfo(YmlBuilder builder, SimpleCommand command) {
        var defaultOffset =2;
        var propertyOffset = 4;
        var listOffset = 6;
        var model = command.getCommandModel();
        var title = StringUtils.nullOrEmpty(model.getUsageMessage())?model.getName():model.getUsageMessage();
        builder.addComment(title);
        builder.addSection(model.getName(), defaultOffset);
        if (!command.getSubCommands().isEmpty()) {
            builder.addSection("children",propertyOffset);
            for (var subCommand : command.getSubCommands()) {
                builder.addListProperty(subCommand.getName(),listOffset);
            }
        }

        if (!model.getPermissions().isEmpty()) {
            builder.addSection("permissions",propertyOffset);
            for (var permission : model.getPermissions()) {
                builder.addListProperty(permission,listOffset);
            }
        }


        if (!model.getCommandAccesses().isEmpty()) {
            builder.addSection("can-use",propertyOffset);
            for (var access : model.getCommandAccesses()) {
                builder.addListProperty(access.name().toLowerCase(),listOffset);
            }
        }

        if (!model.isAllParametersRequired()) {
            builder.addProperty("all-arguments-required", model.isAllParametersRequired(), propertyOffset);
        }

        if (model.getArguments().size() > 0) {
            builder.addSection("arguments",propertyOffset);
            for (var argument : model.getArguments()) {
                builder.addListSection(argument.getName(),listOffset);
                builder.addProperty("type", argument.getType().name().toLowerCase(),listOffset + 4);
                if (!StringUtils.nullOrEmpty(argument.getDescription())) {
                    builder.addProperty("description", argument.getDescription(),listOffset + 4);
                }
                if (!argument.getTabCompleter().isEmpty()) {
                    builder.addSection("options",listOffset + 4);
                    for (var tab : argument.getTabCompleter()) {
                        builder.addListProperty(tab,listOffset  + 8);
                    }
                }
            }
        }

        if (!StringUtils.nullOrEmpty(model.getShortDescription())) {
            builder.addProperty("short-description", model.getShortDescription(), propertyOffset);
        }

        if (!StringUtils.nullOrEmpty(model.getDescription())) {
            builder.addProperty("description", model.getDescription(), propertyOffset);
        }

        if (!StringUtils.nullOrEmpty(model.getLabel())) {
            builder.addProperty("label", model.getLabel(), propertyOffset);
        }

        if (!StringUtils.nullOrEmpty(model.getUsageMessage())) {
            builder.addProperty("usage", model.getUsageMessage(), propertyOffset);
        }

        if (!StringUtils.nullOrEmpty(model.getPermissionMessage())) {
            builder.addProperty("permission-message", model.getPermissionMessage(), propertyOffset);
        }

        for (var subCommand : command.getSubCommands()) {
            renderCommandInfo(builder, subCommand);
        }
        builder.newLine();
    }



    private void renderCommandTreeMember(YmlBuilder builder, SimpleCommand command, int offset)
    {
        builder.addSection(command.getName(),offset);
        offset = offset +2;
        for (var subCommand : command.getSubCommands()) {
            renderCommandTreeMember(builder, subCommand, offset);
        }
    }


}

