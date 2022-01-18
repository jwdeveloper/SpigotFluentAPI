package jw.spigot_fluent_api.desing_patterns.dependecy_injection;

public class FluentInjection {

    static
    {
        instance = new FluentInjection();
    }

    private static FluentInjection instance;
    private Container container;

    public FluentInjection()
    {
       container = new Container();
    }

    public static <T> T getInjection(Class<T> injectionType)
    {
        return instance.container.get(injectionType);
    }

    public static Container getInjectionContainer() {
        return instance.container;
    }

    public static void setInjectionContainer(Container container) {
        instance.container = container;
    }
}
