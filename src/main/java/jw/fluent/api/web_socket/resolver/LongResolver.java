package jw.fluent.api.web_socket.resolver;

import java.nio.ByteBuffer;

public class LongResolver implements TypeResolver
{

    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer)
    {
        return buffer.getLong(currentIndex);
    }

    @Override
    public int typeSize() {
        return 8;
    }
}
