package jw.fluent_api.desing_patterns.dependecy_injection.api.models;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

@Data
public class InjectionInfo
{
     private Class<?> injectionKeyType;
     private RegistrationInfo registrationInfo;

     private Class<?>[] constructorTypes;
     private Constructor<?> injectedConstructor;
     private Set<Class<?>> superClasses;
     private Set<Class<?>> interfaces;
     private Set<Class<? extends Annotation>> annotations;

     private Object[] constructorPayLoadTemp;

     private Object instnace;

     public LifeTime getLifeTime()
     {
          return registrationInfo.lifeTime();
     }

     public boolean hasInjectedConstructor()
     {
          return injectedConstructor != null;
     }

     public boolean hasAnnotation(Class<? extends Annotation> _annotation)
     {
          return annotations.contains(_annotation);
     }

     public boolean hasSuperClass(Class<?> parent) {
          return superClasses.contains(parent);
     }

     public boolean hasInterface(Class<?> parent)
     {
          return interfaces.contains(parent);
     }

     public void setConstructorTypes(Class<?>[] constructorTypes) {
          this.constructorTypes = constructorTypes;
          this.constructorPayLoadTemp = new Object[constructorTypes.length];
     }
}
