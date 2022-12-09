package jw.fluent.plugin.api.assembly_scanner;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface FluentAssemblyScanner
{
     Collection<Class<?>> findByAnnotation(Class<? extends Annotation> annotation);

     Collection<Class<?>> findByInterface(Class<?> _interface);

     Collection<Class<?>> findBySuperClass(Class<?> parentClass);

     Collection<Class<?>> findByPackage(Package _package);
}
