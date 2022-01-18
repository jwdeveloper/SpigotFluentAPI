package jw.spigot_fluent_api.utilites.code_generator;

import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.fluent_message.MessageBuilder;

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
        FileUtility.save(value,path,ObserverClassGenerator.class.getSimpleName()+".txt");
    }
}

/*example output
public class PlayerStatsObserver
{
   private Observable<Integer> scoreObserver = new Observable<>();
   private Observable<String> playerNameObserver = new Observable<>();
   private Observable<Boolean> isActiveObserver = new Observable<>();
   private Observable<Number> pointsObserver = new Observable<>();
   private Observable<List> permissionsObserver = new Observable<>();

public PlayerStatsObserver()
{
   scoreObserver.bind(PlayerStats.class,"score");
   playerNameObserver.bind(PlayerStats.class,"playerName");
   isActiveObserver.bind(PlayerStats.class,"isActive");
   pointsObserver.bind(PlayerStats.class,"points");
   permissionsObserver.bind(PlayerStats.class,"permissions");
}

public void setObject(PlayerStats model)
{
   scoreObserver.setObject(model);
   playerNameObserver.setObject(model);
   isActiveObserver.setObject(model);
   pointsObserver.setObject(model);
   permissionsObserver.setObject(model);
}

}
 */