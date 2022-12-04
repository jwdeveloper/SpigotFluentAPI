package jw.fluent.api.web_socket.resolver;

import java.nio.ByteBuffer;

public class BoolResolver implements TypeResolver{
    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer) {

        return buffer.get(currentIndex) != 0;
    }

    @Override
    public int typeSize() {
        return 1;
    }
}
