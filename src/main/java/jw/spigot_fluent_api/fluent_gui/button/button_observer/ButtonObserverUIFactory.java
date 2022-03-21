package jw.spigot_fluent_api.fluent_gui.button.button_observer;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUIFactory;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl.EnumSelectorObserver;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.fluent_text_input.FluentTextInput;
import jw.spigot_fluent_api.desing_patterns.observer.Observer;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import org.bukkit.ChatColor;

public class ButtonObserverUIFactory extends ButtonUIFactory {


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
                                event.getButton().setDescription(new MessageBuilder().field("State", "Enable"));
                            } else {
                                event.getButton().setHighlighted(false);
                                event.getButton().setDescription(new MessageBuilder().field("State", "Disable"));
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
                            event.getButton().setDescription(new MessageBuilder().field("Value", event.getValue()));
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
                            event.getButton().setDescription(new MessageBuilder().field("Value", event.getValue()));
                        })
                );
    }

    public ButtonObserverUIBuilder objectObserver(Observer<Integer> observable, ChestUI chestUI) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<Integer>()
                        .withObserver(observable)
                        .onValueChange(event ->
                        {
                            event.getButton().setDescription(new MessageBuilder().field("Value", event.getValue()));
                        })
                );
    }
}
