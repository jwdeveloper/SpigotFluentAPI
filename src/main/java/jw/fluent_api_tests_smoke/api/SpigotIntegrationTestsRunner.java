package jw.fluent_api_tests_smoke.api;

import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpigotIntegrationTestsRunner {

    public static void loadTests(Collection<Class<?>> classes) {
        FluentLogger.LOGGER.success("Integration tests enabled");
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
                FluentLogger.LOGGER.error("Error while creating " + integration.getSimpleName() + " integration test");
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
        FluentLogger.LOGGER.success("Integration tests run");
        for (var integrationTest : integrationTests) {
            integrationTest.runTests();
        }

        System.out.println("Integration tests done");
        System.exit(1);
    }
}
