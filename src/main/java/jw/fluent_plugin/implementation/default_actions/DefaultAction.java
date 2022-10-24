package jw.fluent_plugin.implementation.default_actions;

import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
import jw.fluent_api.utilites.java.JavaUtils;

public abstract class DefaultAction implements PluginAction {

    protected PluginOptionsImpl pluginOptions;

    public DefaultAction(PluginOptionsImpl pluginOptions)
    {
        this.pluginOptions = pluginOptions;
    }



    protected boolean validate(String value)
    {
        if(value == null || value.length() == 0 || value.equals(JavaUtils.EMPTY_STRING))
        {
            return false;
        }

        return true;
    }
    protected boolean validateCommand(PipelineOptions options) throws Exception {

        if(options.getDefaultCommand().hasDefaultCommand())
        {
            return true;
        }

        throw new Exception("default command is required for Lang Action");
    }
}
