package jw.fluent.api.spigot.documentation.implementation.renderer;

import jw.fluent.api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent.api.spigot.documentation.api.models.DocumentationSection;
import jw.fluent.api.spigot.messages.message.MessageBuilder;

public class PluginDocumentationRenderer  extends DocumentationRenderer {

    @Override
    public String getName() {
        return "documentation.yml";
    }



    @Override
    protected void onTitleSection(MessageBuilder builder, DocumentationSection section) {
        builder.newLine();
        builder.text("#").bar("=", 100).newLine();
        builder.text("#").bar(" ", 50).text(" ").text(section.getContent()).text(" ").bar(" ", 50).newLine();
        builder.text("#").bar("=", 100).newLine();
    }

    @Override
    protected void onYmlSection(MessageBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("plugin-ignore"))
        {
            return;
        }
        super.onYmlSection(builder, section);
    }

    @Override
    protected void onImageSection(MessageBuilder builder, DocumentationSection section) {

    }

    @Override
    protected void onCodeSection(MessageBuilder builder, DocumentationSection section) {

    }

    @Override
    protected void onTextSection(MessageBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("plugin-ignore"))
        {
            return;
        }

        if(section.hasAttribute("bold"))
        {
            builder.text("#").space().text(section.getContent().toUpperCase()).newLine();
            return;
        }

        builder.text("#").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onListSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("#").space(3).text("-").text(section.getContent()).newLine();
    }

    @Override
    protected void onLinkSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("#").space(3).text(section.getId()).text(":").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onVideoSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("#").space(3).text("video:").space().text(section.getContent()).newLine();
    }
}
