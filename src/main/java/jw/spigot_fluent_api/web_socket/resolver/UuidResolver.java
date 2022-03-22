package jw.spigot_fluent_api.web_socket.resolver;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidResolver implements TypeResolver{
    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer) {
        final var bytes = new byte[16];
        buffer.get(currentIndex,bytes);
        return UUID.nameUUIDFromBytes(bytes);
    }

    @Override
    public int typeSize() {
        return 16;
    }
}
