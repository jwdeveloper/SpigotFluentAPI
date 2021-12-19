package jw.spigot_fluent_api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentEvent<T extends Event> {
    private final Consumer<T> onEvent;
    private final List<Consumer<T>> nextActions;
    private Consumer<Exception> onError;
    private boolean isActive;
    private String permission;

    public FluentEvent(Consumer<T> onEvent) {
        this.onEvent = onEvent;
        this.nextActions = new ArrayList<>();
        this.isActive = true;
    }

    public FluentEvent<T> setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public FluentEvent<T> setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public boolean invoke(T arg) {
        if (!isActive)
            return false;

        if (permission != null && arg instanceof PlayerEvent playerEvent) {
            if (!playerEvent.getPlayer().hasPermission(permission)) {
                return false;
            }
        }

        try {
            onEvent.accept(arg);
            for (var nextAction : nextActions) {
                if (arg instanceof Cancellable cancelable) {
                    if (cancelable.isCancelled())
                        break;
                }
                nextAction.accept(arg);
            }
            return true;
        } catch (Exception exception) {
            if (onError != null)
                onError.accept(exception);
            return false;
        }
    }

    public FluentEvent<T> onError(Consumer<Exception> onError) {
        this.onError = onError;
        return this;
    }

    public FluentEvent<T> andThen(Consumer<T> action) {
        nextActions.add(action);
        return this;
    }
}
