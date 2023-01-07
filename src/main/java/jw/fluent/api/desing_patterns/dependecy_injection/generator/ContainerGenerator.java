package jw.fluent.api.desing_patterns.dependecy_injection.generator;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import jw.fluent.api.utilites.code_generator.builders.MethodCodeGenerator;
import jw.fluent.api.utilites.java.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ContainerGenerator {
    private final List<RegistrationInfo> registrationInfo;
    private ClassCodeBuilder builder;
    private MethodCodeGenerator registerMethod;
    private MethodCodeGenerator constructor;


    public ContainerGenerator(List<RegistrationInfo> registrationInfo) {
        this.registrationInfo = registrationInfo;

    }


    public String generate(String package_, String className) throws IOException {
        builder = new ClassCodeBuilder();

        builder.setPackage(package_);

        builder.addImport("jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container");
        builder.addImport("java.util.HashMap");
        builder.addImport("java.util.Map");
        builder.addImport("jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo");
        builder.addImport("java.util.function.Supplier");
        builder.addImport("java.util.function.Function");
        builder.addImport("jw.fluent.api.desing_patterns.dependecy_injection.api.enums.RegistrationType");

        builder.setModifiers("public");
        builder.setClassName(className);
        builder.addInterface("Container");

        builder.addField(options ->
        {
            options.setModifier("private final");
            options.setType("Map<Class<?>, Supplier<Object>>");
            options.setName("injections");
        });


        constructor = new MethodCodeGenerator();
        constructor.setName(className);
        constructor.setModifiers("public");
        constructor.setBody("""
                injections = new HashMap<>();
                """);


        builder.addMethod(options ->
        {
            options.setModifiers("public");
            options.addAnnotation("Override");
            options.setType("Object");
            options.setName("find");
            options.addParameter("Class<?> type");
            options.setBody("""
                     if(!injections.containsKey(type))
                     {
                       System.out.println("Injection not found");
                       return null;
                     }
                                       
                     return injections.get(type).get();
                    """);
        });

        registerMethod = new MethodCodeGenerator();
        registerMethod.addAnnotation("Override");
        registerMethod.setModifiers("public");
        registerMethod.setType("boolean");
        registerMethod.setName("register");
        registerMethod.addParameter("RegistrationInfo info");


        for (var registration : registrationInfo) {
            addInjection(registration);
        }

        builder.addConstructor(constructor.build());
        registerMethod.addBodyLine("return false;");
        builder.addMethod(registerMethod.build());
        return builder.build();
    }


    public void addInjection(RegistrationInfo info) {
        switch (info.registrationType()) {
            case OnlyImpl, InterfaceAndIml -> addInterfaceAndIml(info);
            case InterfaceAndProvider -> addInterfaceAndProvider(info);
        }
    }


    private void addInterfaceAndIml(RegistrationInfo info) {
        var _interface = info._interface() == null ? info.implementation() : info._interface();
        var implementation = info.implementation();
        var methodName = createMethodName(_interface);

        var methodBuilder = new MethodCodeGenerator();
        methodBuilder.addComment(String.format("%s for class %s", info.lifeTime().name(), _interface.getSimpleName()));
        methodBuilder.setModifiers("private");
        methodBuilder.setType(_interface.getName());
        methodBuilder.setName(methodName);
        constructor.addBodyLine(String.format("injections.put(%s.class, this::%s);", _interface.getName(), methodName));


        if (info.lifeTime() == LifeTime.TRANSIENT) {

            methodBuilder.setBody(options ->
            {
                options.textFormat("return new %s(%s);",
                        implementation.getName(),
                        getConstructorInjections(implementation));
            });
            builder.addMethod(methodBuilder.build());
            return;
        }

        var fieldName = StringUtils.deCapitalize(_interface.getSimpleName()) + "Instance";
        builder.addField(options ->
        {
            options.setModifier("private");
            options.setType(_interface.getName());
            options.setName(fieldName);
        });
        methodBuilder.setBody(options ->
        {
            options.textFormat("""
                            if(%s != null)
                            {
                              return %s;
                            }
                                                
                            %s = new %s(%s);
                            return %s;
                            """,
                    fieldName,
                    fieldName,
                    fieldName,
                    implementation.getName(),
                    getConstructorInjections(implementation),
                    fieldName);
        });
        builder.addMethod(methodBuilder.build());
    }

    private void addInterfaceAndProvider(RegistrationInfo info) {
        var _interface = info._interface() == null ? info.implementation() : info._interface();

        var methodName = createMethodName(_interface);


        var providerFieldName = createFieldName(_interface) + "Provider";


        registerMethod.addBodyLine(String.format("""
                if(info.registrationType() == RegistrationType.InterfaceAndProvider &&
                    info._interface().equals(%s.class))
                    {
                      %s = info.provider();
                      return true;
                    }
                """,
                _interface.getName(),
                providerFieldName));

        builder.addField(options ->
        {
            options.setModifier("private");
            options.setType("Function<Container,Object>");
            options.setName(providerFieldName);
        });


        var methodBuilder = new MethodCodeGenerator();
        methodBuilder.addComment(String.format("%s for class %s", info.lifeTime().name(), _interface.getSimpleName()));
        methodBuilder.setModifiers("private");
        methodBuilder.setType(_interface.getName());
        methodBuilder.setName(methodName);
        constructor.addBodyLine(String.format("injections.put(%s.class, this::%s);", _interface.getName(), methodName));


        if (info.lifeTime() == LifeTime.TRANSIENT) {

            methodBuilder.setBody(options ->
            {
                options.textFormat("""
                                if(%s == null)
                                {
                                   System.out.println("Provider need to be registered");
                                   return null;
                                }
                                return  (%s)%s.apply(this);
                                """,
                        providerFieldName,
                        _interface.getName(),
                        providerFieldName);
            });
            builder.addMethod(methodBuilder.build());
            return;
        }

        var fieldName = StringUtils.deCapitalize(_interface.getSimpleName()) + "Instance";
        builder.addField(options ->
        {
            options.setModifier("private");
            options.setType(_interface.getName());
            options.setName(fieldName);
        });
        methodBuilder.setBody(options ->
        {
            options.textFormat("""
                            if(%s == null)
                            {
                             System.out.println("Provider need to be registered");
                             return null;
                            }
                                                    
                            if(%s != null)
                            {
                               return %s;
                            }
                                                    
                            %s = (%s)%s.apply(this);
                            return %s;
                                """,
                    providerFieldName,
                    fieldName,
                    fieldName,
                    fieldName,
                    _interface.getName(),
                    providerFieldName,
                    fieldName);
        });
        builder.addMethod(methodBuilder.build());
    }

    private String createMethodName(Class<?> clazz) {
        var fullname = clazz.getName().replace('.', '_');
        return "get" + StringUtils.capitalize(fullname);
    }

    private String getConstructorInjections(Class<?> clazz) {
        if (clazz.getConstructors().length == 0) {
            return StringUtils.EMPTY;
        }
        var builder = new MessageBuilder();
        var constructor = clazz.getConstructors()[0];
        var parameters = constructor.getParameterTypes();
        var i = 0;
        for (var param : parameters) {
            var methodName = createMethodName(param);
            builder.text(methodName).text("()");

            if (i != parameters.length - 1) {
                builder.text(",");
            }
            i++;
        }
        return builder.toString();
    }

    private String createFieldName(Class<?> clazz) {
        return StringUtils.deCapitalize(clazz.getSimpleName()) + "Instance";
    }

}
