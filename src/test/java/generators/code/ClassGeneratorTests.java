/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package generators.code;

import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

public class ClassGeneratorTests {


    @Test
    public void test() throws IOException {
        var generator = new ClassCodeBuilder();


        generator.setClassName("ExampleClass")
                .setModifiers("public")
                .setPackage("jw.example.generators")

                .addAnnotation("Data")
                .addAnnotation("Injection")

                .addInterface("Container")
                .addInterface("Example")

                .addComment("this is generated class")
                .addComment("please not modify it")
                .addField(options ->
                {
                    options.addComment("Siema");
                    options.addComment("Ciema");
                    options.setModifier("public");
                    options.setType("Integer");
                    options.setName("value");
                    options.setValue("12");
                })
                .addConstructor(options ->
                {
                    options.setBody("""
                            value = 12;
                            """);
                    options.setModifiers("public");
                })
                .addMethod(options ->
                {
                    options.addComment("Siema");
                    options.addComment("Ciema");
                    options.setModifiers("public");
                    options.setType("void");
                    options.setName("setValue");
                    options.addParameter("int value");
                    options.setBody("""
                            this.value = value;
                            """);
                })
                .addMethod(options ->
                {
                    options.setModifiers("public");
                    options.setType("Integer");
                    options.setName("getValue");
                    options.addParameter("int a");
                    options.addParameter("string b");
                    options.setBody("""
                            value = value + 1;
                            return value;
                            """);
                });

        var result = generator.build();
        var writer = new FileWriter("D:\\MC\\paper_1.19\\plugins\\JW_Piano\\test.txt");
        writer.write(result);
        writer.close();
    }

}
