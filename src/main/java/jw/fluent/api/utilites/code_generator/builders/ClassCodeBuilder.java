package jw.fluent.api.utilites.code_generator.builders;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClassCodeBuilder  {

    private final int offset =4;
    private String _package = StringUtils.EMPTY;
    private String name= StringUtils.EMPTY;
    private String modifiers= StringUtils.EMPTY;
    private final List<String> imports;
    private final List<String> methods;
    private final List<String> annotations;
    private final List<String> interfaces;
    private final List<String> fields;
    private final List<String> comments;
    private final List<String> constructors;

    private final List<String> classes;

    public ClassCodeBuilder() {
        imports = new ArrayList<>();
        methods = new ArrayList<>();
        annotations = new ArrayList<>();
        interfaces = new ArrayList<>();
        fields = new ArrayList<>();
        constructors = new ArrayList<>();
        comments = new ArrayList<>();
        classes = new ArrayList<>();
    }

    public ClassCodeBuilder addComment(String comment)
    {
        this.comments.add(comment);
        return this;
    }


    public ClassCodeBuilder setClassName(String name) {
        this.name = name;
        return this;
    }

    public ClassCodeBuilder setModifiers(String modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public ClassCodeBuilder addAnnotation(String annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public ClassCodeBuilder setPackage(String _package) {
        this._package = _package;
        return this;
    }

    public ClassCodeBuilder addImport(String _import) {
        imports.add(_import);
        return this;
    }

    public ClassCodeBuilder addMethod(String method) {
        methods.add(method);
        return this;
    }

    public ClassCodeBuilder addClass(String clazz) {
        methods.add(clazz);
        return this;
    }

    public ClassCodeBuilder addClass(Consumer<ClassCodeBuilder> consumer) {
        var builder = new ClassCodeBuilder();
        consumer.accept(builder);
        var result = builder.build();
        methods.add(result);
        return this;
    }

    public ClassCodeBuilder addMethod(Consumer<MethodCodeGenerator> consumer)
    {
        var builder = new MethodCodeGenerator();
        consumer.accept(builder);
        var result = builder.build();
        methods.add(result);
        return this;
    }

    public ClassCodeBuilder addInterface(String _interface) {
        interfaces.add(_interface);
        return this;
    }

    public ClassCodeBuilder addField(String field) {
        fields.add(field);
        return this;
    }

    public ClassCodeBuilder addField(Consumer<FieldCodeGenerator> consumer) {
        var builder = new FieldCodeGenerator();
        consumer.accept(builder);
        var result = builder.build();
        fields.add(result);
        return this;
    }

    public ClassCodeBuilder addConstructor(String constructor) {
        constructors.add(constructor);
        return this;
    }

    public ClassCodeBuilder addConstructor(Consumer<MethodCodeGenerator> consumer)
    {
        var builder = new MethodCodeGenerator();
        consumer.accept(builder);
        builder.setName(this.name);
        var result = builder.build();
        methods.add(result);
        return this;
    }

    public String build() {

        comments.add("@JW generated code source don't modify it");
        var builder = new MessageBuilder();

        if(StringUtils.isNotNullOrEmpty(_package))
        {
            builder.text("package").space().text(_package).textNewLine(";");
        }

        builder.newLine();
        for(var _import : imports)
        {
            builder.text("import").space().text(_import).textNewLine(";");
        }
        builder.newLine();

        for(var comment : comments)
        {
            builder.text("//").textNewLine(comment);
        }
        builder.newLine();

        for(var annotation : annotations)
        {
            builder.text("@").textNewLine(annotation);
        }
        builder.text(modifiers).space().text("class").space().text(name);
        if(!interfaces.isEmpty())
        {
            builder.space().text("implements");
            var i =0;
            for(var _interface : interfaces)
            {
                builder.space().text(_interface);
                if(i != interfaces.size()-1)
                {
                    builder.text(",");
                }
                i++;
            }
            builder.newLine();
        }

        builder.textNewLine("{");

        for(var field : fields)
        {
            for(var line : field.lines().toList())
            {
                builder.space(offset).textNewLine(line);
            }
            builder.newLine();
        }

        builder.newLine();

        for(var constructor : constructors)
        {
            for(var line : constructor.lines().toList())
            {
                builder.space(offset).textNewLine(line);
            }
            builder.newLine();
        }

        for(var method : methods)
        {
            for(var line : method.lines().toList())
            {
                builder.space(offset).textNewLine(line);
            }
            builder.newLine();
        }

        for(var clazz : classes)
        {
            for(var line : clazz.lines().toList())
            {
                builder.space(offset).textNewLine(line);
            }
            builder.newLine();
        }

        builder.textNewLine("}");
        return builder.build();
    }



}
