package jw.spigot_fluent_api_integration_tests;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.ClassTypeUtility;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SpigotIntegrationTestsRunner {

    public static void loadTests() {
        FluentPlugin.logSuccess("Integration tests enabled");
        var classes = ClassTypeUtility.fineClassesInPackage(SpigotIntegrationTestsRunner.class.getPackageName());
        var integrationTestsClasses = classes.stream().filter(c -> !Modifier.isAbstract(c.getModifiers())).toList();
        List<SpigotIntegrationTest> integrationTests = new ArrayList<SpigotIntegrationTest>();
        for (var integration : integrationTestsClasses) {
            if (integration.getSuperclass() != SpigotIntegrationTest.class) {
                continue;
            }
            try {
                var object = (SpigotIntegrationTest) integration.newInstance();
                object.loadTests();
                integrationTests.add(object);
            } catch (Exception e) {
                FluentPlugin.logError("Error while creating " + integration.getSimpleName() + " integration test");
            }
        }
        integrationTests = integrationTests.stream().sorted((o1, o2) ->
        {
            if (o1.getPiority() ==
                    o2.getPiority()) {
                return 0;
            } else if (o1.getPiority() <
                    o2.getPiority()) {
                return -1;
            }
            return 1;
        }).toList();
        FluentPlugin.logSuccess("Integration tests run");
        for (var integrationTest : integrationTests) {
            integrationTest.runTests();
        }
    }
}
