package jw.spigot_fluent_api.fluent_gui.button.button_observer;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUIFactory;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.fluent_text_input.FluentTextInput;
import jw.spigot_fluent_api.desing_patterns.observer.fields.Observable;
import jw.spigot_fluent_api.fluent_message.MessageBuilder;
import org.bukkit.ChatColor;

public class ButtonObserverUIFactory extends ButtonUIFactory {

    public ButtonObserverUIBuilder boolObserver(Observable<Boolean> observable) {
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


    public ButtonObserverUIBuilder stringObserver(Observable<String> observable, ChestUI chestUI) {
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


    public ButtonObserverUIBuilder intObserver(Observable<Integer> observable, ChestUI chestUI) {
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

    public ButtonObserverUIBuilder objectObserver(Observable<Integer> observable, ChestUI chestUI) {
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
