package jw.fluent_plugin.api.extention;

import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;

public interface FluentApiExtentionsManager
{
    public void register(FluentApiExtention event);

    public void onEnable(FluentApi fluentAPI);

    public void onDisable(FluentApi fluentAPI);

    public void onConfiguration(FluentApiBuilder builder);

}