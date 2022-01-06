package jw.spigot_fluent_api.web_socket;


import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;

public class WebSocketBase extends WebSocketServer {
    private final HashMap<Integer, WebSocketPacket> webSocketEvents = new HashMap<>();

    public WebSocketBase(int port) {
        super(new InetSocketAddress(port));
    }

    public void registerPackets(List<WebSocketPacket> packets)
    {
        for (var event : packets)
        {
            webSocketEvents.put(event.getPacketId(), event);
        }
    }



    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        FluentPlugin.logInfo(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        FluentPlugin.logInfo(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " leave the room!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer buffer) {
        int id = buffer.getInt(0);
        if (!webSocketEvents.containsKey(id)) {
            return;
        }
        WebSocketPacket webSocketEvent = webSocketEvents.get(id);
        if (!webSocketEvent.resolvePacket(buffer)) {
            return;
        }
        webSocketEvent.onPacketTriggered(webSocket);
    }

    @Override
    public void onClosing(WebSocket conn, int code, String reason, boolean remote) {
        super.onClosing(conn, code, reason, remote);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if(webSocket == null)
        FluentPlugin.logException("Web socket error ", e);
        else
       FluentPlugin.logException("Web socket error "+webSocket.getRemoteSocketAddress().getAddress().toString(), e);
    }
}
