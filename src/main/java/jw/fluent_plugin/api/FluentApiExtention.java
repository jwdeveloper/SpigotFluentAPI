package jw.fluent_plugin.api;

import jw.fluent_plugin.implementation.FluentApi;

public interface FluentApiExtention {

    public void onConfiguration(FluentApiBuilder builder);

    void onFluentApiEnable(FluentApi fluentAPI) throws Exception;

    void onFluentApiDisabled(FluentApi fluentAPI) throws Exception;
}
