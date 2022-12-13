package jw.fluent.api.spigot.gui.fluent_ui;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.gui.fluent_ui.observers.bools.BoolNotifierOptions;
import jw.fluent.api.spigot.gui.fluent_ui.observers.bools.FluentBoolNotifier;
import jw.fluent.api.spigot.gui.fluent_ui.observers.enums.EnumNotifierOptions;
import jw.fluent.api.spigot.gui.fluent_ui.observers.enums.FluentEnumNotifier;
import jw.fluent.api.spigot.gui.fluent_ui.observers.ints.IntNotifierOptions;
import jw.fluent.api.spigot.gui.fluent_ui.observers.ints.FluentIntNotifier;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.FluentListNotifier;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.ListNotifierOptions;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.api.spigot.gui.fluent_ui.styles.FluentButtonStyle;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverBuilder;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverUIBuilder;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.text_input.FluentTextInput;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import org.bukkit.ChatColor;
import org.bukkit.Material;


import java.util.List;
import java.util.function.Consumer;

public class FluentButtonUIFactory {
    private final FluentButtonStyle fluentButtonStyle;
    private final FluentTranslator translator;

    private final FluentButtonUIBuilder builder;

    public FluentButtonUIFactory(FluentTranslator translator,
                                 FluentButtonStyle style,
                                 FluentButtonUIBuilder builder) {
        fluentButtonStyle = style;
        this.translator = translator;
        this.builder = builder;
    }

    public FluentButtonUIBuilder observeInt(Observer<Integer> observer, Consumer<IntNotifierOptions> consumer) {
        var options = new IntNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick("+ "+options.getYield());
            buttonDescriptionInfoBuilder.setOnRightClick("- "+options.getYield());
        });
        builder.setObserver(observer, new FluentIntNotifier(translator, options));
        return builder;
    }

    public <T extends Enum<T>> FluentButtonUIBuilder observeEnum(Observer<T> observer, Consumer<EnumNotifierOptions> consumer) {
        var _class = (Class<T>) observer.getValueType();
        var options = new EnumNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick("Next");
            buttonDescriptionInfoBuilder.setOnRightClick("Previous");
        });
        builder.setObserver(observer, new FluentEnumNotifier<T>(_class, options));
        return builder;
    }

    public <T extends Enum<T>> FluentButtonUIBuilder observeEnum(Observer<T> observer) {

        return observeEnum(observer, enumNotifierOptions -> {});
    }

    public <T> FluentButtonUIBuilder observeList(Observer<Integer> indexObserver, List<T> values,  Consumer<ListNotifierOptions<T>> consumer) {
        var options = new ListNotifierOptions<T>();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick("Next");
            buttonDescriptionInfoBuilder.setOnRightClick("Previous");
        });
        builder.setObserver(indexObserver, new FluentListNotifier<T>(values, options));
        return builder;
    }

    public FluentButtonUIBuilder observeBool(Observer<Boolean> observer) {
        return observeBool(observer,boolNotifierOptions -> {});
    }
    public FluentButtonUIBuilder observeBool(Observer<Boolean> observer, Consumer<BoolNotifierOptions> consumer) {
        var options = new BoolNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick("Change value");
        });
        builder.setObserver(observer, new FluentBoolNotifier(translator, options));
        return builder;
    }


    public FluentButtonUIBuilder back(InventoryUI inventory) {
        return back(inventory, null);
    }

    public FluentButtonUIBuilder back(InventoryUI inventory, InventoryUI parent) {
        builder.setOnLeftClick((player, button) ->
                {
                    if (parent == null) {
                        inventory.close();
                        return;
                    }
                    parent.open(player);
                })
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    var title = StringUtils.EMPTY;
                    if (parent == null)
                        title = new MessageBuilder().color(ChatColor.GRAY).inBrackets(FluentApi.translator().get("gui.base.exit.title")).toString();
                    else
                        title = new MessageBuilder().color(ChatColor.GRAY).inBrackets(FluentApi.translator().get("gui.base.back.title")).toString();
                    buttonDescriptionInfoBuilder.setTitle(title);
                })
                .setMaterial(Material.ARROW)
                .setLocation(inventory.getHeight() - 1, inventory.getWidth() - 1);
        return builder;
    }

    public ButtonObserverUIBuilder stringInput(Observer<String> observable, ChestUI chestUI) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<String>()
                        .withObserver(observable)
                        .onClick(event ->
                        {
                            chestUI.close();
                            var message = new MessageBuilder()
                                    .color(ChatColor.GREEN)
                                    .inBrackets("Enter text value").toString();
                            FluentTextInput.openTextInput(event.getPlayer(), message, value ->
                            {
                                event.getObserver().setValue(value);
                                chestUI.open(event.getPlayer());
                            });
                        })
                        .onValueChange(event ->
                        {
                            event.getButton().setDescription(new MessageBuilder().field(FluentApi.translator().get("gui.base.value"), event.getValue()));
                        })
                );
    }

    public ButtonObserverUIBuilder intInput(Observer<Integer> observable, ChestUI chestUI) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<Integer>()
                        .withObserver(observable)
                        .onClick(event ->
                        {
                            chestUI.close();
                            var message = new MessageBuilder()
                                    .color(ChatColor.GREEN)
                                    .inBrackets("Enter number value").toString();
                            FluentTextInput.openTextInput(event.getPlayer(), message, value ->
                            {
                                if (value.matches("^(0|-*[1-9]+[0-9]*)$"))
                                    event.getObserver().setValue(Integer.valueOf(value));

                                chestUI.open(event.getPlayer());
                            });
                        })
                        .onValueChange(event ->
                        {
                            event.getButton().setDescription(new MessageBuilder().field(FluentApi.translator().get("gui.base.value"), event.getValue()));
                        })
                );
    }
}
