package jw.fluent.api.utilites.code_generator.builders;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FieldCodeGenerator
{
    private String modifier= StringUtils.EMPTY;
    private String type= StringUtils.EMPTY;
    private String name= StringUtils.EMPTY;
    private String value = StringUtils.EMPTY;
    private final List<String> comments;

    public FieldCodeGenerator()
    {
        this.comments = new ArrayList<>();
    }

    public FieldCodeGenerator addComment(String comment)
    {
        this.comments.add(comment);
        return this;
    }

    public FieldCodeGenerator setType(String type)
    {
        this.type = type;
        return this;
    }

    public FieldCodeGenerator setName(String name)
    {
        this.name = name;
        return this;
    }

    public FieldCodeGenerator setModifier(String modifier)
    {
        this.modifier = modifier;
        return this;
    }

    public FieldCodeGenerator setValue(String value) {
        this.value = value;
        return this;
    }

    public String build()
    {
        var builder = new MessageBuilder();

        for(var comment : comments)
        {
            builder.text("//").textNewLine(comment);
        }
        builder.text(modifier).space().text(type).space().text(name);

        if(StringUtils.isNullOrEmpty(value))
        {
            builder.text(";");
        }
        else
        {
            builder.space().text("=").space().text(value).text(";");
        }



        return builder.build();
    }
}
