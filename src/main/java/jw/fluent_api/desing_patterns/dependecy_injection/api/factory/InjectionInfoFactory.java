package jw.fluent_api.desing_patterns.dependecy_injection.api.factory;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.utilites.java.Pair;

public interface InjectionInfoFactory
{
    public Pair<Class<?>,InjectionInfo> create(RegistrationInfo registrationInfo) throws Exception;
}
