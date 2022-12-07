package jw.fluent.plugin.implementation.modules.websocket.api;

import jw.fluent.api.web_socket.FluentWebsocketPacket;
import jw.fluent.api.web_socket.WebSocketPacket;

import java.util.Collection;

public interface FluentWebsocket
{
    String getServerIp();

    int getPort();

    void start();

    void stop() throws InterruptedException;

     void registerPackets(Collection<FluentWebsocketPacket> packets);
}
