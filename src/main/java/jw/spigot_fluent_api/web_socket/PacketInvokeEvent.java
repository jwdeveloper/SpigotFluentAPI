package jw.spigot_fluent_api.web_socket;

import org.java_websocket.WebSocket;

public interface PacketInvokeEvent
{
    public void onPacketTriggered(WebSocket webSocket);
}
