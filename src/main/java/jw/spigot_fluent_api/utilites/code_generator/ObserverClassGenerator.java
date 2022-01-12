package jw.spigot_fluent_api.utilites.code_generator;

import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;

public class ObserverClassGenerator {


    public static void generate(Class type, String outputPath) {
        var value = generateObserver(type);
        saveToFile(value, outputPath);
    }

    private static String generateObserver(Class type) {
        var outPut = new MessageBuilder();

        var classTypeName = type.getSimpleName();
        var className = classTypeName + "Observer";
        var fields = type.getDeclaredFields();
        var objectName = "model";
        var methodName = "setObject";
        var offset =3;

        //open class
        outPut.text("public class ").text(className)
                .newLine()
                .text("{")
                .newLine();

        //fields definition
        for (var field : fields) {
            outPut.space(offset)
                    .text("private Observable<")
                    .text(field.getType().getSimpleName())
                    .text(">")
                    .space()
                    .text(field.getName() + "Observer")
                    .space()
                    .text("= new Observable<>();")
                    .newLine();
        }
        outPut.newLine();
        //constructor
        outPut.text("public ").text(className).text("()").newLine();
        outPut.text("{").newLine();
        for (var field : fields) {
            var fieldName = field.getName()+"Observer";
            outPut.space(offset).text(fieldName)
                    .text(".bind(")
                    .text(classTypeName+".class")
                    .text(",")
                    .text("\""+ field.getName()+"\"")
                    .text(");").newLine();
        }
        outPut.text("}").newLine();
        //close constructor

        outPut.newLine();
        //setObjectMethod  public void setObject(Class<?> model)
        //open method
        outPut.text("public void setObject").withFix(classTypeName+" "+objectName,"(",")").newLine();
        outPut.text("{").newLine();
        for (var field : fields) {
            var fieldName = field.getName()+"Observer";
            outPut.space(offset).text(fieldName).text(".").text(methodName).withFix(objectName,"(",")").text(";").newLine();
        }
        outPut.text("}").newLine();
        //close method

        outPut.newLine().text("}");
        //Close class
        return outPut.toString();
    }

    private static void saveToFile(String value, String path)
    {
        FileUtility.save(value,path,"GeneratedCode.txt");
    }
}
/*
//output
public class LifeCrystalObserver
{
    private Observable<Integer> startLifesObserver =new Observable<>();
    private Observable<Integer> maxLifesObserver =new Observable<>();
    private Observable<String> deadMessageObserver =new Observable<>();
    private Observable<Boolean> canBeCraftedObserver =new Observable<>();
    public LifeCrystalObserver(Object model)
    {
        startLifesObserver = new Observable<>(model,"startLifes");
        maxLifesObserver = new Observable<>(model,"maxLifes");
        deadMessageObserver = new Observable<>(model,"deadMessage");
        canBeCraftedObserver = new Observable<>(model,"canBeCrafted");
    }

      //playerUUIDObserver.setObject();
}

 */