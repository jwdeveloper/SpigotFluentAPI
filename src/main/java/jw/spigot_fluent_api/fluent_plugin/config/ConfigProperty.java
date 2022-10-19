package jw.spigot_fluent_api.fluent_plugin.config;

public record ConfigProperty<T> (String path, T defaultValue, String ... description) {
}
