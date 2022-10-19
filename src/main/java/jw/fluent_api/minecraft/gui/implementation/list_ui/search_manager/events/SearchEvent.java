package jw.fluent_api.minecraft.gui.implementation.list_ui.search_manager.events;

import org.bukkit.entity.Player;

public record SearchEvent<T>(String searchKey, T data, Player player) {
}
