package jw.fluent.api.web_socket.resolver;

import java.nio.ByteBuffer;

public class IntResolver implements TypeResolver{
    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer) {
        return buffer.getInt(currentIndex);
    }

    @Override
    public int typeSize() {
        return 4;
    }
}
