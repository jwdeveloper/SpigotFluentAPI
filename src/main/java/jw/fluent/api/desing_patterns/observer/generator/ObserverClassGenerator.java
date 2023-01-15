package jw.fluent.api.desing_patterns.observer.generator;

import jw.fluent.api.desing_patterns.observer.api.ObserverField;
import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ObserverClassGenerator {

    private HashMap observers = new HashMap<String, String>();
    private final Class<?> baseModel;
    private final String _package;

    private final Set<Class<?>> blackList;


    public ObserverClassGenerator(Class<?> baseModel, String _package) {
        this.baseModel = baseModel;
        this._package = _package;
        blackList = new HashSet<>();
        blackList.add(List.class);
    }

    public Map<String, String> generate() {
        observers = new HashMap<String, String>();

        generateObserverClass(baseModel);

        return observers;
    }


    private void generateObserverClass(Class<?> model) {
        var generator = new ClassCodeBuilder();
        var name = model.getSimpleName();
        var observerName = name + "Observer";
        var modelName = StringUtils.deCapitalize(name);
        generator.setPackage(_package);
        generator.addImport("lombok.Getter");
        generator.addImport("jw.fluent.api.desing_patterns.observer.implementation.Observer");
        generator.addImport(model.getName());
        generator.addAnnotation("Getter");
        generator.setModifiers("public");
        generator.setClassName(observerName);


        var fields = model.getDeclaredFields();


        generator.addConstructor(e ->
        {
            e.setName(observerName);
            e.setModifiers("public");
            e.addParameter(name + " " + modelName);
            e.setBody(body ->
            {
                body.textNewLine("this." + modelName + " = " + modelName + ";");
                for (var field : fields) {
                    if(isBlackList(field.getType()))
                    {
                        continue;
                    }

                    if (isSubObserver(field)) {
                        body.textFormat("%s = new %sObserver(%s);",
                                field.getName(),
                                field.getType().getSimpleName(),
                                modelName + ".get" + StringUtils.capitalize(field.getName()) + "()"
                        ).newLine();
                        continue;
                    }

                    body.textFormat("%s = new Observer<>(%s,\"%s\");", field.getName(), modelName, field.getName()).newLine();
                }
            });
        });


        generator.addField(e ->
        {
            e.setModifier("private final");
            e.setName(StringUtils.deCapitalize(name));
            e.setType(name);

        });
        for (var field : fields) {
            if(isBlackList(field.getType()))
            {
                continue;
            }

            generator.addField(e ->
            {
                e.setModifier("private final");
                e.setName(field.getName());
                generator.addImport(field.getType().getName());

                if (isSubObserver(field)) {
                    e.setType(field.getType().getSimpleName() + "Observer");
                    generateObserverClass(field.getType());
                    return;
                }

                e.setType("Observer<" + field.getType().getSimpleName() + ">");
            });
        }


        observers.put(observerName, generator.build());
    }


    private boolean isSubObserver(Field type) {
        return type.isAnnotationPresent(ObserverField.class);
    }

    private boolean isBlackList(Class<?> type)
    {
        return blackList.contains(type);
    }


}

