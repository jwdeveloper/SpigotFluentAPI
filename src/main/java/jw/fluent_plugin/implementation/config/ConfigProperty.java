package jw.fluent_plugin.implementation.config;

public record ConfigProperty<T> (String path, T defaultValue, String ... description) {
}
