package jw.fluent.plugin.api.extention;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.api.FluentApiExtention;

public interface FluentApiExtentionsManager
{
    public void register(FluentApiExtention extention);
    public void register(FluentApiExtention extention, ExtentionPiority piority);
    public void registerLow(FluentApiExtention extention);

    public void onEnable(FluentApi fluentAPI);

    public void onDisable(FluentApi fluentAPI);

    public void onConfiguration(FluentApiBuilder builder);

}
