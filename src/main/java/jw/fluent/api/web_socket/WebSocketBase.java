package jw.fluent.api.web_socket;


import jw.fluent.plugin.implementation.modules.logger.FluentLogger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketBase extends WebSocketServer {
    private final ConcurrentHashMap<Integer, WebSocketPacket> webSocketEvents;

    public WebSocketBase(int port) {
        super(new InetSocketAddress(port));
        webSocketEvents = new ConcurrentHashMap<>();
    }

    public void registerPackets(Collection<FluentWebsocketPacket> packets)
    {
        for (var packet : packets)
        {
            webSocketEvents.put(packet.getPacketId(), (WebSocketPacket)packet);
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
      //  FluentPlugin.logInfo(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
       // FluentPlugin.logInfo(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " leave the room!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer buffer) {
        var packetId = buffer.getInt(0);
        var webSocketEvent = webSocketEvents.get(packetId);
        if(webSocketEvent == null)
            return;
        if (!webSocketEvent.resolvePacket(buffer))
            return;
        webSocketEvent.onPacketTriggered(webSocket);
    }

    @Override
    public void onClosing(WebSocket conn, int code, String reason, boolean remote) {
        super.onClosing(conn, code, reason, remote);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if(webSocket == null)
            FluentLogger.LOGGER.error("Web socket error ", e);
        else
            FluentLogger.LOGGER.error("Web socket error "+webSocket.getRemoteSocketAddress().getAddress().toString(), e);
    }

    @Override
    public void onStart() {
        //FluentPlugin.logSuccess("Hello world from socket");
    }
}
