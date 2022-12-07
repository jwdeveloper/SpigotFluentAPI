package jw.fluent.plugin.implementation.modules.websocket;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.web_socket.FluentWebsocketPacket;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.config.ConfigProperty;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.websocket.api.FluentWebsocket;
import jw.fluent.plugin.implementation.modules.websocket.api.WebsocketOptions;
import jw.fluent.plugin.implementation.modules.websocket.implementation.FluentWebsocketImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

public class FluentWebsocketExtention implements FluentApiExtention {

    private boolean runServer;
    private Consumer<WebsocketOptions> consumer;

    public FluentWebsocketExtention(Consumer<WebsocketOptions> optionsConsumer) {
        consumer = optionsConsumer;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

        var config = builder.config();
        var runProperty = runProperty();
        runServer = config.getOrCreate(runProperty);
        if (!runServer)
        {
            FluentLogger.LOGGER.info("Websocket is disabled in order to enable change piano.websocket.run to true in config.yml");
            return;
        }
        builder.container().register(FluentWebsocket.class, LifeTime.SINGLETON, container ->
        {
            var options = new WebsocketOptions();
            consumer.accept(options);
            var configProperty = portProperty(options.getDefaultPort());
            var port = config.getOrCreate(configProperty);
            return new FluentWebsocketImpl(port);
        });
        builder.container().registerList(FluentWebsocketPacket.class, LifeTime.SINGLETON);
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        if (!runServer) {
            return;
        }
        var config = fluentAPI.getFluentConfig();
        var webSocket = (FluentWebsocketImpl) fluentAPI.getFluentInjection().findInjection(FluentWebsocket.class);
        var customIpProperty = customIpProperty();
        var customIp = config.getOrCreate(customIpProperty);
        if (StringUtils.nullOrEmpty(customIp)) {
            webSocket.setServerIp(getServerPublicIP());
        } else {
            webSocket.setServerIp(customIp);
        }

        var packets = fluentAPI.getFluentInjection().findAllByInterface(FluentWebsocketPacket.class);
        FluentLogger.LOGGER.info("PacketsHandler",packets.size());
        webSocket.registerPackets(packets);
        webSocket.start();
        fluentAPI.getFluentLogger().info("Websocket runs on ",webSocket.getServerIp()+":"+webSocket.getPort());
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {
        if (!runServer) {
            return;
        }
        var webSocket = fluentAPI.getFluentInjection().findInjection(FluentWebsocket.class);
        webSocket.stop();
    }

    private String getServerPublicIP() throws IOException {
        var url = new URL("http://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }


    private ConfigProperty<Integer> portProperty(int defaultPort) {
        return new ConfigProperty<Integer>("plugin.websocket.port", defaultPort, "Set port for websocket, make sure the port is open on your hosting");
    }

    private ConfigProperty<String> customIpProperty() {
        return new ConfigProperty<String>("plugin.websocket.custom-id", StringUtils.EMPTY_STRING, "Set port for websocket");
    }

    private ConfigProperty<Boolean> runProperty() {
        return new ConfigProperty<Boolean>("plugin.websocket.run", true, "if disabled websocket will not run");
    }
}
