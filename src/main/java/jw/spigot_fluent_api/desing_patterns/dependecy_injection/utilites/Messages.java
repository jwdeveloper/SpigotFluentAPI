package jw.spigot_fluent_api.desing_patterns.dependecy_injection.utilites;

public class Messages
{
    public static final String INFINITE_LOOP = "Field: %s must not have the same type as %s since it do infinite loop";

    public static final String INJECTION_FIELD_NOT_FOUND = "Field: %s has not been registered to Dependency Injection container, so it can't be use in %s";

    public static final String INJECTION_NOT_FOUND ="Class %s has not been register to Dependency Injection container";

    public static final String INJECTION_ALREADY_EXISTS = "Class %s has been already register inside Dependency Injection container";

    public static final String INJECTION_CANT_CREATE = "Can not create injection of type type %s";
}
