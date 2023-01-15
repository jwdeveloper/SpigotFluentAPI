package jw.fluent.api.utilites.code_generator.builders;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MethodCodeGenerator {

    private String methodType = StringUtils.EMPTY;
    private String body = StringUtils.EMPTY;
    private String name = StringUtils.EMPTY;
    private String modifiers = StringUtils.EMPTY;
    private final List<String> parameters;
    private final List<String> comments;
    private final List<String> annotations;

    public MethodCodeGenerator() {
        parameters = new ArrayList<>();
        comments = new ArrayList<>();
        annotations = new ArrayList<>();
    }

    public MethodCodeGenerator addAnnotation(String annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public MethodCodeGenerator addComment(String comment) {
        this.comments.add(comment);
        return this;
    }

    public MethodCodeGenerator setType(String methodType) {
        this.methodType = methodType;
        return this;
    }

    public MethodCodeGenerator setName(String name) {
        this.name = name;
        return this;
    }

    public MethodCodeGenerator setModifiers(String modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public MethodCodeGenerator setBody(String body) {
        this.body = body;
        return this;
    }

    public MethodCodeGenerator addBodyLine(String line) {
        this.body = body + StringUtils.separator() + line;
        return this;
    }

    public MethodCodeGenerator setBody(Consumer<MessageBuilder> consumer) {
        var builder = new MessageBuilder();
        consumer.accept(builder);
        this.body = builder.toString();
        return this;
    }

    public MethodCodeGenerator addParameter(String parameter) {
        this.parameters.add(parameter);
        return this;
    }

    public MethodCodeGenerator addParameter(Class clazz, String parameter) {
        this.parameters.add(clazz.getSimpleName()+" "+parameter);
        return this;
    }

    public String build() {
        var builder = new MessageBuilder();

        for (var comment : comments) {
            builder.text("//").textNewLine(comment);
        }
        for (var annotation : annotations) {
            builder.text("@").textNewLine(annotation);
        }
        builder.text(modifiers).space().text(methodType).space().text(name).text("(");

        for (var i = 0; i < parameters.size(); i++) {
            var param = parameters.get(i);
            builder.text(param);
            if (i != parameters.size() - 1) {
                builder.text(",").space();
            }
        }
        builder.textNewLine(")");
        builder.textNewLine("{");

        var bodyLines = body.lines();
        for (var line : bodyLines.toList()) {
            builder.space(4).textNewLine(line);
        }

        builder.textNewLine("}");

        return builder.build();
    }

}
