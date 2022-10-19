package jw.fluent_api.web_socket.resolver;

import java.nio.ByteBuffer;

public class StringResolver implements TypeResolver
{

    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer) {
        return null;
    }

    @Override
    public int typeSize() {
        return 999;
    }
}
