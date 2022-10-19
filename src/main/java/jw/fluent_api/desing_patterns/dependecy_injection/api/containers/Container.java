package jw.fluent_api.desing_patterns.dependecy_injection.api.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;

public interface Container extends Cloneable
{
     boolean register(RegistrationInfo registrationInfo) throws Exception;

     Object find(Class<?> type);

     Container clone();
}
