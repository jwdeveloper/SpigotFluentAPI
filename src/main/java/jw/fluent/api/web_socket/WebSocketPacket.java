package jw.fluent.api.web_socket;


import com.google.gson.Gson;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.api.web_socket.annotations.PacketProperty;
import jw.fluent.api.web_socket.resolver.*;
import jw.fluent.api.files.implementation.json.JsonUtility;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.java_websocket.WebSocket;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public abstract class WebSocketPacket implements FluentWebsocketPacket {
    private int packetSize = 0;
    private final int packetIdSize = 4;
    private final List<PacketFieldWrapper> packetFields;
    private final Queue<Consumer<WebSocket>> tasks = new LinkedBlockingQueue<>();
    private final SimpleTaskTimer taskTimer;
    private final Gson gson;

    public abstract void onPacketTriggered(WebSocket webSocket);

    public abstract int getPacketId();

    public WebSocketPacket() {
        gson = JsonUtility.getGson();
        packetFields = loadPacketFields();
        packetSize = getPacketSize();
        taskTimer = new SimpleTaskTimer(1, (time, taskTimer1) ->
        {
            for (final var task : tasks) {
                task.accept(null);
            }
            tasks.clear();
        });
        taskTimer.run();
    }

    protected void addSpigotTask(Consumer<WebSocket> consumer) {
        tasks.add(consumer);
    }

    private List<PacketFieldWrapper>  loadPacketFields() {
        var fields = this.getClass().getDeclaredFields();
        final List<PacketFieldWrapper> packetFields = new ArrayList(fields.length);
        for (Field field : fields) {
            if (field.getAnnotation(PacketProperty.class) == null)
                continue;
            var observer = new Observer(this, field);
            TypeResolver typeResolver = null;
            var type = observer.getValueType();
            if (type.getTypeName().equals("int")) {
                typeResolver = new IntResolver();
            }
            if (type.getTypeName().equals("byte")) {
                typeResolver = new ByteResolver();
            }
            if (type.getTypeName().equals("long")) {
                typeResolver = new LongResolver();
            }
            if (type.getTypeName().equals("bool")) {
                typeResolver = new BoolResolver();
            }
            if (type.getTypeName().equals("java.util.UUID")) {
                typeResolver = new UuidResolver();
            }
            if (type.getTypeName().equals("string")) {
                typeResolver = new StringResolver();
            }
            packetFields.add(new PacketFieldWrapper(observer, typeResolver));
        }
        return packetFields;
    }

    public boolean resolvePacket(ByteBuffer buffer) {
        if (packetSize != buffer.limit()) {
            FluentLogger.LOGGER.warning(getClass().getSimpleName(),"Invalid incoming packet size", buffer.limit(), "expected size",packetSize);
            return false;
        }
        int currentIndex = packetIdSize;
        Object result;
        try {
            for (var packetField : packetFields) {
                result = packetField.getResolver().resolve(currentIndex, buffer);
                currentIndex += packetField.getResolver().typeSize();
                packetField.getObserver().set(result);
            }
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Packet resolver error " + this.getClass().getSimpleName(), e);
            return false;
        }
        return true;
    }

    private int getPacketSize() {
        int size = 0;
        for (var packetField : packetFields) {
            size += packetField.getResolver().typeSize();
        }
        return packetIdSize + size;
    }

    protected void sendJson(WebSocket webSocket, Object obj) {

        webSocket.send(gson.toJson(obj));
    }


}
