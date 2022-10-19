package jw.fluent_api.desing_patterns.dependecy_injection.api.containers.builders;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.ContainerConfiguration;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ContainerBuilder
{
     ContainerBuilder register(Class<?> _class, LifeTime lifeTime);
     <T> ContainerBuilder register(Class<T> _interface, Class<? extends T> implementation, LifeTime lifeTime);
     <T> ContainerBuilder register(Class<T> _interface, LifeTime lifeTime, Function<Object,Object> provider);
     ContainerBuilder registerSigleton(Class<?> _class);
     ContainerBuilder registerTransient(Class<?> _class);
      ContainerBuilder configure(Consumer<ContainerConfiguration> configuration);
}
