package jw.fluent_api.spigot.gui.button.button_observer;

import com.google.common.base.Function;
import jw.fluent_api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent_api.spigot.gui.button.ButtonUIFactory;
import jw.fluent_api.spigot.gui.button.button_observer.observer_impl.EnumSelectorObserver;
import jw.fluent_api.spigot.gui.button.button_observer.observer_impl.ListSelectorObserver;
import jw.fluent_api.spigot.gui.button.button_observer.observer_impl.TextListSelectorObserver;
import jw.fluent_api.spigot.gui.button.button_observer.observer_impl.events.onSelectEvent;
import jw.fluent_api.spigot.gui.implementation.chest_ui.ChestUI;
import jw.fluent_api.spigot.text_input.FluentTextInput;
import jw.fluent_api.desing_patterns.observer.implementation.Observer;
import jw.fluent_api.spigot.messages.message.MessageBuilder;
import jw.fluent_plugin.implementation.FluentAPI;
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
        final var _class = (Class<T>) observable.getType();
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(observable, new EnumSelectorObserver<T>(_class));
    }


    public ButtonObserverUIBuilder boolObserver(Observer<Boolean> observable) {
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
                                event.getButton().setMaterial(Material.LIME_CONCRETE);
                                event.getButton().setDescription(new MessageBuilder().field(FluentAPI.lang().get("gui.base.state"), FluentAPI.lang().get("gui.base.active")));
                            } else {
                                event.getButton().setHighlighted(false);
                                event.getButton().setMaterial(Material.RED_CONCRETE);
                                event.getButton().setDescription(new MessageBuilder().field(FluentAPI.lang().get("gui.base.state"), FluentAPI.lang().get("gui.base.inactive")));
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
                            event.getButton().setDescription(new MessageBuilder().field(FluentAPI.lang().get("gui.base.value"), event.getValue()));
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
                            event.getButton().setDescription(new MessageBuilder().field(FluentAPI.lang().get("gui.base.value"), event.getValue()));
                        })
                );
    }



    public ButtonObserverUIBuilder intSelectObserver(Observer<Integer> observable, int min, int max, int yield) {
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
                            event.getButton().setDescription(new MessageBuilder().field(FluentAPI.lang().get("gui.base.value"), event.getValue()));
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
                           event.getButton().setDescription(new MessageBuilder().field(FluentAPI.lang().get("gui.base.value"), event.getValue()));
                        })
                );
    }
}
