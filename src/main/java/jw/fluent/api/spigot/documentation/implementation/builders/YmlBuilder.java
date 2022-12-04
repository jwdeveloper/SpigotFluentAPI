package jw.fluent.api.spigot.documentation.implementation.builders;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

public class YmlBuilder
{
    private MessageBuilder builder;

    public YmlBuilder()
    {
        builder = new MessageBuilder();
    }

    public YmlBuilder addSection(String name)
    {
        return addSection(name,0);
    }

    public YmlBuilder addSection(String name, int offset)
    {
        return addProperty(name, StringUtils.EMPTY_STRING,offset);
    }

    public YmlBuilder addListProperty(String member, int offset)
    {
        builder.space(offset).text("- ").text(member).newLine();
        return this;
    }

    public YmlBuilder addListSection(String member, int offset)
    {
        builder.space(offset).text("- ").text(member).text(":").newLine();
        return this;
    }

    public YmlBuilder addProperty(String field, Object value)
    {
        return addProperty(field,value,0);
    }

    public YmlBuilder addProperty(String field, Object value, int offset)
    {
        builder.space(offset).text(field).text(":").space().text(value).newLine();
        return this;
    }

    public YmlBuilder addComment(String comment)
    {
        builder.text("#").text(comment).newLine();
        return this;
    }

    public YmlBuilder newLine()
    {
        builder.newLine();
        return this;
    }

    public String build()
    {
        return builder.build();
    }
}
