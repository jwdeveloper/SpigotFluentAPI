package jw.spigot_fluent_api.fluent_validator.api.builder;

public interface ValidatorConfiguration<T> {

    public PropertyValidator<T> forProperty(String name);
}
