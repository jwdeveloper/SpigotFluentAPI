package jw.fluent.api.desing_patterns.dependecy_injection.api.factory;

import jw.fluent.api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent.api.utilites.java.Pair;

public interface InjectionInfoFactory
{
    public Pair<Class<?>,InjectionInfo> create(RegistrationInfo registrationInfo) throws Exception;
}
