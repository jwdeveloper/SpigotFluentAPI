package jw.fluent.plugin.api.config;

public record ConfigProperty<T> (String path, T defaultValue, String ... description) {
}
