package jw.fluent.plugin.implementation.modules.documentation;

import jw.fluent.api.spigot.documentation.implementation.CommandsDocumentationRenderer;
import jw.fluent.api.spigot.documentation.implementation.InformationDocumentationRenderer;
import jw.fluent.api.spigot.messages.FluentMessage;
import jw.fluent.api.spigot.documentation.implementation.PermissionDocumentationRenderer;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class FluentDocumentationExtention implements FluentApiExtention {

    private Consumer<DocumentationOptions> options;

    public FluentDocumentationExtention(Consumer<DocumentationOptions> options) {
        this.options = options;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        var model = new DocumentationOptions();
        options.accept(model);

        var commandDocumentation = new CommandsDocumentationRenderer();
        var permissionDocumentation = new PermissionDocumentationRenderer(model.getPermissionModel(), fluentAPI.getFluentPermission().getPermissions());
        var infoDocumentation = new InformationDocumentationRenderer();

        var content = FluentMessage.message();

        content.text(infoDocumentation.render()).newLine();
        if (model.isRenderCommandsDocumentation()) {
            content.text(commandDocumentation.render()).newLine();
        }
        if (model.isRenderCommandsDocumentation()) {
            content.text(permissionDocumentation.render()).newLine();
        }
        var path = StringUtils.nullOrEmpty(model.getPath()) ? FluentApi.path() + "\\documentation.yml" : model.getPath();
        save(content.build(), path);
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }

    public static void save(String content, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
    }
}
