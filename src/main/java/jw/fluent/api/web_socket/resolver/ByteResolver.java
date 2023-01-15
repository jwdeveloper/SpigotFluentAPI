package jw.fluent.api.web_socket.resolver;

import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

import java.nio.ByteBuffer;

public class ByteResolver implements TypeResolver{
    @Override
    public Object resolve(int currentIndex, ByteBuffer buffer) {
        if(buffer.limit() <= currentIndex)
        {
            return 0;
        }

        return buffer.get(currentIndex);
    }

    @Override
    public int typeSize() {
        return 1;
    }
}
