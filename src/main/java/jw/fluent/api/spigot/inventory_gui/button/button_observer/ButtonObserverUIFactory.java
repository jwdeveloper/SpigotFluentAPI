package jw.fluent.api.spigot.inventory_gui.button.button_observer;

import com.google.common.base.Function;
import jw.fluent.api.spigot.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUIFactory;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl.EnumSelectorObserver;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl.ListSelectorObserver;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl.TextListSelectorObserver;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl.events.onSelectEvent;
import jw.fluent.api.spigot.text_input.FluentTextInput;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Consumer;

public class ButtonObserverUIFactory extends ButtonUIFactory {



    public <T> ButtonObserverUIBuilder listSelectorObserver(Observer<Integer> indexObserver,
                                                                List<T> values,
                                                                Function<T,String> mapping) {
        return ButtonObserverUI.<T>builder()
                .addObserver(indexObserver, new ListSelectorObserver<T>(values, mapping));
    }

    public <T> ButtonObserverUIBuilder listSelectorObserver(Observer<Integer> indexObserver,
                                                            List<T> values,
                                                            Function<T,String> mapping,
                                                            Consumer<onSelectEvent<T>> event) {
        return ButtonObserverUI.<T>builder()
                .addObserver(indexObserver, new ListSelectorObserver<T>(values, mapping,event));
    }


    public <T> ButtonObserverUIBuilder listSelectorObserver(List<T> values,
                                                            Function<T,String> mapping,
                                                            Consumer<onSelectEvent<T>> event)
                                                             {
        return ButtonObserverUI.<T>builder()
                .addObserver(new ObserverBag<Integer>(0).getObserver(),
                        new ListSelectorObserver<T>(values, mapping,event));
    }


    public ButtonObserverUIBuilder textListSelectorObserver(Observer<Integer> indexObserver, List<String> values) {
        return ButtonObserverUI.builder()
                .addObserver(indexObserver, new TextListSelectorObserver(values));
    }


    public <T extends Enum<T>> ButtonObserverUIBuilder enumSelectorObserver(Observer<T> observable) {
        final var _class = (Class<T>) observable.getValueType();
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(observable, new EnumSelectorObserver(_class));
    }


    public ButtonObserverUIBuilder boolObserver(Observer<Boolean> observable) {
        return boolObserver(observable, Material.LIME_CONCRETE,Material.RED_CONCRETE );
    }

    public ButtonObserverUIBuilder boolObserver(Observer<Boolean> observable, Material enableMaterial, Material disableMaterial) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<Boolean>()
                        .withObserver(observable)
                        .onClick(event ->
                        {
                            event.getObserver().setValue(!event.getValue());
                        })
                        .onValueChange(event ->
                        {
                            if (event.getValue()) {
                                event.getButton().setHighlighted(true);
                                event.getButton().setMaterial(enableMaterial);
                                event.getButton().setDescription(new MessageBuilder().field(FluentApi.translator().get("gui.base.state"), FluentApi.translator().get("gui.base.active")));
                            } else {
                                event.getButton().setHighlighted(false);
                                event.getButton().setMaterial(disableMaterial);
                                event.getButton().setDescription(new MessageBuilder().field(FluentApi.translator().get("gui.base.state"), FluentApi.translator().get("gui.base.inactive")));
                            }
                        })
                );
    }


    public ButtonObserverUIBuilder stringObserver(Observer<String> observable, ChestUI chestUI) {
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


    public ButtonObserverUIBuilder intObserver(Observer<Integer> observable, ChestUI chestUI) {
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



    public ButtonObserverUIBuilder intRangeObserver(Observer<Integer> observable, int min, int max, int yield) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<Integer>()
                        .withObserver(observable)
                        .onClick(event ->
                        {
                            if (event.getValue() + yield > max) {
                                event.getObserver().setValue(min);
                                return;
                            }
                            event.getObserver().setValue(event.getValue() + yield);
                        })
                        .onValueChange(event ->
                        {
                            event.getButton().setDescription(new MessageBuilder().field(FluentApi.translator().get("gui.base.value"), event.getValue()));
                        })
                );
    }

    public <T> ButtonObserverUIBuilder defaultObserver(Observer<T> observable) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<T>()
                        .withObserver(observable)
                        .onValueChange(event ->
                        {
                           event.getButton().setDescription(new MessageBuilder().field(FluentApi.translator().get("gui.base.value"), event.getValue()));
                        })
                );
    }
}
