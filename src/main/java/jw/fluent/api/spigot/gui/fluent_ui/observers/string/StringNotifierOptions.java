package jw.fluent.api.spigot.gui.fluent_ui.observers.string;

import jw.fluent.api.spigot.gui.fluent_ui.observers.NotifierOptions;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class StringNotifierOptions extends NotifierOptions {
    private Consumer<TextInputEvent> onTextInput = (e) -> {
    };

    private Consumer<MessageBuilder> message = (e) -> {};
}
