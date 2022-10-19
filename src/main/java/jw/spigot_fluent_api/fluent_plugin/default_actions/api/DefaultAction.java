package jw.spigot_fluent_api.fluent_plugin.default_actions.api;

import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.spigot_fluent_api.utilites.java.JavaUtils;

public abstract class DefaultAction implements PluginPipeline {

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
