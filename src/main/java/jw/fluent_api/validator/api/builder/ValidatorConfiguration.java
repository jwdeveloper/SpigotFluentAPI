package jw.fluent_api.validator.api.builder;

public interface ValidatorConfiguration<T> {

    public PropertyValidator<T> forProperty(String name);
}
