package jw.fluent.plugin.implementation.modules.documentation;

import jw.fluent.api.spigot.commands.implementation.SimpleCommandManger;
import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.spigot.documentation.implementation.decorator.CommandsDocumentationDecorator;
import jw.fluent.api.spigot.documentation.implementation.decorator.ConfigDocumentationDecorator;
import jw.fluent.api.spigot.documentation.implementation.renderer.GithubDocumentationRenderer;
import jw.fluent.api.spigot.documentation.implementation.renderer.PluginDocumentationRenderer;
import jw.fluent.api.spigot.documentation.implementation.renderer.SpigotDocumentationRenderer;
import jw.fluent.api.spigot.documentation.implementation.decorator.PermissionDocumentationDecorator;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.permissions.api.PermissionGeneratorDto;
import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.api.utilites.files.FileUtility;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        var decorators = getDecorator(model, fluentAPI.getFluentPermission().getPermissions());
        var documentation = new Documentation();
        for (var decorator : decorators) {
            decorator.decorate(documentation);
        }
        var renderers = getRenderers(model);
        var basePath = StringUtils.nullOrEmpty(model.getPath()) ? FluentApi.path() : model.getPath();
        for (var renderer : renderers) {
            var builder = new MessageBuilder();
            var result = renderer.render(builder, documentation);
            var path = basePath + FileUtility.separator() + renderer.getName();
            save(result, path);
        }
    }

    private List<DocumentationDecorator> getDecorator(DocumentationOptions options, List<PermissionModel> permissionModels) {
        var result = new ArrayList<DocumentationDecorator>();
        result.addAll(options.getDecorators());

        var config = new ConfigDocumentationDecorator();
        result.add(config);

        var commandDocumentation = new CommandsDocumentationDecorator(SimpleCommandManger.getRegisteredCommands());
        result.add(commandDocumentation);

        var permissionDocumentation = new PermissionDocumentationDecorator(new PermissionGeneratorDto(options.getPermissionModel(), permissionModels));
        result.add(permissionDocumentation);
        return result;
    }

    private List<DocumentationRenderer> getRenderers(DocumentationOptions options) {
        var result = new ArrayList<DocumentationRenderer>();
        var spigotDocumentationRenderer = new SpigotDocumentationRenderer();
        var pluginDocumentationRenderer = new PluginDocumentationRenderer();
        var githubRenderer = new GithubDocumentationRenderer();
        result.add(spigotDocumentationRenderer);
        result.add(pluginDocumentationRenderer);
        result.add(githubRenderer);
        return result;
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
