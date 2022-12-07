package jw.fluent.plugin.implementation.modules.websocket.implementation;

import jw.fluent.api.web_socket.FluentWebsocketPacket;
import jw.fluent.api.web_socket.WebSocketBase;
import jw.fluent.api.web_socket.WebSocketPacket;
import jw.fluent.plugin.implementation.modules.websocket.api.FluentWebsocket;

import java.util.Collection;

public class FluentWebsocketImpl extends WebSocketBase implements FluentWebsocket
{
    private String serverIp;

    public void setServerIp(String serverIp)
    {
        this.serverIp = serverIp;
    }

    public String getServerIp()
    {
     return serverIp;
    }

    @Override
    public int getPort() {
        return super.getPort();
    }

    public FluentWebsocketImpl(int port) {
        super(port);
    }

    @Override
    public void stop() throws InterruptedException {
        super.stop();
    }

    @Override
    public void registerPackets(Collection<FluentWebsocketPacket> packets) {
        super.registerPackets(packets);
    }

    @Override
    public void start() {
        super.start();
    }
}
