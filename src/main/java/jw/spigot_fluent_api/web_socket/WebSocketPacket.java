package jw.spigot_fluent_api.web_socket;


import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import jw.spigot_fluent_api.desing_patterns.observer.Observer;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Consumer;

public abstract class WebSocketPacket implements PacketInvokeEvent
{
    private int packetSize =0;
    private int packetIdSize = 4;
    private List<Observer<Object>> fieldList = new ArrayList<>();
    private Queue<Consumer<WebSocket>> tasks  = new LinkedList<>();
    private FluentTaskTimer taskTimer;

    public abstract void onPacketTriggered(WebSocket webSocket);

    public abstract int getPacketId();

    public WebSocketPacket()
    {
        loadPacketFields();
        packetSize = getPacketSize();
        taskTimer = new FluentTaskTimer(1,(time, taskTimer1) ->
        {
            for(Consumer<WebSocket> webSocket:tasks)
            {
                webSocket.accept(null);
            }
            tasks.clear();
        });
        taskTimer.run();
    }
    protected void addSpigotTask(Consumer<WebSocket> consumer)
    {
        tasks.add(consumer);
    }

    private void loadPacketFields()
    {
        for(Field field:this.getClass().getDeclaredFields())
        {
            if (field.getAnnotation(PacketProperty.class) == null)
                continue;
            fieldList.add(new Observer(this,field));
        }
    }

    public boolean resolvePacket(ByteBuffer buffer)
    {
        if(packetSize != buffer.limit())
        {
            FluentPlugin.logSuccess(packetSize+" "+buffer.limit());
            return false;
        }
        int bufferIndex =packetIdSize;
        int start = packetIdSize;
        Object value  = null;
        Type type;
        for(int i=0;i<fieldList.size();i++)
        {
            try
            {
                start = bufferIndex;
                type = fieldList.get(i).getType();
                FluentPlugin.logSuccess(type.getTypeName()+" ");
                if (type.getTypeName().equals("int"))
                {
                    bufferIndex = start+4;
                    value = buffer.getInt(start);
                }
                if (type.getTypeName().equals("byte"))
                {
                    bufferIndex = start+1;
                    value = buffer.get(start);
                }
                if (type.getTypeName().equals("long"))
                {
                    bufferIndex = start+8;
                    value = buffer.getLong(start);
                }
                if (type.getTypeName().equals("bool"))
                {
                    bufferIndex = start+1;
                    value = buffer.get(start) != 0;
                }
                if (type.getTypeName().equals("java.util.UUID"))
                {
                    FluentPlugin.logSuccess("UUID!!!!!!!!! ");
                    bufferIndex = start+16;
                    final var bytes = new byte[16];
                    buffer.get(start,bytes);
                    value = UUID.nameUUIDFromBytes(bytes);
                    FluentPlugin.logSuccess(value+" ");
                }
                if (type.getTypeName().equals("string"))
                {
                    bufferIndex = start+1;
                    final var bytes = new byte[buffer.limit()-start];
                    buffer.get(start,bytes);
                    value = new String(bytes);
                }
                fieldList.get(i).set(value);
            }
            catch (Exception e)
            {
                FluentPlugin.logException("Packet resolver error "+this.getClass().getSimpleName(),e);
            }
        }
        return true;
    }

    private int getPacketSize()
    {
        int size =0;
        for(Observer<Object> bindingField:fieldList)
        {
            if (bindingField.getType().getTypeName().equals("byte") ||
                    bindingField.getType().getTypeName().equals("bool")
            )
            {
                size+=1;
            }
            if (bindingField.getType().getTypeName().equals("int"))
            {
                size+=4;
            }
            if (bindingField.getType().getTypeName().equals("long"))
            {
                size+=8;
            }
            if (bindingField.getType().getTypeName().equals("java.util.UUID"))
            {
                size+=16;
            }
        }
        return packetIdSize + size;
    }

    protected void sendJson(WebSocket webSocket, Object obj)
    {
        var gson =  JsonUtility.getGson();
        webSocket.send(gson.toJson(obj));
    }


}
