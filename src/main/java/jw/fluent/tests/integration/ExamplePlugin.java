package jw.fluent.tests.integration;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.FluentPlugin;

public class ExamplePlugin extends FluentPlugin
{
    /*
      Btw, in case you're interested: I've now played with proguard to eliminate dead code,
      this is the configuration I've ended up with: https://paste.md-5.net/kepezehaca.xml,
      while using this as my proguard config: https://paste.md-5.net/unevafucun.coffeescript
      The shaded jar had a size of 50K while the shrink could get it down to 36K.
      May not be impressive to many, but that was only one (and my smallest) lib. I'm planning on doing much more than that.
      Seems like a nice setup, as it doesn't take much longer than usual and I do not have to take any extra steps. "It just works".
     */
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {



    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {


    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }
}
