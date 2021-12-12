package jw.spigot_fluent_api.gui_v2.button.button_observer;

import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.utilites.binding.Observable;

import java.util.function.Consumer;

public class ButtonObserverBuilder<T> {
    private Observable<T> observable;
    private ButtonUI buttonUI;
    private Consumer<ButtonObserverEvent<T>> onClickEvent = (a) -> {
    };
    private Consumer<ButtonObserverEvent<T>> onChangeEvent = (a) -> {
    };

    public ButtonObserverBuilder<T> observable(Observable<T> observable) {
        this.observable = observable;
        return this;
    }

    public ButtonObserverBuilder<T> buttonUI(ButtonUI buttonUI) {
        this.buttonUI = buttonUI;
        return this;
    }

    public ButtonObserverBuilder<T> onClick(Consumer<ButtonObserverEvent<T>> event) {
        this.onClickEvent = event;
        return this;
    }

    public ButtonObserverBuilder<T> onValueChange(Consumer<ButtonObserverEvent<T>> event) {
        this.onChangeEvent = event;
        return this;
    }

    public ButtonObserver<T> build() {
        var result = new ButtonObserver<>(observable, new ButtonNotifier() {
            @Override
            public void onClick(ButtonObserverEvent event) {
                onClickEvent.accept(event);
            }

            @Override
            public void onValueChanged(ButtonObserverEvent event) {
                onChangeEvent.accept(event);
            }
        });
        result.setButtonUI(buttonUI);
        return result;
    }

}
