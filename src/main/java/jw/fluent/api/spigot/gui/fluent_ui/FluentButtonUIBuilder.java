package jw.fluent.api.spigot.gui.fluent_ui;


import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.*;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonNotifier;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserver;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserverBuilder;
import jw.fluent.api.spigot.gui.inventory_gui.enums.ButtonType;
import jw.fluent.api.spigot.gui.inventory_gui.events.ButtonUIEvent;
import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonDescriptionInfoBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.styles.renderer.ButtonStyleRenderer;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.function.Consumer;
import java.util.function.Supplier;


public class FluentButtonUIBuilder {
    private final FluentTranslator translator;
    private final ButtonDescriptionInfoBuilder descriptionBuilder;
    private final ButtonStyleRenderer renderer;
    private final ButtonObserverUIBuilder buttonBuilder;

    public FluentButtonUIBuilder(FluentTranslator translator, ButtonStyleRenderer renderer) {
        this.renderer = renderer;
        this.translator = translator;
        descriptionBuilder = new ButtonDescriptionInfoBuilder();
        buttonBuilder = new ButtonObserverUIBuilder();
    }

    public <T> FluentButtonUIBuilder setObserver(Observer<T> observer, ButtonNotifier<T> buttonNotifier) {
        buttonBuilder.addObserver(observer, buttonNotifier);
        return this;
    }

    public <T> FluentButtonUIBuilder setObserver(Supplier<Observer<T>> observer, ButtonNotifier<T> buttonNotifier) {
        buttonBuilder.addObserver(observer, buttonNotifier);
        return this;
    }

    public <T> FluentButtonUIBuilder setObserver(Consumer<ButtonObserverBuilder<T>> consumer) {
        var observer = new ButtonObserverBuilder<T>();
        consumer.accept(observer);
        buttonBuilder.addObserver(observer);
        return this;
    }

    public <T> FluentButtonUIBuilder setObserver(ButtonObserver<T> buttonObserver) {
        buttonBuilder.addObserver(buttonObserver);
        return this;
    }


    public FluentButtonUIBuilder setDescription(Consumer<ButtonDescriptionInfoBuilder> consumer) {
        consumer.accept(descriptionBuilder);
        return this;
    }

    public FluentButtonUIBuilder setLocation(int height, int width) {
        buttonBuilder.setLocation(height, width);
        return this;
    }

    public FluentButtonUIBuilder setMaterial(Material material) {
        buttonBuilder.setMaterial(material);
        return this;
    }

    public FluentButtonUIBuilder setMaterial(Material material, int customMaterialId) {
        buttonBuilder.setCustomMaterial(material, customMaterialId);
        return this;
    }

    public FluentButtonUIBuilder setMaterial(Material material, int customMaterialId, Color color)
    {
        buttonBuilder.setCustomMaterial(material, customMaterialId, color);
        return this;
    }

    public FluentButtonUIBuilder setButtonType(ButtonType type) {
        buttonBuilder.setButtonType(type);
        return this;
    }

    public FluentButtonUIBuilder setPermissions(String... permissions) {
        buttonBuilder.setPermissions(permissions);
        return this;
    }

    public FluentButtonUIBuilder setOnLeftClick(ButtonUIEvent event) {
        buttonBuilder.setOnClick(event);
        return this;
    }

    public FluentButtonUIBuilder setOnRightClick(ButtonUIEvent event) {
        buttonBuilder.setOnRightClick(event);
        return this;
    }

    public FluentButtonUIBuilder setOnShiftClick(ButtonUIEvent event) {
        buttonBuilder.setOnShiftClick(event);
        return this;
    }

    public FluentButtonUIBuilder setHighlighted() {
        buttonBuilder.setHighlighted();
        return this;
    }

    public ButtonObserverUI build(InventoryUI inventoryUI) {
        assert inventoryUI != null;
        var description = renderer.render(descriptionBuilder.build());
        buttonBuilder.setDescription(description);
        buttonBuilder.setTitle(" ");
        return buttonBuilder.buildAndAdd(inventoryUI);
    }
}
