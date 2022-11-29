package jw.fluent_plugin.implementation.modules.tests;

import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;

public class FluentTestsAction implements FluentApiExtention
{

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        //var classes = options.getPlugin().getTypeManager().findByInterface(SpigotTest.class);
      //  SpigotIntegrationTestsRunner.loadTests(classes);
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }
}
