package jw.fluent.api.spigot.documentation.implementation.renderer;

import jw.fluent.api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent.api.spigot.documentation.api.models.DocumentationSection;
import jw.fluent.api.spigot.messages.message.MessageBuilder;

public class GithubDocumentationRenderer extends DocumentationRenderer {

    @Override
    public String getName() {
        return "documentation-github.md";
    }

    @Override
    protected void onTextSection(MessageBuilder builder, DocumentationSection section) {
        builder.text(">").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onTitleSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("##").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onListSection(MessageBuilder builder, DocumentationSection section) {
        builder.text(" - ").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onYmlSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("``` yaml").newLine();
        builder.text(section.getContent()).newLine();
        builder.text("```").newLine();
    }
}
