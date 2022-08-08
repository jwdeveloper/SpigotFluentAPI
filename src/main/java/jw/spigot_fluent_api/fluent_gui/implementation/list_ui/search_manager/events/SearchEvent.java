package jw.spigot_fluent_api.fluent_gui.implementation.list_ui.search_manager.events;

import org.bukkit.entity.Player;

public record SearchEvent<T>(String searchKey, T data, Player player) {
}
