package jw.fluent_api.spigot.documentation.implementation;

import jw.fluent_api.spigot.commands.implementation.SimpleCommand;
import jw.fluent_api.spigot.commands.implementation.SimpleCommandManger;
import jw.fluent_api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent_api.utilites.java.StringUtils;

import java.util.Collection;

public class CommandsDocumentationRenderer extends DocumentationRenderer {





    public String render() {
        var commands = getCommandsModel();
        var result = render(commands);
        return result;
    }

    private Collection<SimpleCommand> getCommandsModel() {
        return SimpleCommandManger.getRegisteredCommands();
    }

    private String render(Collection<SimpleCommand> commands) {

        renderTitle("Commands");

        builder.text("commands-tree:").newLine();
        for (var command : commands) {
            renderCommandTreeMember(command,0);
        }
        builder.newLine();
        builder.newLine();

        builder.text("commands:").newLine();
        for (var command : commands) {
            renderCommandInfo(command);
        }
        return builder.build();
    }

    private void renderCommandInfo(SimpleCommand command) {
        var model = command.getCommandModel();

        builder.text("#").bar("=", 25).space().text(model.getName()).space().bar("=", 25).newLine();
        builder.space(defaultOffset).text(model.getName()).text(":").newLine();

        if (!command.getSubCommands().isEmpty()) {
            builder.space(propertyOffset).text("children").text(":").newLine();
            for (var subCommand : command.getSubCommands()) {
                builder.space(listOffset).text("-").space().text(subCommand.getName()).newLine();
            }
        }

        if (!model.getPermissions().isEmpty()) {
            builder.space(propertyOffset).text("permissions").text(":").newLine();
            for (var permission : model.getPermissions()) {
                builder.space(listOffset).text("-").space().text(permission).newLine();
            }
        }


        if (!model.getCommandAccesses().isEmpty()) {
            builder.space(propertyOffset).text("can-use").text(":").newLine();
            for (var access : model.getCommandAccesses()) {
                builder.space(listOffset).text("-").space().text(access.name().toLowerCase()).newLine();
            }
        }

        if (!model.isAllParametersRequired()) {
            builder.space(propertyOffset).text("all-arguments-required").text(":").space().text(model.isAllParametersRequired()).newLine();
        }

        if (model.getArguments().size() > 0) {
            builder.space(propertyOffset).text("arguments").text(":").newLine();
            for (var argument : model.getArguments()) {
                builder.space(listOffset).text("-").space().text(argument.getName()).text(":").newLine();
                builder.space(listOffset + 4).text("type").text(":").space().text(argument.getType().name().toLowerCase()).newLine();
                if (!StringUtils.nullOrEmpty(argument.getDescription())) {
                    builder.space(listOffset + 4).text("description").text(":").space().text(argument.getDescription()).newLine();
                }
                if (!argument.getTabCompleter().isEmpty()) {
                    builder.space(listOffset + 4).text("options").text(":").newLine();
                    for (var tab : argument.getTabCompleter()) {
                        builder.space(listOffset + 8).text("-").space().text(tab).newLine();
                    }
                }
            }
        }

        if (!StringUtils.nullOrEmpty(model.getShortDescription())) {
            builder.space(propertyOffset).text("short-description").text(":").space().text(model.getShortDescription()).newLine();
        }

        if (!StringUtils.nullOrEmpty(model.getDescription())) {
            builder.space(propertyOffset).text("description").text(":").space().text(model.getDescription()).newLine();
        }

        if (!StringUtils.nullOrEmpty(model.getLabel())) {
            builder.space(propertyOffset).text("label").text(":").space().text(model.getLabel()).newLine();
        }

        if (!StringUtils.nullOrEmpty(model.getUsageMessage())) {
            builder.space(propertyOffset).text("usage").text(":").space().text(model.getUsageMessage()).newLine();
        }

        if (!StringUtils.nullOrEmpty(model.getPermissionMessage())) {
            builder.space(propertyOffset).text("permission-message").text(":").space().text(model.getPermissionMessage()).newLine();
        }

        for (var subCommand : command.getSubCommands()) {
            renderCommandInfo(subCommand);
        }
        builder.newLine();
    }



    private void renderCommandTreeMember(SimpleCommand command, int offset)
    {
        builder.space(defaultOffset +offset).text(command.getName()).text(":").newLine();
        offset = offset +2;
        for (var subCommand : command.getSubCommands()) {
            renderCommandTreeMember(subCommand, offset);
        }
    }
}

