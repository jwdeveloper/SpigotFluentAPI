package jw.fluent.api.utilites.code_generator;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.utilites.java.StringUtils;

public class UnitTestGenerator {
    public static void generate(Class<?> _interface, Class<?> _implementation, String outputPath) {
        var value = generateTest(_interface, _implementation);
        saveToFile(value, outputPath);
    }

    private static String generateTest(Class<?> _interface, Class<?> _implementation) {
        var outPut = new MessageBuilder();

        var classTypeName = _implementation.getSimpleName();
        var className = classTypeName + "Tests";
        var fields = _implementation.getConstructors()[0].getParameterTypes();
        var sutName = "sut";
        var offset = 3;


        for(var type :_implementation.getConstructors()[0].getParameterTypes())
        {
            outPut.text("import ").text(type.getName()).text(";").newLine();
        }

        addImports(outPut,_interface.getName(),_implementation.getName(),
                "static org.mockito.Mockito.mock",
                "org.junit.Test");

        //open class
        outPut.text("public class ").text(className)
                .newLine()
                .text("{")
                .newLine();

        //sut
        outPut.space(offset);
        CreateProperty(outPut, "private final",
                _interface.getSimpleName(),
                sutName,
                StringUtils.EMPTY);

        //mock definition
        for (var field : fields) {
            outPut.space(offset);
            CreateProperty(outPut, "private final",
                    field.getSimpleName(),
                    decapitalize(field.getSimpleName())+"Mock",
                    "mock("+field.getSimpleName()+".class)");
        }
        outPut.newLine();
        //constructor
        outPut.space(offset).text("public ").text(className).text("()").newLine();
        outPut.space(offset).text("{").newLine();
        outPut.space(offset*2).text(sutName).text(" = new ").text(_implementation.getSimpleName()).text("(");
        for(var i =0;i<fields.length;i++)
        {
            var field = fields[i];
            outPut.newLine();
            if(i!= fields.length-1)
            {
                outPut.space(2+offset*2).text(decapitalize(field.getSimpleName())+"Mock").text(",");
            }
            else
            {
                outPut.space(2+offset*2).text(decapitalize(field.getSimpleName())+"Mock");
            }
        }
        outPut.text(");");
        outPut.newLine();
        outPut.space(offset).text("}").newLine();
        //close constructor

        outPut.newLine();
        //setObjectMethod  public void setObject(Class<?> model)
        //open method
        for (var method : _interface.getMethods()) {
            CreateTestMethod(outPut, offset,method.getName() + "_Should_Success");
            CreateTestMethod(outPut, offset,method.getName() + "_Should_Fail");
        }
        //close method

        outPut.newLine().text("}");
        //Close class
        return outPut.toString();
    }

    private static void saveToFile(String value, String path) {
        FileUtility.save(value, path, "GeneratedTest.txt");
    }
    private static void CreateProperty(MessageBuilder outPut, String alias, String typeName, String methodName, String value) {

        outPut.text(alias)
                .space()
                .text(typeName)
                .space()
                .text(methodName);
                if(value.equals(StringUtils.EMPTY))
                {
                    outPut.text(";");
                }
                else
                {
                    outPut.text(" = ").text(value).text(";");
                }
              outPut.newLine();
    }

    private static void CreateTestMethod(MessageBuilder outPut,int offset, String methodName) {
        outPut.newLine();
        //setObjectMethod  public void setObject(Class<?> model)
        //open method
        outPut.space(offset).text("@Test").newLine();
        outPut.space(offset).text("public void ").text(methodName).text("()").newLine();
        outPut.space(offset).text("{").newLine();
        outPut.space(offset).newLine();
        outPut.space(offset).text("}").newLine();
        //close method
    }

    private static void  addImports(MessageBuilder outPut, String ... imports)
    {
        for(var import_ : imports)
        {
            outPut.text("import ").text(import_).text(";").newLine();
        }
        outPut.newLine();
    }
    private static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);

        return new String(c);
    }
}
