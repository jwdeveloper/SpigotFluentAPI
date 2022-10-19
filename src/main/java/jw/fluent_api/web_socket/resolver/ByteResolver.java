package jw.fluent_api.web_socket.resolver;

import java.nio.ByteBuffer;

public class ByteResolver implements TypeResolver{
    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer) {
        return buffer.get(currentIndex);
    }

    @Override
    public int typeSize() {
        return 1;
    }
}
