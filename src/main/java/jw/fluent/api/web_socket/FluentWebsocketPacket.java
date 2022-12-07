package jw.fluent.api.web_socket;

import org.java_websocket.WebSocket;

public interface FluentWebsocketPacket
{
    public void onPacketTriggered(WebSocket webSocket);

    public  int getPacketId();
}
