package jw.fluent.api.spigot.documentation.api;

import jw.fluent.api.spigot.messages.FluentMessage;
import jw.fluent.api.spigot.messages.message.MessageBuilder;

public abstract class DocumentationRenderer {


    protected MessageBuilder builder;

    protected final int defaultOffset = 4;
    protected final int propertyOffset = 6;
    protected final int listOffset = 8;

    public DocumentationRenderer() {
        builder = FluentMessage.message();
    }

    public abstract String render();

    protected void renderTitle(String title) {
        builder.newLine();
        builder.text("#").bar("=", 100).newLine();
        builder.text("#").bar(" ", 50).text(" ").text(title).text(" ").bar(" ", 50).newLine();
        builder.text("#").bar("=", 100).newLine();
    }

    protected MessageBuilder ymlField(String field)
    {
       return builder.text(field).text(":").newLine();
    }

    protected MessageBuilder ymlField(String field, String value)
    {
        return builder.text(field).text(":").space().text(value).newLine();
    }

    protected MessageBuilder ymlField(String field, String value, int offset)
    {
        return builder.space(offset).text(field).text(":").space().text(value).newLine();
    }
    protected MessageBuilder addComment(String comment)
    {
        return builder.text("#").space().text(comment).newLine();
    }

    protected MessageBuilder smallTitle(String title)
    {
        return builder.text("#").bar("=", 50).space().text(title).space().bar("=", 50-title.length()).newLine();
    }
}
