package jw.fluent.api.spigot.documentation.api;

import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.spigot.documentation.api.models.DocumentationSection;
import jw.fluent.api.spigot.messages.message.MessageBuilder;

public abstract class DocumentationRenderer
{
    public abstract String getName();

    protected void onTitleSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onTextSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onImageSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }


    protected void onListSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }

    protected void onCodeSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onYmlSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onHtmlSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }

    protected void onVideoSection(MessageBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }

    public final String render(MessageBuilder builder, Documentation documentation)
    {
        for(var section : documentation.getSections())
        {
            resolveSection(builder, section);
        }
        return builder.build();
    }

    private void resolveSection(MessageBuilder builder, DocumentationSection section)
    {
        switch (section.getSectionType())
        {
            case YML -> onYmlSection(builder, section);
            case CODE-> onCodeSection(builder, section);
            case TEXT-> onTextSection(builder, section);
            case IMAGE-> onImageSection(builder, section);
            case TITlE-> onTitleSection(builder, section);
            case HTML-> onHtmlSection(builder, section);
            case LIST-> onListSection(builder, section);
            case VIDEO-> onVideoSection(builder, section);
        }
    }


    private void addByDefault(MessageBuilder stringBuilder, DocumentationSection section)
    {
        stringBuilder.text(section.getContent()).newLine();
    }
}
