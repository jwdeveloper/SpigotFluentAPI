package jw.fluent.plugin.implementation.modules.websocket.api;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class WebsocketOptions {
    private int defaultPort = 443;

    @Getter
    private List<Consumer<FluentWebsocket>> onStarted = new ArrayList<>();

    @Getter
    private List<Consumer<FluentWebsocket>> onStopped = new ArrayList<>();


   public void onStart(Consumer<FluentWebsocket> event) {
        onStarted.add(event);
    }

   public void onStop(Consumer<FluentWebsocket> event) {
        onStopped.add(event);
    }
}
