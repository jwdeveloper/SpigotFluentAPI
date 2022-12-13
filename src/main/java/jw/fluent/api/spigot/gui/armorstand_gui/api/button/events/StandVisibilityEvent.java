package jw.fluent.api.spigot.gui.armorstand_gui.api.button.events;

import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;

public record StandVisibilityEvent(StandButton button, boolean isVisible) {
}
