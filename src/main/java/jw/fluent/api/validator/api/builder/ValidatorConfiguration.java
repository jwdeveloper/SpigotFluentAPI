package jw.fluent.api.validator.api.builder;

public interface ValidatorConfiguration<T> {

    public PropertyValidator<T> forProperty(String name);
}
